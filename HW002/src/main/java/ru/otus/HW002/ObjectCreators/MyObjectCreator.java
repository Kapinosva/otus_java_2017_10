package ru.otus.HW002.ObjectCreators;

import ru.otus.HW002.Const;

public class MyObjectCreator implements ObjectCreator{

    private  MyClass[] objectArray= new MyClass[Const.OBJECT_COUNT];

    public void FillMemory(){
        for(int i = 0; i < Const.OBJECT_COUNT; i++){
            objectArray[i] = new MyClass();
        }
    }

    class MyClass{
        long a = 1L;
        long b = 2L;
        long c = 3L;
        long d = 4L;
    }
}
