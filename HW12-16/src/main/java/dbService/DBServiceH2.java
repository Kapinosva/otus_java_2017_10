package dbService;

import accountService.account.UserAccount;
import dataSet.Address;
import dataSet.DataSet;
import dataSet.Phone;
import dataSet.User;

import dataSet.dataSetSQL.DataSetSQLs;
import dataSet.visitor.Loader;
import dataSet.visitor.Saver;
import dbService.dao.Dao;
import org.h2.jdbcx.JdbcDataSource;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.Collection;

public class DBServiceH2 implements DBService {

    private Connection connection;
    private Executor executor;

    public DBServiceH2(){
        try {
            String url = "jdbc:h2:./h2db";
            String name = "sa";
            String pass = "";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);
            connection = DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executor = new Executor(connection);
        executor.setShowSQL(true);
        try {
            executor.execute(DataSetSQLs.getDataSetSQL(User.class).getCreateSQL());
            executor.execute(DataSetSQLs.getDataSetSQL(Phone.class).getCreateSQL());
            executor.execute(DataSetSQLs.getDataSetSQL(Address.class).getCreateSQL());
        } catch (SQLException e) {
            System.out.println("Table is not created");
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> void save(T dataset) {
        Dao dao = new Dao(connection, executor);
        dataset.doService(new Saver(dao));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> datasetClass) {
        Dao dao = new Dao(connection, executor);
        T result = dao.load(id,datasetClass);
        result.doService(new Loader(dao));
        return result;
    }

    @Override
    public UserAccount getUserByLogin(String login) {
        throw new NotImplementedException();
    }

    @Override
    public Collection<UserAccount> getUserList() {
        throw new NotImplementedException();
    }


    @Override
    public void disconnect(){
        try {
            if (!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
