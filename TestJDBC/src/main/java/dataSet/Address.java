package dataSet;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address extends DataSet {

    @Column(name = "Street")
    String street;
    @Column(name = "House")
    String house;

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getFlat() {
        return flat;
    }

    @Column(name = "Flat")
    String flat;

    public Address(){

    }
    public Address(String street, String house, String flat) {
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

}
