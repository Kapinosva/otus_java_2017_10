package dataSet;


import dataSet.visitor.DataSetVisitor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class User extends DataSet {
    @Column(name = "age")
    private int age;
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phoneMaster")
    private List<Phone> phones;

    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address= address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public User(){

    }

    public User(int age, String name){
        this.age = age;
        this.name = name;
    }
    public User(int age, String name, List<Phone> phones){
        this.phones = phones;
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id : " + this.getId() +
                "\nname : " + this.name +
                "\nage : " + this.age  +
                " \nPHONES: " + ((phones == null) ? "" : phones.stream().map(s->s.getPhoneNumber()).collect(Collectors.joining(", ")))+
                " \nAdress: " + ((address == null) ? "" : address.getStreet() + " " + address.getHouse() + " " + address.getFlat());
    }

    @Override
    public void doService(DataSetVisitor visitor) {
        visitor.accept(this);
    }
}
