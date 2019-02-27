package messageSystem;

import app.MsgWorker;
import channel.SocketMsgWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MessageServer extends MessageServerClient{
    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    private static final int THREADS_NUMBER = 2;
    private static final int PORT = 5050;
    private static final String HOST = "localhost";
    private static final int MIRROR_DELAY_MS = 100;

    private static Address MESSAGE_SERVER_ADDRESS = new Address("MessageServer");
    private static Address FRONT_END_ADDRESS = new Address("Front");
    private static Address ACCOUNT_SERVICE_ADDRESS = new Address("AccountService");

    public static int getPort(){
        return PORT;
    }
    public static String getHost(){
        return HOST;
    }

    public static Address getFrontEndAddress(){
        return FRONT_END_ADDRESS;
    }
    public static Address getMessageServerAddress(){
        return MESSAGE_SERVER_ADDRESS;
    }
    public static Address getAccountServiceAddress(){
        return ACCOUNT_SERVICE_ADDRESS;
    }

    private final ExecutorService executor;
    private final List<MsgWorker> workers;
    private final Map<Address, Integer> portAdresses = new ConcurrentHashMap<>();

    public MessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
    }
    public void setAddressPort(Address address, int port){
        portAdresses.put(address, port);
    }

    public void start(){
        executor.submit(this::reSend);
        executor.submit(()->{try {
                    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                        logger.info("Server started on port: " + serverSocket.getLocalPort());
                        while (!executor.isShutdown()) {
                            Socket socket = serverSocket.accept(); //blocks
                            SocketMsgWorker worker = new SocketMsgWorker(socket);
                            worker.init();
                            workers.add(worker);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Bye");
                }
                }
        );

    }

    private MsgWorker getWorkerByAddress(Address address) throws Exception {
        Integer port = portAdresses.get(address);
        for (MsgWorker endWorker : workers) {
            if (endWorker.getSocketPort() == port){
                return endWorker;
            }
        }
        throw new Exception();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void reSend() {
        while (true) {
            for (MsgWorker worker : workers) {
                Message msg = worker.pool();
                while (msg != null) {
                    System.out.println("MSG " + msg);
                    if (msg.getTo().equals(getAddress())){
                        msg.exec(this);
                    }else{
                        try {
                            getWorkerByAddress(msg.getTo()).send(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    msg = worker.pool();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }

    @Override
    public Address getAddress() {
        return MESSAGE_SERVER_ADDRESS;
    }
}
