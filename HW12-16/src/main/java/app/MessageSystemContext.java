package app;

import messageSystem.Address;
import messageSystem.MessageSystem;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontEndServiceAddress;
    private Address accountServiceAddress;
    private Address loginServiceAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontEndServiceAddress() {
        return frontEndServiceAddress;
    }

    public void setFrontEndServiceAddress(Address frontEndAddress) {
        this.frontEndServiceAddress = frontEndAddress;
        //messageSystem.addAddressee(frontAddressee);
        //frontAddressee.setMsContext(this);
    }

    public Address getAccountServiceAddress() {
        return accountServiceAddress;
    }

    public void setAccountServiceAddress(Address accountServiceAddress) {
        this.accountServiceAddress = accountServiceAddress;
        //messageSystem.addAddressee(asAddressee);
        //asAddressee.setMsContext(this);
    }

    public Address getLoginServiceAddress() {
        return loginServiceAddress;
    }

    public void setLoginServiceAddress(Address loginServiceAddress) {
        this.loginServiceAddress = loginServiceAddress;
        //messageSystem.addAddressee(lsAddressee);
        //lsAddressee.setMsContext(this);
    }
}
