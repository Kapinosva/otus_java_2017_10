package ru.otus.HW006.ATM.Comand;

import ru.otus.HW006.ATM.ATMIntf;
import ru.otus.HW006.Exceptions.NoCardException;
import ru.otus.HW006.Exceptions.NotEnoughMoneyInATMException;
import ru.otus.HW006.Exceptions.NotMultipleSumExceprion;
import ru.otus.HW006.Exceptions.TODOException;

public class CmdWithdraw extends Comand{

    public CmdWithdraw(ATMIntf atm) {
        super(atm);
    }

    @Override
    public void  doComand(){
        try {
            atm.makeWithdraw();
        } catch (NoCardException e) {
            System.out.println("No card in ATM. Cannot withdraw.");;
        } catch (NotMultipleSumExceprion e) {
            System.out.println("Not multiple sum requested. Cannot withdraw.");
        } catch (NotEnoughMoneyInATMException e) {
            System.out.println("Not enough money in ATM.");
        }
    }

    @Override
    public void printComandPrompt() {
        System.out.println("WithDraw");
    }
}
