package webserver.servlets.websocket;
import app.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.concurrent.TimeUnit;

@Configurable
public class WebSocketUsersServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);
    @Autowired
    private MessageSystemContext msContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void configure(WebSocketServletFactory factory) {

        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        factory.setCreator(new UsersWebSocketCreator(msContext));
    }
}
