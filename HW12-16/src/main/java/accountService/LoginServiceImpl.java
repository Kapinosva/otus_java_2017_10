package accountService;

import javax.servlet.http.HttpSession;

import accountService.account.exception.NoSuchUserException;
import app.MessageSystemContext;
import messageSystem.Address;
import messageSystem.MessageSystem;


public class LoginServiceImpl implements LoginService {

    private AccountService ac;
    private MessageSystemContext msContext;
    private Address address;

    public LoginServiceImpl(AccountService ac, Address address){
        this.ac = ac;
        this.address = address;
    }

    public void loginUser(String login, String password, HttpSession session) throws NoSuchUserException {
        if ((!ac.isRegisteredUserLogin(login)) || (!ac.isRightLoginPasswordPair(login,password))){
            throw new NoSuchUserException();
        }else{
            session.setAttribute("currentUser", ac.getRegisteredUserByLogin(login));
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

    @Override
    public void setMsContext(MessageSystemContext msContext) {
        this.msContext = msContext;
        msContext.getMessageSystem().addAddressee(this);
    }


}
