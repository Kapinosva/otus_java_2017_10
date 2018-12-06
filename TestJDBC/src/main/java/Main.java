import dataSet.Address;
import dataSet.Phone;
import dataSet.User;
import dbService.DBService;
import dbService.DBServiceH2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DBService db = new DBServiceH2();
        try {
            User vasya = new User(55, "Vasya");
            vasya.setPhones(Arrays.asList(new Phone("8 905 710 63 15", vasya),
                    new Phone("8 3333 333 333 22", vasya)));
            vasya.setAddress(new Address( "Lenina", "88", "13"));

            db.save(vasya);
            System.out.println(vasya);

            User petya = new User(55, "Petya");
            petya.setPhones(Arrays.asList(new Phone("8 910 000 00 10", petya)
            ,new Phone("8 99 99 99 9 99 9 99 9 9 ", petya)));
            petya.setAddress(new Address( "Pushkina", "11", "01"));

            db.save(petya);
            System.out.println(petya);

            vasya = db.load(1, User.class);
            System.out.println(vasya);
        }finally {
            db.disconnect();
        }
    }
}
