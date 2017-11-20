package ru.otus.HW004;

public class KVA004Main {
    static GCStat gcStat = new GCStat();
    static ObjectCreator oc = new ObjectCreator();

    public static void main(String[] args) {
        new Thread(gcStat).start();
        new Thread(oc).start();
    }
}
