package frontEndService;

import app.FrontEndService;
import app.MessageSystemContext;
import app.messages.MsgLoginUser;
import app.messages.MsgRegisterUser;
import messageSystem.Address;
import messageSystem.MessageSystem;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import webserver.servlets.websocket.UsersWebSocket;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class FrontEndServiceImpl implements FrontEndService {
    private Set<UsersWebSocket> usersEventsSubscribers = new HashSet<>();
    private MessageSystemContext msContext;
    private Address address;

    public FrontEndServiceImpl (Address address){
        this.address = address;
    }

    @Override
    public void subscribeOnRegisterUsers(UsersWebSocket usersWebSocket){
        usersEventsSubscribers.add(usersWebSocket);
    }

    @Override
    public void unSubscribeOnRegisterUsers(UsersWebSocket usersWebSocket){
        usersEventsSubscribers.remove(usersWebSocket);
    }

    @Override
    public void handleWebsocketRequest(String data, HttpSession httpSession, UsersWebSocket usersWebSocket) {
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
                            , msContext.getLoginServiceAddress()
                            , o.get("login").toString()
                            , o.get("password").toString()
                            , httpSession
                            , usersWebSocket
                    )
            );
        }
    }

    @Override
    public void isRegisteredUser(String login, String result) {
        for (UsersWebSocket ws: usersEventsSubscribers){
            ws.onRegisteredUser(result + login);
        }
    }

    @Override
    public void isLogindUser(String login, String result, UsersWebSocket callBackLoginWS) {
        for (UsersWebSocket ws: usersEventsSubscribers){
            if (ws == callBackLoginWS){
                ws.onLoginUser(result + login);
                break;
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

    @Override
    public void setMsContext(MessageSystemContext msContext) {
        this.msContext = msContext;
        msContext.getMessageSystem().addAddressee(this);
    }
}
