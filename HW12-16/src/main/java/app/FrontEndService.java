package app;

import messageSystem.Addressee;
import webserver.servlets.websocket.UsersWebSocket;

import javax.servlet.http.HttpSession;

public interface FrontEndService extends Addressee {
    void isRegisteredUser(String login, String result);

    void isLogindUser(String login, String result, UsersWebSocket callBackLoginWS);

    void subscribeOnRegisterUsers(UsersWebSocket usersWebSocket);

    void unSubscribeOnRegisterUsers(UsersWebSocket usersWebSocket);

    void handleWebsocketRequest(String data, HttpSession httpSession, UsersWebSocket usersWebSocket);

}

