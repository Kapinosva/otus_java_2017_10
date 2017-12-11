package ru.otus.HW006.Department;

import ru.otus.HW006.ATM.ATMIntf;
import ru.otus.HW006.ATM.ATMUnitIntf;
import ru.otus.HW006.Bank.Bank;
import ru.otus.HW006.Bank.BankIntf;

import java.util.ArrayList;
import java.util.List;

public class Department implements DepartmentIntf {
    private BankIntf0 masterBank = null;
    private List<ATMUnitIntf> ATNs = new ArrayList<>();

    public Department(BankIntf b){
        masterBank = b;
    }

    @Override
    public BankIntf getBank() {
        return masterBank;
    }

    @Override
    public int getBalanceATMs() {
        int sum = 0;
        for (ATMUnitIntf a: ATNs){
            sum += a.getBalance();
        }
        return sum;
    }

    @Override
    public void refillATMs() {
        System.out.println("Refill all ATMS of bank '" + getBank().getName() + "'");
        for (ATMUnitIntf a: ATNs){
            a.refillCells();
        }
    }

    @Override
    public void addATM(ATMUnitIntf ATM) {
        ATNs.add(ATM);
    }
}
