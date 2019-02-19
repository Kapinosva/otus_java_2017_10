package app.messages;

import app.AccountService;
import accountService.account.exception.NoSuchUserException;
import app.MsgToAccService;
import common.Const;
import messageSystem.Address;
import org.json.simple.JSONObject;


public class MsgLoginUser extends MsgToAccService {
    private final String login;
    private final String password;
    private final String SessionId;

    public MsgLoginUser(Address from, Address to, String login, String password, String SessionId) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.SessionId = SessionId;
    }

    @Override
    public void exec(AccountService accountService) {
        JSONObject result = new JSONObject();
        result.put("result",common.Const.LOGINNED_USER);
        String user = null;
        try {
            user = accountService.getRegisteredUserByLogin(login);
        } catch (NoSuchUserException e) {
            result.put("result", Const.USER_NOT_FOUND);
        }
        result.put("user", user);
        accountService.getMS().sendMessage(new MsgLoginUserAnswer(getTo(), getFrom(), login, result.toJSONString(), SessionId));
    }
}
