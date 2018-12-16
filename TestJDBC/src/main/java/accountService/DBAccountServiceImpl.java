package accountService;

import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;
import context.Context;
import dataSet.User;
import dbService.DBService;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public class DBAccountServiceImpl implements AccountService{
    private Context context;
    private DBService dbService;

    public DBAccountServiceImpl(Context context) {
        this.context = context;
        dbService =  context.get(DBService.class);
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
    public UserAccount getRegisteredUserByLogin(String login) throws NoSuchUserException {
        UserAccount result = dbService.getUserByLogin(login);
        if (result == null){
            throw new NoSuchUserException();
        }else {
            return result;
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
        return getRegisteredUserByLogin(login).getPassword().equals(password);
    }
}
