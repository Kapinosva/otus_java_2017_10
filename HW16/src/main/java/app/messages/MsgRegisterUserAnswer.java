package app.messages;

import app.FrontEndService;
import app.MsgToFrontend;

public class MsgRegisterUserAnswer extends MsgToFrontend {
    private final String login;
    private final String result;

    public MsgRegisterUserAnswer(String login, String result) {
        this.login = login;
        this.result = result;
    }

    @Override
    public void exec(FrontEndService frontendService) {
        frontendService.onRegisterUserAnswer(login, result);
    }

}
