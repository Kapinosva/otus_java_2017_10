package ru.otus.HW006.Bank;

import ru.otus.HW006.Utils.Randimize;
import java.util.ArrayList;
import java.util.List;

public class Banks {
    private static List<BankIntf> banks = new ArrayList<>();
    static{
        banks.add(new Bank("VTB"));
        banks.add(new Bank("SberBank"));
        banks.add(new Bank("AlfaBank"));
        banks.add(new Bank("RosSelhoz"));
        banks.add(new Bank("Moscow"));
    }

    public static List<BankIntf> getBanks(){
        return banks;
    }

    public static BankIntf getRandomBank(){
        int tmp = Randimize.getRndNum(banks.size());
        return banks.get(tmp);
    }
}
