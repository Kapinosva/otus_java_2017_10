package accountService.account.exception;

public class NoSuchUserException extends Exception{
    public NoSuchUserException(){
        super("No such user. Bad login or password.");
    }
}
