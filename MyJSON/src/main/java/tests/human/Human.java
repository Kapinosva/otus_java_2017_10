package tests.human;

import tests.human.animal.Animal;

import java.io.Serializable;

public class Human implements Serializable {
    private  int age = 55;
    private  String name = "Vasya";

    public Human(Animal animal) {
        this.animal = animal;
    }
    private Animal animal;

}
