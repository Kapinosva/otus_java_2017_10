package app;

import messageSystem.Address;
import messageSystem.Addressee;
import messageSystem.Message;
import messageSystem.MessageServer;

public abstract class MsgToServer extends Message {
    public MsgToServer(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        //this message can be processed only by FrontEndService
        if (addressee instanceof MessageServer) {
            exec((MessageServer) addressee);
        }
    }

    public abstract void exec(MessageServer messageServer);
}