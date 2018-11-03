package ru.otus.HW006.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {
    private static String lastUserInput;

    public static String getLastUserInput(){
        return lastUserInput;
    }

    public static int getLastUserintInput(){
        return Integer.parseInt(lastUserInput);
    }

    private static void wantExit(){
        if (lastUserInput.equals("Exit")){
            System.out.println("GOODBYE!!!!");
            Runtime.getRuntime().exit(0);
        }
    }


    public static String getUserInput(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            lastUserInput = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            lastUserInput = "";
        }
        wantExit();
        return getLastUserInput();
    }

}
