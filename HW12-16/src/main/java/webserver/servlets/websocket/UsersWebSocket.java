package webserver.servlets.websocket;

import app.FrontEndService;
import app.MessageSystemContext;
import app.messages.MsgLoginUser;
import app.messages.MsgRegisterUser;
import messageSystem.MessageSystem;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebSocket
public class UsersWebSocket {

    private Session session;
    private MessageSystemContext msContext;
    private HttpSession httpSession;

    public UsersWebSocket(MessageSystemContext msContext, HttpSession httpSession) {
        this.msContext = msContext;
        this.httpSession = httpSession;
        System.out.println("Session onCreate\n" + httpSession);
    }

    public void onLoginUser(String result){
        try{
            session.getRemote().sendString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRegisteredUser(String result){
        try{
            session.getRemote().sendString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
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
                            msContext.getFrontAddressee(),
                            msContext.getAsAddressee(),
                            o.get("login").toString(),
                            o.get("password").toString()
                    )
            );
        }else if (o.get("type").equals("login")) {
            msContext.getMessageSystem().sendMessage(
                    new MsgLoginUser(
                            msContext.getFrontAddressee()
                            , msContext.getLsAddressee()
                            , o.get("login").toString()
                            , o.get("password").toString()
                            , httpSession
                            , this
                    )
            );
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        msContext.getFrontAddressee().subscribeOnRegisterUsers(this);
        System.out.println("onOpen");
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        msContext.getFrontAddressee().unSubscribeOnRegisterUsers(this);
        System.out.println("onClose");
    }


}
