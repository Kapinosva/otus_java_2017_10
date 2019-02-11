package app.messages;

import accountService.LoginService;
import accountService.account.exception.NoSuchUserException;
import app.MsgToLoginService;
import messageSystem.Addressee;
import webserver.servlets.websocket.UsersWebSocket;

import javax.servlet.http.HttpSession;

public class MsgLoginUser extends MsgToLoginService {
    private final String login;
    private final String password;
    private final HttpSession httpSession;
    private final UsersWebSocket callBackLoginWS;

    public MsgLoginUser(Addressee from, Addressee to, String login, String password, HttpSession httpSession, UsersWebSocket callBackLoginWS) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.httpSession = httpSession;
        this.callBackLoginWS = callBackLoginWS;
    }

    @Override
    public void exec(LoginService lsService) {
        String result = "You are loggined as ";
        try {
            lsService.loginUser(login, password, httpSession);
        } catch (NoSuchUserException e) {
            result = "User not found ";
        }
        lsService.getMS().sendMessage(new MsgLoginUserAnswer(getTo(), getFrom(), login, result, callBackLoginWS));
    }
}
