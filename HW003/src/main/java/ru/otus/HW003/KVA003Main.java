package ru.otus.HW003;

import javax.print.DocFlavor;
import java.util.*;

public class KVA003Main {

    public static void main(String[] args) {
        List<String> list = new MyArrayList<>();
        list.add("one");
        list.add("three");
        list.add("five");
        list.add("six");
        list.add("seven");
        list.add("mama");
        list.add("myla");
        list.add("ramu");

        String[] newList = {"6", "3","0", "1"};
        Collections.addAll(list, newList);
        System.out.println("list AFTER addAll =>> " + list.toString() + "\n");

        System.out.println("list before sort =>> " + list.toString() + "\n");
        Collections.sort(list);
        System.out.println("list AFTER sort =>> " + list.toString() + "\n");

        List<String> copiedList = Arrays.asList("monday", "tuesday", "wednesday");
        Collections.copy(list, copiedList);
        System.out.println("list AFTER copying =>> " + list.toString() + "\n");
    }

}
