package accountService.account.exception;

import accountService.account.UserAccount;

public class DuplicateUserException extends Exception {
    public DuplicateUserException(){
        super("Duplicate user login, please use another login.");
    }
    public DuplicateUserException(UserAccount user){
        super("Duplicate user login '" + user.getLogin() + "', please use another login.");
    }
    public DuplicateUserException(String login){
        super("Duplicate user login '" + login + "', please use another login.");
    }

}
