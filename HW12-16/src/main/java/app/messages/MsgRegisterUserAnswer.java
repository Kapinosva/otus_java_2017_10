package app.messages;

import app.FrontEndService;
import app.MsgToFrontend;
import messageSystem.Addressee;

public class MsgRegisterUserAnswer extends MsgToFrontend {
    private final String login;
    private final String result;

    public MsgRegisterUserAnswer(Addressee from, Addressee to, String login, String result) {
        super(from, to);
        this.login = login;
        this.result = result;
    }

    @Override
    public void exec(FrontEndService frontendService) {
        frontendService.isRegisteredUser(login, result);
    }
}
