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

    Collection<UserAccount> getUserList();

    UserAccount getRegisteredUserById(long id) throws NoSuchUserException;

    UserAccount getRegisteredUserByLogin(String login) throws NoSuchUserException;

    void updateUser(long id, UserAccount newUserInfo) throws NoSuchUserException;

    default void updateUser(UserAccount newUserInfo) throws NoSuchUserException{
        updateUser(newUserInfo.getId(),newUserInfo);
    }
    boolean isRegisteredUserLogin(String login);
    boolean isRightLoginPasswordPair(String login, String password) throws NoSuchUserException;

}

