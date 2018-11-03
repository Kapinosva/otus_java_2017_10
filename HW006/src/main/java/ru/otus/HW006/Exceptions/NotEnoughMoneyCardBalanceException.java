package ru.otus.HW006.Exceptions;

public class NotEnoughMoneyCardBalanceException extends Exception{
    public NotEnoughMoneyCardBalanceException(){
        super("You have not enough money on your Balance");
    }
}
