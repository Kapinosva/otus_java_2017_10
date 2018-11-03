package ru.otus.HW006.ATM;

import ru.otus.HW006.Utils.Randimize;

public class Denomination {
    private static int[] denominations = {10,50,100,500,1000,5000};

    public static int getRndDenomination(){
        return denominations[Randimize.getRndNum(denominations.length)];
    }
}
