package ru.otus.HW006;

import ru.otus.HW006.ATM.ATM;
import ru.otus.HW006.ATM.ATMIntf;
import ru.otus.HW006.Bank.Banks;
import ru.otus.HW006.Department.DepartmentIntf;
import ru.otus.HW006.Department.Departments;
import ru.otus.HW006.Utils.UserInput;

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
                    System.out.println("0 fill all ATMs");
                    System.out.println("1 getBalance all ATMs");
                    System.out.println("2 restore ATMs state");
                    UserInput.getUserInput();
                    if (UserInput.getLastUserintInput() == 0){
                        d.fillATMs();
                        System.out.println("All ATMs filled and saved starting state");
                    }else if (UserInput.getLastUserintInput() == 1){
                        System.out.println("ALL ATMS balance = " + d.getBalanceATMs());
                    }else if (UserInput.getLastUserintInput() == 2){
                        d.restoreATMs();
                        System.out.println("restored mementos");
                    }

                }
            }else if (UserInput.getLastUserintInput() == 1){
                ATMIntf atm = new ATM(1, Banks.getRandomBank());
                atm.fillCells();
                atm.start();
            }
        }
    }
}
