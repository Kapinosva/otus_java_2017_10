package dbService.dao;

import dataSet.Address;
import dataSet.DataSet;
import dataSet.Phone;
import dataSet.User;
import dbService.Executor;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;

public class UserDao extends Dao{
    public UserDao(Connection connection, Executor executor){
        super(connection, executor);
    }
    public void saveUser(User user) {
        super.save(user.getAddress());
        super.save(user);
        for (Phone phone: user.getPhones()){
            super.save(phone);
        }
    }
    public User loadUser(long id) {
        User result = super.load(id, User.class);
        result.setAddress(super.load(result.getAddress().getId(), Address.class));
        return result;
    }

}
