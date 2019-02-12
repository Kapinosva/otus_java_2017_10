package webserver.servlets.websocket;

import app.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;


public class UsersWebSocketCreator implements WebSocketCreator {

    private MessageSystemContext msContext;

    public UsersWebSocketCreator(MessageSystemContext msContext) {
        this.msContext = msContext;
        System.out.println("WebSuuuuuucketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        UsersWebSocket socket = new UsersWebSocket(msContext, req.getHttpServletRequest().getSession());        
        System.out.println("Socket created");
        return socket;
    }
}
