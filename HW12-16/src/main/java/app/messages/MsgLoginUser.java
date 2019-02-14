package app.messages;

import accountService.LoginService;
import accountService.account.exception.NoSuchUserException;
import app.MsgToLoginService;
import messageSystem.Address;
import webserver.servlets.websocket.UsersWebSocket;

import javax.servlet.http.HttpSession;

public class MsgLoginUser extends MsgToLoginService {
    private final String login;
    private final String password;
    private final HttpSession httpSession;
    private final UsersWebSocket callBackLoginWS;
    private final String USER_NOT_FOUND = "User not found: ";
    private final String LOGINNED_USER = "You are loggined as ";

    public MsgLoginUser(Address from, Address to, String login, String password, HttpSession httpSession, UsersWebSocket callBackLoginWS) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.httpSession = httpSession;
        this.callBackLoginWS = callBackLoginWS;
    }

    @Override
    public void exec(LoginService lsService) {
        String result = LOGINNED_USER;
        try {
            lsService.loginUser(login, password, httpSession);
        } catch (NoSuchUserException e) {
            result = USER_NOT_FOUND;
        }
        lsService.getMS().sendMessage(new MsgLoginUserAnswer(getTo(), getFrom(), login, result, callBackLoginWS));
    }
}
