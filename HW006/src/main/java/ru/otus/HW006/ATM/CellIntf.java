package ru.otus.HW006.ATM;

public interface CellIntf {
    int getBalance();
    int getDenomination();
    int getCount();
    int withdraw(int sum);
}
