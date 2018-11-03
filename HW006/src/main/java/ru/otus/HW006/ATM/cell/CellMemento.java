package ru.otus.HW006.ATM.cell;

public class CellMemento {
    private int denomination;
    private int count;

    public CellMemento(int denomination, int count){
        this.denomination = denomination;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getDenomination() {
        return denomination;
    }
}
