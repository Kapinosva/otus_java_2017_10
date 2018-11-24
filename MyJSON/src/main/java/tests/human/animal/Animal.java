package tests.human.animal;

import java.io.Serializable;

public abstract  class Animal implements Serializable {
    private String name;
    private int age;

    public int getAge() {
        return age;
    }

    public Animal setAge(int age) {
        this.age = age;
        return  this;
    }

    public String getName(){
        return name;
    }

    public Animal setName(String name){
        this.name = name;
        return  this;
    }
}
