package app.messages;

import app.FrontEndService;
import app.MsgToFrontend;

public class MsgLoginUserAnswer extends MsgToFrontend {
    private final String login;
    private final String result;
    private final String sessionId;

    public MsgLoginUserAnswer(String login, String result, String sessionId) {
        this.login = login;
        this.result = result;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(FrontEndService frontendService) {
        frontendService.onLoginUserAnswer(login, result, sessionId);
    }
}
