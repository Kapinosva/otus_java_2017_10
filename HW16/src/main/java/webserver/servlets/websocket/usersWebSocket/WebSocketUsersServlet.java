package webserver.servlets.websocket.usersWebSocket;

import app.FrontEndService;

import messageSystem.Message;
import messageSystem.MessageServerClient;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Configurable
public class WebSocketUsersServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);
    @Autowired
    private FrontEndService frontEnd;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        factory.setCreator(new UsersWebSocketCreator(frontEnd));
    }
}
