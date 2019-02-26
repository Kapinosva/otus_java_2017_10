package dbService;

import accountService.account.UserAccount;
import dataSet.DataSet;

import java.sql.Connection;
import java.util.Collection;

public interface DBService {
    <T extends DataSet> void save(T dataset);
    <T extends DataSet> T load(long id, Class<T> datasetClass);
    default boolean isRegisteredUserLogin(String login){
        return getUserByLogin(login) != null;
    }
    UserAccount getUserByLogin(String login);
    Collection<UserAccount> getUserList();
    void disconnect();
}

