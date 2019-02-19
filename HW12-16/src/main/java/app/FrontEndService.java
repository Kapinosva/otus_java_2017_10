package app;

import messageSystem.Addressee;
import org.eclipse.jetty.websocket.api.Session;

import javax.servlet.http.HttpSession;


public interface FrontEndService extends Addressee {
    void onRegisterUserAnswer(String login, String result);

    void onLoginUserAnswer(String login, String answerResultresult, String sessionId);

    void subscribeOnRegisterUsers(String SessionId, Session usersWebSocketSession, HttpSession httpSession);

    void unSubscribeOnRegisterUsers(String SessionId);

    void handleWebsocketRequest(String data, String SessionId);

}

