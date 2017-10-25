package ru.otus.HW002;

import java.util.ArrayList;

public class Const {
    public static ArrayList<String> STRUCTURE_TYPE = new ArrayList<>();
    static{
        STRUCTURE_TYPE.add("String with String Pool");
        STRUCTURE_TYPE.add("String without String Pool");
        STRUCTURE_TYPE.add("MyObject");
        STRUCTURE_TYPE.add("Array");
    }
    public static int OBJECT_COUNT = 3000000;
    public static String COMMAND_PROMPT = "Please select the structure type for memory measurment and input it's number(q for exit).>>";

}
