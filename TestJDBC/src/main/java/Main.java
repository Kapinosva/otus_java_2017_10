import accountService.*;
import accountService.account.exception.NoSuchUserException;
import context.Context;
import dataSet.Address;
import dataSet.Phone;
import dataSet.User;
import dbService.DBService;
import dbService.DBServiceH2;
import dbService.DBServiceHibernate;
import webserver.MyWebServer;
import webserver.MyWebServerImpl;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.add(MyWebServer.class, new MyWebServerImpl(context));
        context.add(DBService.class, new DBServiceHibernate());
        //context.add(AccountService.class, new AccountServiceImpl());
        context.add(AccountService.class, new DBAccountServiceImpl(context));
        context.add(LoginService.class, new LoginServiceImpl(context.get(AccountService.class)));
        MyWebServer server = context.get(MyWebServer.class);
        server.start();
    }
}
