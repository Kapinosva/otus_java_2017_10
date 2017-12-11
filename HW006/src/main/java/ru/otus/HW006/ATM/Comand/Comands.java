package ru.otus.HW006.ATM.Comand;

import java.util.ArrayList;
import java.util.List;

public class Comands {
    private List<Comand> comands = new ArrayList<>();

    public void addComand(Comand comand){
        comands.add(comand);
    }

    public Comand get(int index){
        return comands.get(index);
    }

    public void printComands(){
        for (int i = 0; i < comands.size();i++){
            System.out.print("Comand id = " + i + " Comand name = ");
            comands.get(i).printComandPrompt();
        }
    }


    public boolean idInComands(int index){
        for (int i = 0; i < comands.size();i++){
            if (i == index){
                return true;
            }
        }
        return false;
    }

}
