package accountService.account.exception;

public class DuplicateUserException extends Exception {
    public DuplicateUserException(){
        super("Duplicate user, please use another login.");
    }
}
