package app;

import messageSystem.Addressee;
import messageSystem.MessageSystem;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Addressee frontAddressee;
    private Addressee asAddressee;//AccountService
    private Addressee lsAddressee;//LoginService

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
      //  messageSystem.start();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Addressee getFrontAddressee() {
        return frontAddressee;
    }

    public void setFrontAddressee(Addressee frontAddressee) {
        this.frontAddressee = frontAddressee;
        messageSystem.addAddressee(frontAddressee);
        frontAddressee.setMsContext(this);
    }

    public Addressee getAsAddressee() {
        return asAddressee;
    }

    public void setAsAddressee(Addressee asAddressee) {
        this.asAddressee = asAddressee;
        messageSystem.addAddressee(asAddressee);
        asAddressee.setMsContext(this);
    }

    public Addressee getLsAddressee() {
        return lsAddressee;
    }

    public void setLsAddressee(Addressee lsAddressee) {
        this.lsAddressee = lsAddressee;
        messageSystem.addAddressee(lsAddressee);
        lsAddressee.setMsContext(this);
    }
}
