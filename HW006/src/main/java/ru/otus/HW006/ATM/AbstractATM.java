package ru.otus.HW006.ATM;

import ru.otus.HW006.ATM.Comand.Comand;
import ru.otus.HW006.ATM.Comand.Comands;
import ru.otus.HW006.ATM.cell.CellImpl;
import ru.otus.HW006.ATM.cell.CellCareTaker;
import ru.otus.HW006.ATM.cell.Cell;
import ru.otus.HW006.Card.Cards;
import ru.otus.HW006.Exceptions.*;
import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Card.CardInrf;
import ru.otus.HW006.Utils.Randimize;
import ru.otus.HW006.Utils.UserInput;

import java.util.*;

public abstract class AbstractATM implements ATMIntf{
    private CardInrf insertingCard = null;
    BankIntf masterBank = null;
    int id = 0;

    private List<Cell> cells = new ArrayList<>();

    Comands comands = new Comands();

    private CellCareTaker cellCareTaker= new CellCareTaker();

    @Override
    public void printCardBalance(){
        System.out.println("Your Card Balance = " + getCardBalance());
    }

    @Override
    public void start(){
        printUserPrompt();
        while(true) {
            Comand selectingComand = selectCommand();
            selectingComand.doComand();
        }
    }

    @Override
    public void addCell(int denomination, int count){
        Cell cell = new CellImpl(denomination, count);
        cells.add(cell);
        Collections.sort(cells, (c1, c2)-> Integer.compare(c2.getDenomination(), c1.getDenomination()));
        for(Cell c: cells){
            System.out.println(c.getDenomination()+ " * "+ c.getCount() + " = " + c.getBalance());
        }
        System.out.println("----------------------------");
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public CardInrf getInsertingCard(){
        return insertingCard;
    }

    @Override
    public BankIntf getMasterBank() {
        return masterBank;
    }

    @Override
    public void printUserPrompt(){
        System.out.println("Welcomme. This is ATM of '" + masterBank.getName() + "' bank.");
        System.out.println("If you want to Exit - type in 'Exit'");
    };

    private Comand selectCommand(){
        if (insertingCard == null){
            do {
                System.out.println("Please select your card from list below");
                for(CardInrf c: Cards.getCards()){
                    System.out.println("Number = " + c.getNumber());
                }
                UserInput.getUserInput();
            }while ((!Cards.numberInCards(UserInput.getLastUserInput())));
            insertCard(Cards.getCard(UserInput.getLastUserInput()));
        }
        System.out.println("Please select comand.");
        do{
            comands.printComands();
            UserInput.getUserInput();
        }while ((!comands.isIDInComandList(UserInput.getLastUserintInput())));
        return comands.get(UserInput.getLastUserintInput());
    }

    @Override
    public void insertCard(CardInrf card){
        insertingCard = card;
    }

    @Override
    public int getCardBalance(){
        return insertingCard.getBalance();
    }

    private void withdraw(int sum){
        int rest = 0;
        for(Cell c: cells){
            rest = c.withdraw(sum);
            if (rest == 0){
                break;
            }
        }
    }

    @Override
    public void makeWithdraw() throws NoCardException, NotMultipleSumExceprion, NotEnoughMoneyInATMException {
        if (insertingCard == null){
            throw new NoCardException();
        }else {
            int sum = 0;
            while(true){
                System.out.println("Please, enter the amount of money =>");
                UserInput.getUserInput();
                if (UserInput.getLastUserintInput() > 0) {
                    sum = UserInput.getLastUserintInput();
                    break;
                }
            }
            try {
                if (canWithdraw(sum)){
                    withdraw(sum);
                    insertingCard.withdraw(sum);
                    System.out.println("Withdrawed " + sum );
                }
            } catch (NotEnoughMoneyCardBalanceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public int getBalance(){
        int balance = 0;
        for(Cell c: cells){
            balance = balance + c.getBalance();
        }
        return balance;
    }

    @Override
    public void fillCells(){
        int cellcnt = Randimize.getRndNum(9) + 1;
        cells.clear();
        for(int i = 0; i < cellcnt; i++){
            int cellDenomination = Denomination.getRndDenomination();
            int cellCount = Randimize.getRndNum(50);
            addCell(cellDenomination, cellCount);
        }
        System.out.println("=========================");
    }

    private boolean isMultipleSum(int sum){
        Map<Integer, Integer> cellsMap = new HashMap<>();
        fillCellMap(cellsMap);
        for(Integer key : cellsMap.keySet()){
            if ((sum % key) == 0){
                return  true;
            }
        }
        return false;
    }

    private boolean canWithdraw(int sum) throws NotMultipleSumExceprion, NotEnoughMoneyInATMException, NotEnoughMoneyCardBalanceException {
        if (sum > getBalance()){
            throw new NotEnoughMoneyInATMException();
        }else if(!isMultipleSum(sum)){
            throw new NotMultipleSumExceprion();
        }else if (getCardBalance() < sum){
            throw new NotEnoughMoneyCardBalanceException();
        }else {
            return true;
        }
    }

    @Override
    public void saveCells(){
        for (Cell cell :cells){
            cellCareTaker.put(cell, cell.saveMemento());
        }
    }

    @Override
    public void restoreCells(){
        for (Cell cell :cells){
            cell.restoreMemento(cellCareTaker.get(cell));
        }
    }

    private void fillCellMap(Map<Integer, Integer> cellsMap){
        cellsMap.clear();;
        for(Cell cell: cells){
            if (cellsMap.containsKey(cell.getDenomination())){
                cellsMap.put(cell.getDenomination(), cell.getCount() + cellsMap.get(cell.getDenomination()));
            }else{
                cellsMap.put(cell.getDenomination(),cell.getCount());
            }
        }
    }

}
