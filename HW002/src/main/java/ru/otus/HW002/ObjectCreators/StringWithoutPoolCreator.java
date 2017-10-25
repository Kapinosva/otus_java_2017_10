package ru.otus.HW002.ObjectCreators;

import ru.otus.HW002.Const;

public class StringWithoutPoolCreator  implements ObjectCreator{

    private  String[] stringArray= new String[Const.OBJECT_COUNT];

    public void FillMemory(){
        for(int i = 0; i < Const.OBJECT_COUNT; i++){
            stringArray[i] = new String(new char[0]);
        }
    }

}



