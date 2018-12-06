package dataSet;

import dataSet.visitor.DataSetVisitor;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone extends DataSet {

    public void setPhoneMaster(User phoneMaster) {
        this.phoneMaster = phoneMaster;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private User phoneMaster;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Phone(){
    }

    public Phone(String phoneNumber, User phoneMaster) {
        this.phoneMaster = phoneMaster;
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void doService(DataSetVisitor visitor) {
        visitor.accept(this);
    }

}
