package webserver.servlets.websocket;

import app.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import webserver.servlets.websocket.chat.ChatWebSocket;


public class RegisterUserWebSocketCreator implements WebSocketCreator {

    private MessageSystemContext msContext;

    public RegisterUserWebSocketCreator(MessageSystemContext msContext) {
        this.msContext = msContext;
        System.out.println("WebSuuuuuucketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        RegisterUserWebSocket socket = new RegisterUserWebSocket(msContext, req.getSession());
        System.out.println("Socket created");
        return socket;
    }
}
