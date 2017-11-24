package ru.otus.HW005;


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

}
