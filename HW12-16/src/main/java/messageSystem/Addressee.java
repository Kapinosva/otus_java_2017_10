package messageSystem;

import app.MessageSystemContext;

public interface Addressee {
    Address getAddress();
    MessageSystem getMS();
    void setMsContext(MessageSystemContext msContext);
}
