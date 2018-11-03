package ru.otus.HW006.ATM.cell;

public interface CellIntf {
    int getBalance();
    int getDenomination();
    int getCount();
    int withdraw(int sum);
    CellMemento saveMemento();
    void restoreMemento(CellMemento cellMemento);
}
