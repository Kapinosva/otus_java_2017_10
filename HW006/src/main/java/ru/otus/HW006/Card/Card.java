package ru.otus.HW006.Card;

import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Card.CardAbs;

public class Card extends CardAbs {

    public Card(int balance, BankIntf issuer, String number){
        this.balance = balance;
        this.issuer = issuer;
        this.number = number;
    }

}
