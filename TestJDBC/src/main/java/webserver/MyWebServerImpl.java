package webserver;

import context.Context;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import webserver.servlets.*;


public class MyWebServerImpl implements MyWebServer {

    private Context cntxt;

    public MyWebServerImpl(Context cntxt){
        this.cntxt = cntxt;
    }

    public void start(){

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server server = new Server(8080);
        server.setHandler(context);
        addServlets(context);
        try {
            server.start();
            server.join();
            System.out.println("Server started");
        } catch (Exception e) {
            System.out.println("Server aborted");
            e.printStackTrace();
        }
    }

    private void  addServlets(ServletContextHandler context){
        context.addServlet(new ServletHolder(new AdminPageRequestServlet(cntxt)), "/adm");
        context.addServlet(new ServletHolder(new EditUserRequestServlet(cntxt)), "/getuser");
        context.addServlet(new ServletHolder(new SignUplRequestsServlet(cntxt)), "/signup");
        context.addServlet(new ServletHolder(new SignInlRequestsServlet(cntxt)), "/signin");
        context.addServlet(new ServletHolder(new AllRequestsServlet(cntxt)), "/*");
    }
}
