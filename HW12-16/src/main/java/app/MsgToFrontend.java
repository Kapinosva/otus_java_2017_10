package app;

import messageSystem.Addressee;
import messageSystem.Message;


public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Addressee from, Addressee to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}