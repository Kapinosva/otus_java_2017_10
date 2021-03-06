package app;

import java.util.Collection;

import accountService.account.UserAccount;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import accountService.account.exception.NoSuchUserException;
import messageSystem.Addressee;


public interface AccountService extends Addressee{
    void registerUser(String login, String password) throws DuplicateUserException, EmptyLoginOrPasswordException;

    Collection<UserAccount> getUserList();

    UserAccount getRegisteredUserById(long id) throws NoSuchUserException;

    String getRegisteredUserByLogin(String login) throws NoSuchUserException;

    void updateUser(long id, UserAccount newUserInfo) throws NoSuchUserException;

    default void updateUser(UserAccount newUserInfo) throws NoSuchUserException{
        updateUser(newUserInfo.getId(),newUserInfo);
    }
    boolean isRegisteredUserLogin(String login);
    boolean isRightLoginPasswordPair(String login, String password) throws NoSuchUserException;

}

