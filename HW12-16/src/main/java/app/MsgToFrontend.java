package app;

import messageSystem.Addressee;
import messageSystem.Message;


public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Addressee from, Addressee to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontEndService) {
            exec((FrontEndService) addressee);
        }
    }

    public abstract void exec(FrontEndService frontendService);
}