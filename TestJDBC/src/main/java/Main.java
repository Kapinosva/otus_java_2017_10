import dataSet.Address;
import dataSet.Phone;
import dataSet.User;
import dbService.DBService;
import dbService.DBServiceH2;
import dbService.DBServiceHibernate;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DBService db = new DBServiceH2();
        try {
            User vasya = new User(55, "Vasya");
            vasya.setPhones(Arrays.asList(new Phone("8 905 710 63 15", vasya)));
            vasya.setAddress(new Address( "Lenina", "88", "13"));

            db.save(vasya);
            System.out.println(vasya);
            User petya = new User(55, "Petya");
            petya.setPhones(Arrays.asList(new Phone("8 910 000 00 10", petya)));
            petya.setAddress(new Address( "Pushkina", "11", "01"));
            db.save(petya);

            vasya = db.load(46, User.class);
            System.out.println(vasya);
        }finally {
            db.disconnect();
        }
    }
}
