package webserver.servlets.websocket;

import app.FrontendService;
import app.MessageSystemContext;
import app.messages.MsgLoginUser;
import app.messages.MsgRegisterUser;
import com.google.gson.Gson;
import messageSystem.MessageSystem;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebSocket
public class RegisterUserWebSocket implements FrontendService {

    private Session session;
    private MessageSystemContext msContext;
    private HttpSession httpSession;

    public RegisterUserWebSocket(MessageSystemContext msContext, HttpSession httpSession) {
        this.msContext = msContext;
        this.httpSession = httpSession;
        init();
    }

    public void setMsContext(MessageSystemContext msContext){
        this.msContext = msContext;
    }
    public void init() {
        msContext.setFrontAddressee(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.println(data);
        JSONParser parser = new JSONParser();
        JSONObject o = null;
        try {
            o = (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (o.get("type").equals("add")){
            msContext.getMessageSystem().sendMessage(
                    new MsgRegisterUser(
                            this
                            , msContext.getAsAddressee()
                            , o.get("login").toString()
                            , o.get("password").toString()

                    )
            );
        }else{
            msContext.getMessageSystem().sendMessage(
                    new MsgLoginUser(
                            this
                            , msContext.getLsAddressee()
                            , o.get("login").toString()
                            , o.get("password").toString()
                            , httpSession
                    )
            );
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
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
        System.out.println("onClose");
    }

    @Override
    public String getId() {
        return "FrontEnd";
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }


    @Override
    public void isRegisteredUser(String login, String result) {
        System.out.println(result + login);
        try {
            session.getRemote().sendString(result + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isLogindUser(String login, String result) {
        System.out.println(result + login);
        try {
            session.getRemote().sendString(result + login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
