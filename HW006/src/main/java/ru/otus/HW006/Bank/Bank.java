package ru.otus.HW006.Bank;

public class Bank implements BankIntf {
    private String name = "";

    public Bank(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
