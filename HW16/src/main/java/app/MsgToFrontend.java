package app;

import messageSystem.Addressee;
import messageSystem.Message;

public abstract class MsgToFrontend extends Message {
    @Override
    public void exec(Addressee addressee) {
        //this message can be processed only by FrontEndService
        if (addressee instanceof FrontEndService) {
            exec((FrontEndService) addressee);
        }
    }

    public abstract void exec(FrontEndService frontendService);
}