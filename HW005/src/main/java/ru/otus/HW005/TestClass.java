package ru.otus.HW005;


import java.util.Collection;
import java.util.function.Predicate;

public class TestClass {
    @Test
    @BeforeTest
    @AfterTest
    public int inc(int a){
        return a ++;
    }

    @Test
    @BeforeTest
    @AfterTest
    public void text(){
        System.out.println("Testing TestClass.text");
    }
    public Collection<T> filter(Collection<T> list, Predicate<T> p){
        for(T e : list){
            Predicate(e);
        }
    }

}
