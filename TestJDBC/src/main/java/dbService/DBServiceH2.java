package dbService;

import dataSet.Address;
import dataSet.DataSet;
import dataSet.Phone;
import dataSet.User;

import dataSet.dataSetSQL.DataSetSQL;
import dataSet.dataSetSQL.DataSetSQLs;
import dataSet.visitor.Saver;
import dbService.dao.Dao;
import dbService.dao.UserDao;
import org.h2.jdbcx.JdbcDataSource;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

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
        Dao dao = new Dao(connection, executor);;
        dataset.doService(new Saver(dao));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> datasetClass) {

        if (datasetClass.equals(User.class)){
            UserDao dao = new UserDao(connection, executor);
            return (T)dao.loadUser(id);
        }else {
            Dao dao = new Dao(connection, executor);
            return dao.load(id,datasetClass);
        }
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
