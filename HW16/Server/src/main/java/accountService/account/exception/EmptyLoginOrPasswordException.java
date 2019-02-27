package accountService.account.exception;

public class EmptyLoginOrPasswordException extends Exception {
    public EmptyLoginOrPasswordException(){
        super("User must have not empty login and password.");
    }
}
