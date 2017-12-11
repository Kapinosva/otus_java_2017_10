package ru.otus.HW006.ATM;

import ru.otus.HW006.ATM.Comand.Comand;
import ru.otus.HW006.ATM.Comand.Comands;
import ru.otus.HW006.Card.Cards;
import ru.otus.HW006.Exceptions.*;
import ru.otus.HW006.Bank.BankIntf;
import ru.otus.HW006.Card.CardInrf;
import ru.otus.HW006.Utils.Randimize;
import ru.otus.HW006.Utils.UserInput;

import java.util.*;

public abstract class ATMAbs implements ATMIntf{
    private CardInrf insertingCard = null;
    BankIntf masterBank = null;
    int id = 0;
    private List<CellIntf> cells = new ArrayList<>();
    private Map<Integer, Integer> cellsMap = new HashMap<>();
    Comands comands = new Comands();

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
        cells.add(new Cell(denomination, count));

        //cells.sort();
        Collections.sort(cells, (c1, c2)->{return Integer.compare(c2.getDenomination(), c1.getDenomination());});
        for(CellIntf c: cells){
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
        }while ((!comands.idInComands(UserInput.getLastUserintInput())));
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
        for(CellIntf c: cells){
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
            } catch (NotEnoughMoneCardBalanceException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public int getBalance(){
        int balance = 0;
        for(CellIntf c: cells){
            balance = balance + c.getBalance();
        }
        return balance;
    }

    @Override
    public void refillCells(){
        int cellcnt = Randimize.getRndNum(9) + 1;
        cells.clear();
        cellsMap.clear();;
        for(int i = 0; i < cellcnt; i++){
            int cellDenomination = Denomination.getRndDenomination();
            int cellCount = Randimize.getRndNum(50);
            addCell(cellDenomination, cellCount);
            if (cellsMap.containsKey(cellDenomination)){
                cellsMap.put(cellDenomination, cellCount + cellsMap.get(cellDenomination));
            }else{
                cellsMap.put(cellDenomination, cellCount);
            }
        }
        System.out.println("=========================");
        cellsMap.forEach((Integer i1, Integer i2)->{
            System.out.println(i1 + "  " + i2);
        });

    }

    private boolean isMultipleSum(int sum){
        for(Integer key : cellsMap.keySet()){
            if ((sum % key) == 0){
                return  true;
            }
        }
        return false;
    }

    private boolean canWithdraw(int sum) throws NotMultipleSumExceprion, NotEnoughMoneyInATMException, NotEnoughMoneCardBalanceException {
        if (sum > getBalance()){
            throw new NotEnoughMoneyInATMException();
        }else if(!isMultipleSum(sum)){
            throw new NotMultipleSumExceprion();
        }else if (getCardBalance() < sum){
            throw new NotEnoughMoneCardBalanceException();
        }else {
            return true;
        }
    }

}
