package ru.otus.HW006.Card;


import ru.otus.HW006.Bank.Banks;

import java.util.ArrayList;
import java.util.List;

public class Cards {
    private static List<CardInrf> cards = new ArrayList<>();

    static {
        cards.add(new Card(10559, Banks.getRandomBank(), "4276"));
        cards.add(new Card(1159, Banks.getRandomBank(), "3276"));
        cards.add(new Card(24559, Banks.getRandomBank(), "4586"));
        cards.add(new Card(153559, Banks.getRandomBank(), "1076"));
        cards.add(new Card(10559, Banks.getRandomBank(), "4279"));
        cards.add(new Card(109, Banks.getRandomBank(), "4597"));
        cards.add(new Card(1259, Banks.getRandomBank(), "5555"));
        cards.add(new Card(180559, Banks.getRandomBank(), "1022"));
        cards.add(new Card(107959, Banks.getRandomBank(), "0001"));
    }

    public static List<CardInrf> getCards(){
        return cards;
    }

    public static boolean numberInCards(String number){
        for(CardInrf c: cards){
            if (c.getNumber().equals(number)){
                return true;
            }
        }
        return false;
    }

    public static CardInrf getCard(String number){
        for(CardInrf c: cards){
            if (c.getNumber().equals(number)){
                return c;
            }
        }
        return null;
    }

}
