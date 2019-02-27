import webserver.MyWebServer;
import webserver.MyWebServerImpl;

public class FrontEndMain {

    public static void main(String[] args) {

        MyWebServer server = new MyWebServerImpl();
        try {
            server.start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
