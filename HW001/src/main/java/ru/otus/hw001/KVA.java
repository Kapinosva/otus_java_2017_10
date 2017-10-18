package ru.otus.hw001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.Lists;

public class KVA {
    public static void main(String[] args) {
        CharSequence chs ="";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            chs =  br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List list = Lists.charactersOf(chs);
        System.out.println(list.size());
    }
}
