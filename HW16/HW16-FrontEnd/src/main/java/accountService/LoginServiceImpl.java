package accountService;

import javax.servlet.http.HttpSession;

import accountService.account.UserAccount;

public class LoginServiceImpl implements LoginService {

    public void loginUser(UserAccount CurrentUser, HttpSession session){
        session.setAttribute("currentUser", CurrentUser);
    }

}
