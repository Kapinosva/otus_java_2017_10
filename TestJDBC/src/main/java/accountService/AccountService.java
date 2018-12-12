package accountService;


import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

public interface AccountService {
    void registerUser(String login, String password) throws DuplicateUserException, EmptyLoginOrPasswordException;
    void loginUser(String login, String password, HttpSession session) throws NoSuchUserException;
    Collection<UserAccount> getUserList();
    UserAccount getRegisteredUserById(long id);
    void updateUser(long id, UserAccount newUserInfo);

}

