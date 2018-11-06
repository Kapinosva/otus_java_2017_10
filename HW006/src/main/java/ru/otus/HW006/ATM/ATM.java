package ru.otus.HW006.ATM;

import ru.otus.HW006.ATM.Comand.CmdGetCardBalance;
import ru.otus.HW006.ATM.Comand.CmdWithdraw;
import ru.otus.HW006.Bank.BankIntf;

public class ATM extends AbstractATM {

    public ATM(int id, BankIntf masterBank){
        this.id = id;
        this.masterBank = masterBank;
        this.comands.addComand(new CmdGetCardBalance(this));
        this.comands.addComand(new CmdWithdraw(this));
    }

}
