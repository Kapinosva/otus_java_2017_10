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
            e.printStackTrace();
        } catch (NotMultipleSumExceprion notMultipleSumExceprion) {
            notMultipleSumExceprion.printStackTrace();
        } catch (NotEnoughMoneyInATMException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printComandPrompt() {
        System.out.println("WithDraw");
    }
}
