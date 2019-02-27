package webserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import webserver.servlets.*;

import java.util.ArrayList;
import java.util.List;

public class MyWebServerImpl implements MyWebServer {

    public void start(int port){

        System.out.println("Server starting");
        List<Handler> handlers = new ArrayList<>();

        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");

        String resourceString = Thread.currentThread().getContextClassLoader().getResource("WEB-INF/web.xml").toString();
        String war = resourceString.substring(0, resourceString.length() - 15);
        ctx.setWar(war);
        handlers.add(ctx);
        Server server = new Server(port);
        server.setHandler(ctx);
        try {
            server.start();
            System.out.println("Server started");
            server.join();
        } catch (Exception e) {
            System.out.println("Server aborted");
            e.printStackTrace();
        }
    }

}
