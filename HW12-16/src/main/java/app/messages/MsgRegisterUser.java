package app.messages;

import accountService.AccountService;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import app.MsgToAccService;
import messageSystem.Address;

public class MsgRegisterUser extends MsgToAccService {
    private final String login;
    private final String password;
    private final String REGISTERED_USER = "Registered user ";
    private final String DUPLICATE_LOGIN = "Duplicate user login ";
    private final String EMPTY_LOGIN_OR_PASSWORD = "You have entered empty login or password.";

    public MsgRegisterUser(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(AccountService acService) {
        String result = REGISTERED_USER;
        try {
            acService.registerUser(login, password);
        } catch (DuplicateUserException e) {
            result = DUPLICATE_LOGIN;
        } catch (EmptyLoginOrPasswordException e) {
            result = EMPTY_LOGIN_OR_PASSWORD;
        }
        acService.getMS().sendMessage(new MsgRegisterUserAnswer(getTo(), getFrom(), login, result));
    }
}
