import messageSystem.MessageServer;
import java.io.IOException;

import java.util.logging.Logger;

public class MessageServerMain {
    private static final Logger logger = Logger.getLogger(MessageServerMain.class.getName());

    private static final String ACCCLIENT_START_COMMAND =
            "java -jar ../HW16-AccountService/target/AccountService-jar-with-dependencies.jar";
    private static final String FRONTCLIENT_START_COMMAND =
            "java -jar ../HW16-FrontEnd/target/FrontEnd-jar-with-dependencies.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;


    public static void main(String[] args) {
        MessageServer messageServer = new MessageServer();
        messageServer.start();
       // processRun(ACCCLIENT_START_COMMAND);
        processRun(FRONTCLIENT_START_COMMAND);
    }

    private static void processRun(String cmd) {
        try {
            ProcessRunner processRunner = new ProcessRunner();
            processRunner.start(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
