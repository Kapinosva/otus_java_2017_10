package app.messages;

import accountService.AccountService;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import app.MsgToAccService;
import messageSystem.Addressee;

public class MsgRegisterUser extends MsgToAccService {
    private final String login;
    private final String password;

    public MsgRegisterUser(Addressee from, Addressee to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(AccountService acService) {
        String result = "Registered user ";
        try {
            acService.registerUser(login, password);
        } catch (DuplicateUserException e) {
            result = "Duplicate user login ";
        } catch (EmptyLoginOrPasswordException e) {
            result = "You have entered empty login or password.";
        }
        acService.getMS().sendMessage(new MsgRegisterUserAnswer(getTo(), getFrom(), login, result));
    }
}
