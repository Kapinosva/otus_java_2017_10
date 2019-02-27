package app;


import messageSystem.Message;

import java.io.Closeable;

public interface MsgWorker extends Closeable {
    void send(Message msg);

    Message pool();

    Message take() throws InterruptedException;

    int getSocketPort();
    int getSocketLocalPort();

    void close();
}
