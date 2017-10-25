package ru.otus.HW002.ObjectCreators;

import ru.otus.HW002.Const;

public class ArrayCreator implements ObjectCreator{

    private  int[][] intArray= new int[Const.OBJECT_COUNT][];

    public void FillMemory(){
        for(int i = 0; i < Const.OBJECT_COUNT; i++){
            intArray[i] = new int[0];
        }
    }
}
