package ru.otus.HW006.ATM.Comand;

import ru.otus.HW006.ATM.ATMIntf;

public class CmdGetCardBalance extends Comand {

    public CmdGetCardBalance(ATMIntf atm) {
        super(atm);
    }

    @Override
    public void doComand(){
        atm.printCardBalance();
    }

    @Override
    public void printComandPrompt() {
        System.out.println("Get card balance");
    }
}
