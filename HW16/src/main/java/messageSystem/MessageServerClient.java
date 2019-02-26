package messageSystem;

import channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

public abstract class MessageServerClient implements Addressee{
    SocketMsgWorker messageServerClient;
    public final void initMessageServerClient(String host, int port){
        try {
            messageServerClient = new SocketMsgWorker(new Socket(host, port));
            messageServerClient.init();
        } catch (IOException e) {
            System.out.println("There is no Server on " + host + ":" + port);
            e.printStackTrace();
        }
    }
    @Override
    public final SocketMsgWorker getSocketMsgWorker() {
        return messageServerClient;
    }

}
