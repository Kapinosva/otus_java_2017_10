import webserver.MyWebServer;
import webserver.MyWebServerImpl;

public class Main {
    public static void main(String[] args) {
        MyWebServer server = new MyWebServerImpl();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
