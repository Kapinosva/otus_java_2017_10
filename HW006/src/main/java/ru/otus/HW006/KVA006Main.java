package ru.otus.HW006;

import ru.otus.HW006.ATM.ATM;
import ru.otus.HW006.ATM.ATMIntf;
import ru.otus.HW006.Bank.Banks;
import ru.otus.HW006.Department.Department;
import ru.otus.HW006.Department.DepartmentIntf;
import ru.otus.HW006.Department.Departments;
import ru.otus.HW006.Utils.UserInput;

import javax.jws.soap.SOAPBinding;

public class KVA006Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Please select Department Or 1 ATM");
            System.out.println("Enter 'Exit' for Exit");
            System.out.println("0 Department");
            System.out.println("1 ATM");
            UserInput.getUserInput();
            if (UserInput.getLastUserintInput() == 0){
                Departments.fillDepartments();
                DepartmentIntf d = Departments.getRandomDept();
                while (true){
                    System.out.println("Please select comand");
                    System.out.println("0 refill all ATMs");
                    System.out.println("1 getBalance all ATMs");
                    UserInput.getUserInput();
                    if (UserInput.getLastUserintInput() == 0){
                        d.refillATMs();
                        System.out.println("All ATMs refilled");
                    }else if (UserInput.getLastUserintInput() == 1){
                        System.out.println("ALL ATMS balance = " + d.getBalanceATMs());
                    }

                }
            }else if (UserInput.getLastUserintInput() == 1){
                ATMIntf atm = new ATM(1, Banks.getRandomBank());
                atm.refillCells();
                atm.start();
            }
        }
    }
}
