package tests.human;

import tests.human.animal.Animal;

import java.io.Serializable;
import java.util.*;

public class Man extends Human implements Serializable{

    private int someField = 999999;
    private Map<Integer, String[]> someMap = new HashMap<>();
    public Man(Animal animal) {
        super(animal);
        someMap.put(1,new String[]{"SomeText1",null, "asfsdgrg"});
        someMap.put(1000,new String[]{"Thousand"});
        someMap.put(10,new String[]{"Ten"});
    }

}
