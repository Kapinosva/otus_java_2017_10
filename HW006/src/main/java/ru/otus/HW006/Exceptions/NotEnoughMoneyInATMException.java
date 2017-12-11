package ru.otus.HW006.Exceptions;

public class NotEnoughMoneyInATMException extends Exception {
    public NotEnoughMoneyInATMException() {
        super("Too big sum! Atm cannot give it you.");
    }
}
