package app;

import accountService.AccountService;
import messageSystem.Addressee;
import messageSystem.Message;

public abstract class MsgToAccService extends Message {
    public MsgToAccService(Addressee from, Addressee to) {
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
