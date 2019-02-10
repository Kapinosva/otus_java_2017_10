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
        try {
            acService.registerUser(login, password);
        } catch (DuplicateUserException e) {
            acService.getMS().sendMessage(new MsgRegisterUserAnswer(getTo(), getFrom(), login, "Duplicate user login "));
        } catch (EmptyLoginOrPasswordException e) {
            acService.getMS().sendMessage(new MsgRegisterUserAnswer(getTo(), getFrom(), "", "You have entered empty login or password."));
        }
        acService.getMS().sendMessage(new MsgRegisterUserAnswer(getTo(), getFrom(), login, "Registered user "));
    }
}
