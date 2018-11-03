package ru.otus.HW006.Department;

import ru.otus.HW006.ATM.ATMUnitIntf;
import ru.otus.HW006.Bank.BankIntf;


public interface DepartmentIntf {
    BankIntf getBank();
    int getBalanceATMs();
    void fillATMs();
    void addATM(ATMUnitIntf ATM);
    void restoreATMs();
}
