package ru.otus.HW006.ATM.cell;

import java.util.HashMap;
import java.util.Map;

public class CellCareTaker {
    private Map<Cell, CellMemento> cellMementos = new HashMap<>();

    public void put(Cell cell, CellMemento cellMemento){
        cellMementos.replace(cell, cellMemento);
    }

    public CellMemento get(Cell cell){
            return cellMementos.get(cell);
    }
}
