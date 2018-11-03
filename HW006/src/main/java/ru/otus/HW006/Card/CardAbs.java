package ru.otus.HW006.Card;

import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Exceptions.NotEnoughMoneyCardBalanceException;

public abstract class CardAbs implements CardInrf {
    int balance = 0;
    BankIntf issuer = null;
    String number = "";

    private void setBalance(int balance){
        this.balance = balance;
    }

    @Override
    public void withdraw(int sum) throws NotEnoughMoneyCardBalanceException {
        if (sum > getBalance()){
            throw new NotEnoughMoneyCardBalanceException();
        }else{
            setBalance(getBalance() - sum);
        }
    }

    @Override
    public int getBalance(){
        return balance;
    }

    @Override
    public BankIntf getIssuer(){
        return  issuer;
    }

    @Override
    public String getNumber(){
        return  number;
    }
}
