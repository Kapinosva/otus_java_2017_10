package app.messages;

import accountService.LoginService;
import accountService.account.exception.NoSuchUserException;
import app.MsgToLoginService;
import messageSystem.Addressee;

import javax.servlet.http.HttpSession;

public class MsgLoginUser extends MsgToLoginService {
    private final String login;
    private final String password;
    private final HttpSession httpSession;

    public MsgLoginUser(Addressee from, Addressee to, String login, String password, HttpSession httpSession) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.httpSession = httpSession;
    }

    @Override
    public void exec(LoginService lsService) {
        try {
            lsService.loginUser(login, password, httpSession);
        } catch (NoSuchUserException e) {
            lsService.getMS().sendMessage(new MsgLoginUserAnswer(getTo(), getFrom(), login, "User not found "));
        }

        lsService.getMS().sendMessage(new MsgLoginUserAnswer(getTo(), getFrom(), login, "You are loggined as "));
    }
}
