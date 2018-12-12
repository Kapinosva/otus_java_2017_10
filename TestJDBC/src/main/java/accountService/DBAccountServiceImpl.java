package accountService;

import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;
import context.Context;
import dbService.DBService;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public class DBAccountServiceImpl implements AccountService {
    private Context context;

    public DBAccountServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void registerUser(String login, String password) throws DuplicateUserException, EmptyLoginOrPasswordException {
        DBService dbService =  context.get(DBService.class);
        if (dbService.isRegisteredUserLogin(login)){
            throw new DuplicateUserException();
        }else{
            dbService.save(new UserAccount(login,password, ""));
        }
    }

    @Override
    public void loginUser(String login, String password, HttpSession session) throws NoSuchUserException {
        DBService dbService =  context.get(DBService.class);
        if ((!dbService.isRegisteredUserLogin(login)) || (!dbService.getUserByLogin(login).getPassword().equals(password))){
            throw new NoSuchUserException();
        }else{
            session.setAttribute("currentUser", dbService.getUserByLogin(login));
        }

    }

    @Override
    public Collection<UserAccount> getUserList() {
        DBService dbService =  context.get(DBService.class);
        return dbService.getUserList();
    }

    @Override
    public UserAccount getRegisteredUserById(long id) {
        DBService dbService =  context.get(DBService.class);
        return dbService.load(id, UserAccount.class);
    }

    @Override
    public void updateUser(long id, UserAccount newUserInfo) {
        DBService dbService =  context.get(DBService.class);
        newUserInfo.setId(id);
        dbService.save(newUserInfo);
    }
}
