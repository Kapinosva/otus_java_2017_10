package accountService;

import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;

import app.AccountService;
import app.MessageSystemContext;
import com.google.gson.Gson;
import dbService.DBService;
import messageSystem.Address;
import messageSystem.MessageSystem;

import java.util.Collection;
public class DBAccountServiceImpl implements AccountService {
    private DBService dbService;
    private MessageSystemContext msContext;
    private Address address;

    public DBAccountServiceImpl(DBService dbService, Address address) {
        this.dbService = dbService;
        this.address = address;
    }

    public void setMsContext(MessageSystemContext msContext){
        this.msContext = msContext;
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public void registerUser(String login, String password) throws DuplicateUserException, EmptyLoginOrPasswordException {
        if (dbService.isRegisteredUserLogin(login)){
            throw new DuplicateUserException(login);
        }else{
            dbService.save(new UserAccount(login,password, ""));
        }
    }

    @Override
    public Collection<UserAccount> getUserList() {
        return dbService.getUserList();
    }

    @Override
    public UserAccount getRegisteredUserById(long id) throws NoSuchUserException {
        UserAccount result = dbService.load(id, UserAccount.class);
        if (result == null){
            throw new NoSuchUserException();
        }
        return result;
    }

    @Override
    public String getRegisteredUserByLogin(String login) throws NoSuchUserException {
        UserAccount result = dbService.getUserByLogin(login);
        if (result == null){
            throw new NoSuchUserException();
        }else {
            Gson gson = new Gson();
            return gson.toJson(result);
        }
    }

    @Override
    public void updateUser(long id, UserAccount newUserInfo) {
        if (newUserInfo.getId() != id ){newUserInfo.setId(id);}
        dbService.save(newUserInfo);
    }

    @Override
    public boolean isRegisteredUserLogin(String login) {
        return dbService.isRegisteredUserLogin(login);
    }

    @Override
    public boolean isRightLoginPasswordPair(String login, String password) throws NoSuchUserException {
        Gson g = new Gson();

        UserAccount user = g.fromJson(getRegisteredUserByLogin(login), UserAccount.class);
        return user.getPassword().equals(password);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }


}
