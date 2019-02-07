package accountService;

import accountService.account.exception.NoSuchUserException;

import javax.servlet.http.HttpSession;

public interface LoginService {
    void loginUser(String login, String password, HttpSession session) throws NoSuchUserException;
}
