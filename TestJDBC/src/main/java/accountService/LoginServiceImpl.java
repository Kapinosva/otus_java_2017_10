package accountService;

import accountService.account.exception.NoSuchUserException;
import dbService.DBService;

import javax.servlet.http.HttpSession;

public class LoginServiceImpl implements LoginService {

    AccountService ac;

    public LoginServiceImpl(AccountService ac){
        this.ac = ac;
    }
    public void loginUser(String login, String password, HttpSession session) throws NoSuchUserException {
        if ((!ac.isRegisteredUserLogin(login)) || (!ac.isRightLoginPasswordPair(login,password))){
            throw new NoSuchUserException();
        }else{
            session.setAttribute("currentUser", ac.getRegisteredUserByLogin(login));
        }
    }

}
