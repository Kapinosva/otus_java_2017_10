package app.messages;

import app.AccountService;
import accountService.account.exception.DuplicateUserException;
import accountService.account.exception.EmptyLoginOrPasswordException;
import app.MsgToAccService;
import messageSystem.Address;

public class MsgRegisterUser extends MsgToAccService {
    private final String login;
    private final String password;

    public MsgRegisterUser(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(AccountService acService) {
        String result = common.Const.REGISTERED_USER;
        try {
            acService.registerUser(login, password);
        } catch (DuplicateUserException e) {
            result = common.Const.DUPLICATE_LOGIN;
        } catch (EmptyLoginOrPasswordException e) {
            result = common.Const.EMPTY_LOGIN_OR_PASSWORD;
        }
        acService.getMS().sendMessage(new MsgRegisterUserAnswer(getTo(), getFrom(), login, result));
    }
}
