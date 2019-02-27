package webserver.servlets.websocket.usersWebSocket;

import app.FrontEndService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;


public class UsersWebSocketCreator implements WebSocketCreator {

    private FrontEndService frontEnd;

    public UsersWebSocketCreator(FrontEndService frontEnd) {
        this.frontEnd = frontEnd;
        System.out.println("WebScketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        UsersWebSocket socket = new UsersWebSocket(frontEnd, req.getHttpServletRequest().getSession());
        System.out.println("Socket created");
        return socket;
    }
}
