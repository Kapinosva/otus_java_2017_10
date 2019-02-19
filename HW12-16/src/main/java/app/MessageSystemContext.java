package app;

import messageSystem.Address;
import messageSystem.MessageSystem;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontEndServiceAddress;
    private Address accountServiceAddress;

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
    }

    public Address getAccountServiceAddress() {
        return accountServiceAddress;
    }

    public void setAccountServiceAddress(Address accountServiceAddress) {
        this.accountServiceAddress = accountServiceAddress;
    }
}
