package ru.otus.HW006.Utils;

import java.util.Random;

public class Randimize {
    private static Random rnd = new Random();

    public static int getRndNum(int max){
        return rnd.nextInt(max);
    }

}
