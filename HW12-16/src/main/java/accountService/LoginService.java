package accountService;

import accountService.account.exception.NoSuchUserException;
import messageSystem.Addressee;

import javax.servlet.http.HttpSession;

public interface LoginService  extends Addressee {
    void loginUser(String login, String password, HttpSession session) throws NoSuchUserException;
}
