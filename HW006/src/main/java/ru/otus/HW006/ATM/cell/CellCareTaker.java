package ru.otus.HW006.ATM.cell;

import java.util.HashMap;
import java.util.Map;

public class CellCareTaker {
    private Map<CellIntf, CellMemento> cellMementos = new HashMap<>();

    public void put(CellIntf cell, CellMemento cellMemento){
        cellMementos.replace(cell, cellMemento);
    }

    public CellMemento get(CellIntf cell){
            return cellMementos.get(cell);
    }
}
