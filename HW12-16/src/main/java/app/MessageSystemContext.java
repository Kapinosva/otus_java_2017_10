package app;

import accountService.AccountService;
import accountService.LoginService;
import messageSystem.Addressee;
import messageSystem.MessageSystem;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private FrontEndService frontAddressee;
    private AccountService asAddressee;//AccountService
    private LoginService lsAddressee;//LoginService

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public FrontEndService getFrontAddressee() {
        return frontAddressee;
    }

    public void setFrontAddressee(FrontEndService frontAddressee) {
        this.frontAddressee = frontAddressee;
        messageSystem.addAddressee(frontAddressee);
        frontAddressee.setMsContext(this);
    }

    public AccountService getAsAddressee() {
        return asAddressee;
    }

    public void setAsAddressee(AccountService asAddressee) {
        this.asAddressee = asAddressee;
        messageSystem.addAddressee(asAddressee);
        asAddressee.setMsContext(this);
    }

    public LoginService getLsAddressee() {
        return lsAddressee;
    }

    public void setLsAddressee(LoginService lsAddressee) {
        this.lsAddressee = lsAddressee;
        messageSystem.addAddressee(lsAddressee);
        lsAddressee.setMsContext(this);
    }
}
