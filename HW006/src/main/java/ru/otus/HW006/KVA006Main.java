package ru.otus.HW006;

import ru.otus.HW006.ATM.ATM;
import ru.otus.HW006.ATM.ATMIntf;
import ru.otus.HW006.Bank.Banks;
import ru.otus.HW006.Department.Department;
import ru.otus.HW006.Department.Departments;
import ru.otus.HW006.Utils.UserInput;

public class KVA006Main {
    private static final int DepartmentSelect = 0;
    private static final int ATMSelect = 1;

    private static final int FillATMSSelect = 0;
    private static final int ATMsBalanceSelect = 1;
    private static final int RestoreATMsSelect = 2;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Please select 0-DepartmentImpl Or 1-ATM");
            System.out.println("Enter 'Exit' for Exit");
            System.out.println("0 DepartmentImpl");
            System.out.println("1 ATM");
            UserInput.getUserInput();
            if (UserInput.getLastUserintInput() == DepartmentSelect){
                Departments.fillDepartments();
                Department d = Departments.getRandomDept();
                while (true){
                    System.out.println("Please select comand");
                    System.out.println("0 fill all ATMs");
                    System.out.println("1 getBalance all ATMs");
                    System.out.println("2 restore ATMs state");
                    UserInput.getUserInput();
                    if (UserInput.getLastUserintInput() == FillATMSSelect){
                        d.fillATMs();
                        System.out.println("All ATMs filled and saved starting state");
                    }else if (UserInput.getLastUserintInput() == ATMsBalanceSelect){
                        System.out.println("ALL ATMS balance = " + d.getBalanceATMs());
                    }else if (UserInput.getLastUserintInput() == RestoreATMsSelect){
                        d.restoreATMs();
                        System.out.println("restored mementos");
                    }

                }
            }else if (UserInput.getLastUserintInput() == ATMSelect){
                ATMIntf atm = new ATM(1, Banks.getRandomBank());
                atm.fillCells();
                atm.start();
            }
        }
    }
}
