package accountService;

import accountService.account.exception.NoSuchUserException;
import app.MessageSystemContext;
import messageSystem.MessageSystem;

import javax.servlet.http.HttpSession;

public class LoginServiceImpl implements LoginService {

    private AccountService ac;
    private MessageSystemContext msContext;

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

    @Override
    public String getId() {
        return "LoginService";
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

    @Override
    public void setMsContext(MessageSystemContext msContext) {
        this.msContext = msContext;
    }


}
