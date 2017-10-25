package ru.otus.HW002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {
    public static String getUserInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
            try {
                System.out.println(Const.COMMAND_PROMPT);
                for (int i = 0; i < Const.STRUCTURE_TYPE.size(); i++) {
                    System.out.println("\t" + (i + 1) + " - " +Const.STRUCTURE_TYPE.get(i));
                }
                System.out.print(">>");
                userInput = br.readLine().trim();

            } catch (IOException e) {
                userInput = "";
            }
            return userInput;
    }
}
