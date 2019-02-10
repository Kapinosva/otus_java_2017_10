package webserver.servlets.websocket.chat;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketCreator implements WebSocketCreator {
    private Set<ChatWebSocket> users;

    public ChatWebSocketCreator() {
        this.users = new HashSet<>();
        System.out.println("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        ChatWebSocket socket = new ChatWebSocket(users, req.getSession());
        System.out.println("Socket created");
        return socket;
    }
}
