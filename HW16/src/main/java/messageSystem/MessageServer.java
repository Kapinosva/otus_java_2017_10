package messageSystem;

import app.MsgWorker;
import channel.SocketMsgWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MessageServer {
    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService executor;
    private final List<MsgWorker> workers;

    public MessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
    }

    public void start(){
        ExecutorService MessageServerExecutor = Executors.newSingleThreadExecutor();
        MessageServerExecutor.submit(()->{try {
                    executor.submit(this::reSend);
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

    @SuppressWarnings("InfiniteLoopStatement")
    private void reSend() {
        while (true) {
            for (MsgWorker worker : workers) {
                Message msg = worker.pool();
                while (msg != null) {
                    System.out.println("MSG " + msg);
                    for (MsgWorker endWorker : workers) {
                        endWorker.send(msg);
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

}
