package webserver.servlets.websocket.usersWebSocket;

import app.FrontEndService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebSocket
public class UsersWebSocket {

    private Session session;
    private FrontEndService frontEnd;
    private HttpSession httpSession;
    private String id;

    public UsersWebSocket(FrontEndService frontEnd, HttpSession httpSession) {
        this.frontEnd = frontEnd;
        this.httpSession = httpSession;
        System.out.println("Session onCreate\n" + httpSession);
        id = java.util.UUID.randomUUID().toString();
    }

    public void onLoginUser(String result){
        try{
            session.getRemote().sendString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRegisteredUser(String result){
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        frontEnd.handleWebsocketRequest(data, getId());
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        frontEnd.subscribeOnRegisterUsers(getId(), getSession(), httpSession);
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
        frontEnd.unSubscribeOnRegisterUsers(getId());
        System.out.println("onClose");
    }

    public String getId() {
        return id;
    }
}
