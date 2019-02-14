package app.messages;

import app.FrontEndService;
import app.MsgToFrontend;
import messageSystem.Address;
import webserver.servlets.websocket.UsersWebSocket;

public class MsgLoginUserAnswer extends MsgToFrontend {
    private final String login;
    private final String result;
    private final UsersWebSocket callBackLoginWS;

    public MsgLoginUserAnswer(Address from, Address to, String login, String result, UsersWebSocket callBackLoginWS) {
        super(from, to);
        this.login = login;
        this.result = result;
        this.callBackLoginWS = callBackLoginWS;
    }

    @Override
    public void exec(FrontEndService frontendService) {
        frontendService.isLogindUser(login, result, callBackLoginWS);
    }
}
