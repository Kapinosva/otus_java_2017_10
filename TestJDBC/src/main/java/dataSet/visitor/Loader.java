package dataSet.visitor;

import accountService.account.UserAccount;
import dataSet.Address;
import dataSet.Phone;
import dataSet.User;
import dbService.dao.Dao;

public class Loader implements DataSetVisitor {
    private Dao dao;

    public Loader(Dao dao){
        this.dao = dao;
    }

    @Override
    public void accept(Address address) {
        address = dao.load(address.getId(), Address.class);
    }

    @Override
    public void accept(Phone phone) {
        phone = dao.load(phone.getId(), Phone.class);
    }

    @Override
    public void accept(User user) {
        user.setAddress(dao.load( user.getAddressId(),Address.class));
        user.setPhones(dao.loadList("phoneMaster_id", user.getId(),Phone.class));
    }

    @Override
    public void accept(UserAccount user) {

    }
}
