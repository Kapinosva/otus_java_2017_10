package messageSystem;

import app.MessageSystemContext;

public interface Addressee {
    String getId();
    MessageSystem getMS();
    void setMsContext(MessageSystemContext msContext);
}
