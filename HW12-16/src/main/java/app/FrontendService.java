package app;

import messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void isRegisteredUser(String login, String result);

    void isLogindUser(String login, String result);
}

