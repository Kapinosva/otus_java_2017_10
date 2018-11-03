package ru.otus.HW006.ATM;

import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Card.CardInrf;
import ru.otus.HW006.Exceptions.NoCardException;
import ru.otus.HW006.Exceptions.NotEnoughMoneyInATMException;
import ru.otus.HW006.Exceptions.NotMultipleSumExceprion;

public interface ATMIntf extends ATMUnitIntf {

    int getId();

    void addCell(int denomination, int count);

    BankIntf getMasterBank();

    void start();

    CardInrf getInsertingCard();

    void printUserPrompt();

    int getCardBalance();

    void printCardBalance();

    void makeWithdraw() throws NoCardException, NotMultipleSumExceprion, NotEnoughMoneyInATMException;

    void insertCard(CardInrf card);

}
