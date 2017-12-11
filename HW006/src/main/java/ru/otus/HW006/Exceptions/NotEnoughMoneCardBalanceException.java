package ru.otus.HW006.Exceptions;

public class NotEnoughMoneCardBalanceException extends Exception{
    public NotEnoughMoneCardBalanceException(){
        super("You have not enough money on your Balance");
    }
}
