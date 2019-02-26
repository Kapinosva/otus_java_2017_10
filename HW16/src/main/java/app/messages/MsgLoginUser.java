package app.messages;

import accountService.account.exception.NoSuchUserException;
import app.AccountService;
import app.MsgToAccService;
import common.Const;
import org.json.simple.JSONObject;


public class MsgLoginUser extends MsgToAccService {
    private final String login;
    private final String password;
    private final String sessionId;

    public MsgLoginUser(String login, String password, String sessionId) {
        this.login = login;
        this.password = password;
        this.sessionId = sessionId;
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
        accountService.getSocketMsgWorker().send(new MsgLoginUserAnswer(login, result.toJSONString(), sessionId));
    }

}
