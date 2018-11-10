import animal.Animal;

import java.io.Serializable;

public class Human implements Serializable {

    public Human(int age, String name, Animal animal) {
        this.age = age;
        this.name = name;
        this.animal = animal;
    }

    private int age;
    private String name;


    public Animal getAnimal() {
        return animal;
    }

    private Animal animal;

}
