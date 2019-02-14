package app;

import accountService.LoginService;
import messageSystem.Address;
import messageSystem.Addressee;
import messageSystem.Message;

public abstract class MsgToLoginService extends Message {
    public MsgToLoginService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof LoginService) {
            exec((LoginService) addressee);
        }
    }

    public abstract void exec(LoginService ACService);
}
