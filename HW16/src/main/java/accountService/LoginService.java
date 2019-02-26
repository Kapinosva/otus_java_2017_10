package accountService;

import accountService.account.UserAccount;

import javax.servlet.http.HttpSession;

public interface LoginService {
    void loginUser(UserAccount CurrentUser, HttpSession session);
}
