package frontEndService;

import accountService.LoginService;
import accountService.account.UserAccount;
import app.FrontEndService;
import app.MessageSystemContext;
import app.messages.MsgLoginUser;
import app.messages.MsgRegisterUser;

import com.google.gson.Gson;
import common.Const;
import messageSystem.Address;
import messageSystem.MessageSystem;
import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontEndServiceImpl implements FrontEndService {
    private Map<String,Session> usersEventsSubscribers = new HashMap<>();
    private Map<String,HttpSession> usersHttpSessionEventsSubscribers = new HashMap<>();
    private MessageSystemContext msContext;
    private Address address;
    private LoginService loginService;

    public FrontEndServiceImpl (Address address, LoginService loginService){
        this.address = address;
        this.loginService = loginService;
    }

    @Override
    public void subscribeOnRegisterUsers(String SessionId, Session usersWebSocketSession, HttpSession httpSession){
        usersEventsSubscribers.put(SessionId, usersWebSocketSession);
        usersHttpSessionEventsSubscribers.put(SessionId, httpSession);
    }

    @Override
    public void unSubscribeOnRegisterUsers(String SessionId){
        usersEventsSubscribers.remove(SessionId);
        usersHttpSessionEventsSubscribers.remove(SessionId);
    }

    @Override
    public void handleWebsocketRequest(String data, String SessionId) {
        JSONParser parser = new JSONParser();
        JSONObject o = null;
        try {
            o = (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (o.get("type").equals("add")) {
            msContext.getMessageSystem().sendMessage(
                    new MsgRegisterUser(
                            getAddress(),
                            msContext.getAccountServiceAddress(),
                            o.get("login").toString(),
                            o.get("password").toString()
                    )
            );
        }else if (o.get("type").equals("login")) {
            msContext.getMessageSystem().sendMessage(
                    new MsgLoginUser(
                            getAddress()
                            , msContext.getAccountServiceAddress()
                            , o.get("login").toString()
                            , o.get("password").toString()
                            , SessionId
                    )
            );
        }
    }

    @Override
    public void onRegisterUserAnswer(String login, String result) {
        for (Session session: usersEventsSubscribers.values()){
            try {
                session.getRemote().sendString(result + login);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoginUserAnswer(String login, String answerResultresult, String sessionId) {
        Gson g = new Gson();
        Map<String, String> result = g.fromJson(answerResultresult, Map.class);

        if (result.get("result").toString().equals(Const.LOGINNED_USER)){
            for (String subscriberSessionId: usersHttpSessionEventsSubscribers.keySet()){
                if (sessionId.equals(subscriberSessionId)){
                    UserAccount currentUser = g.fromJson(result.get("user").toString(), UserAccount.class);
                    loginService.loginUser(currentUser, usersHttpSessionEventsSubscribers.get(sessionId));
                }
            }
        }
        for (String subscriberSessionId: usersEventsSubscribers.keySet()){
            if (sessionId.equals(subscriberSessionId)){
                try {
                    usersEventsSubscribers.get(sessionId).getRemote().sendString(result.get("result").toString().concat(login));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

    public void setMsContext(MessageSystemContext msContext) {
        this.msContext = msContext;
        msContext.getMessageSystem().addAddressee(this);
    }
}
