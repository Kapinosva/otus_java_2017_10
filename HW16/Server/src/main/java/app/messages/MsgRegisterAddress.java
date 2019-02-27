package app.messages;

import accountService.account.exception.NoSuchUserException;
import app.AccountService;
import app.MsgToAccService;
import app.MsgToServer;
import common.Const;
import messageSystem.Address;
import messageSystem.MessageServer;
import org.json.simple.JSONObject;


public class MsgRegisterAddress extends MsgToServer {
    private final int port;

    public MsgRegisterAddress(Address from, Address to, int port) {
        super(from, to);
        this.port = port;
    }

    @Override
    public void exec(MessageServer messageServer) {
        messageServer.setAddressPort(getFrom(),port);
    }

}
