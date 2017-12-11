package ru.otus.HW006.Department;

import ru.otus.HW006.ATM.ATM;
import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Bank.Banks;

import java.util.ArrayList;
import java.util.List;
import ru.otus.HW006.Utils.Randimize;

public class Departments {
    private static List<DepartmentIntf> departments = new ArrayList<>();

    public static void fillDepartments(){
        for (BankIntf b : Banks.getBanks()){
            Department d = new Department(b);
            for (int i = 0; i <Randimize.getRndNum(5); i++){
                d.addATM(new ATM(i, b));
            }
            departments.add(d);
        }
    }

    public static DepartmentIntf getRandomDept(){
        return departments.get(Randimize.getRndNum(departments.size()));

    }
}
