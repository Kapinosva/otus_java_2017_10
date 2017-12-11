package ru.otus.HW006.Card;

import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Exceptions.NotEnoughMoneCardBalanceException;

public interface CardInrf {
    int getBalance();
    BankIntf getIssuer();
    String getNumber();
    void withdraw(int sum) throws NotEnoughMoneCardBalanceException;
}
