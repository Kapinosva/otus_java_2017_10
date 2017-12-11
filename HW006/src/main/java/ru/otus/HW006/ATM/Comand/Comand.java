package ru.otus.HW006.ATM.Comand;

import ru.otus.HW006.ATM.ATMIntf;

public abstract class Comand {
    ATMIntf atm = null;

    public abstract void doComand();
    public abstract void printComandPrompt();

    public Comand(ATMIntf atm){
        this.atm = atm;
    }
}
