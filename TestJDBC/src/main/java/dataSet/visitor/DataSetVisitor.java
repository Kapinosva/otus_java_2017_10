package dataSet.visitor;

import dataSet.Address;
import dataSet.Phone;
import dataSet.User;

public interface DataSetVisitor {
    void accept(Address address);
    void accept(Phone phone);
    void accept(User user);
}
