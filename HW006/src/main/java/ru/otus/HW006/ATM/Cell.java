package ru.otus.HW006.ATM;

public class Cell implements CellIntf {
    private int denomination = 0;
    private int count = 0;

    public Cell(int denomination, int count){
        this.denomination = denomination;
        this.count = count;
    }

    @Override
    public int withdraw(int sum){
        if (sum <= getBalance()){
            int canWithdrawBankNotes  = (int) (sum / getDenomination());
            count = count - canWithdrawBankNotes;
            return sum - canWithdrawBankNotes * getDenomination();
        }else{
            count = 0;
            return sum - getBalance();
        }
    }
    @Override
    public int getBalance() {
        return denomination * count;
    }

    @Override
    public int getDenomination() {
        return denomination;
    }

    @Override
    public int getCount() {
        return count;
    }
}
