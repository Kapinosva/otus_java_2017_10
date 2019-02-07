package dataSet.visitor;

import accountService.account.UserAccount;
import dataSet.Address;
import dataSet.Phone;
import dataSet.User;
import dbService.dao.Dao;

public class Saver implements DataSetVisitor {
    private Dao dao;

    public Saver(Dao dao){
        this.dao = dao;
    }

    @Override
    public void accept(Address address) {
        dao.save(address);
    }

    @Override
    public void accept(Phone phone) {
        dao.save(phone);
    }

    @Override
    public void accept(User user) {

        accept(user.getAddress());
        dao.save(user);
        for (Phone phone: user.getPhones()){
            accept(phone);
        }
    }

    @Override
    public void accept(UserAccount user) {

    }
}
