package frontEndService;

import app.FrontEndService;
import app.MessageSystemContext;
import messageSystem.MessageSystem;
import webserver.servlets.websocket.UsersWebSocket;

import java.util.HashSet;
import java.util.Set;

public class FrontEndServiceImpl implements FrontEndService {
    private Set<UsersWebSocket> usersEventsSubscribers = new HashSet<>();
    private MessageSystemContext msContext;

    @Override
    public void subscribeOnRegisterUsers(UsersWebSocket usersWebSocket){
        usersEventsSubscribers.add(usersWebSocket);
    }

    @Override
    public void unSubscribeOnRegisterUsers(UsersWebSocket usersWebSocket){
        usersEventsSubscribers.remove(usersWebSocket);
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
    public String getId() {
        return null;
    }

    @Override
    public MessageSystem getMS() {
        return null;
    }

    @Override
    public void setMsContext(MessageSystemContext msContext) {
        this.msContext = msContext;
    }
}
