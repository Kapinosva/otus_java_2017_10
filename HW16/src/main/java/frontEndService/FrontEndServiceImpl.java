package frontEndService;

import accountService.LoginService;
import accountService.account.UserAccount;
import app.FrontEndService;
import app.messages.MsgLoginUser;
import app.messages.MsgRegisterUser;

import com.google.gson.Gson;
import common.Const;
import messageSystem.Message;
import messageSystem.MessageServerClient;

import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontEndServiceImpl extends MessageServerClient implements FrontEndService {
    private Map<String,Session> usersEventsSubscribers = new HashMap<>();
    private Map<String,HttpSession> usersHttpSessionEventsSubscribers = new HashMap<>();
    private LoginService loginService;

    public FrontEndServiceImpl (LoginService loginService){
        this(loginService, "localhost", 5050);
    }

    public FrontEndServiceImpl (LoginService loginService, String host, int port){
        this.loginService = loginService;
        initMessageServerClient(host, port);
        start();
    }

    public void start(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Message msg = getSocketMsgWorker().take();
                    System.out.println("Message received in FrontEnd: " + msg.toString());
                    msg.exec(this);
                }
            } catch (InterruptedException e) {
                System.out.println("Bye front");
            }
        });
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
            getSocketMsgWorker().send(new MsgRegisterUser(
                    o.get("login").toString(),
                    o.get("password").toString()
            ));
        }else if (o.get("type").equals("login")) {
            getSocketMsgWorker().send(new MsgLoginUser(
                     o.get("login").toString()
                    , o.get("password").toString()
                    , SessionId
            ));
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

}
