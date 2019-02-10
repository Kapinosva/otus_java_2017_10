package messageSystem;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("LoopStatementThatDoesntLoop")
public final class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
    private boolean started = false;
    private final List<Thread> workers;
    private final Map<Addressee, LinkedBlockingQueue<Message>> messagesMap;
    private List<Addressee> addresseeList;

    public MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeList = new LinkedList<>();
    }

    public void setAddresseeList(List<Addressee> addresseeList) {
        this.addresseeList = addresseeList;
        messagesMap.clear();
        addresseeList.forEach((a)->messagesMap.put(a, new LinkedBlockingQueue<>()));
    }

    public void addAddressee(Addressee addressee) {
        addresseeList.add(addressee);
        messagesMap.put(addressee, new LinkedBlockingQueue<>());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
        if (!started){
            started = true;
            start();
        }
    }


    public void start() {
        for (Addressee addressee : addresseeList) {
            String name = "MS-worker-" + addressee.getId();
            Thread thread = new Thread(() -> {
                while (true) {
                    LinkedBlockingQueue<Message> queue = messagesMap.get(addressee);
                    try {
                        Message message = queue.take(); //Blocks
                        message.exec(addressee);
                    } catch (InterruptedException e) {
                        logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                        return;
                    }
                }
            });
            thread.setName(name);
            thread.start();
            workers.add(thread);
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}
