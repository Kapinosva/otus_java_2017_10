package messageSystem;

import channel.SocketMsgWorker;

public interface Addressee {
    SocketMsgWorker getSocketMsgWorker();
}
