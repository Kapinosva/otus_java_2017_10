package app;

import messageSystem.Address;
import messageSystem.Addressee;
import messageSystem.Message;

public abstract class MsgToAccService extends Message {
    public MsgToAccService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof AccountService) {
            exec((AccountService) addressee);
        }
    }

    public abstract void exec(AccountService ACService);
}
