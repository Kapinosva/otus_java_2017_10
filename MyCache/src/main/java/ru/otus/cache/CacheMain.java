package ru.otus.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CacheMain {

    public static void main(String[] args) throws InterruptedException {
        new CacheMain().lifeCacheExample();
        
    }


    private void outInfo(CacheEngine<?,?> c){
    	c.printSize();
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        c.printSize();
        System.out.println("Cache hits: " + c.getHitCount());
        System.out.println("Cache misses: " + c.getMissCount());
        try {
            System.out.println("Press 'Enter'");
            br.read();
		} catch (IOException e) {
			System.out.println("Something Wrong");
		}
    }
    
    private void lifeCacheExample() throws InterruptedException {

    	int size = 90000;
        CacheEngine<Integer, String> cache =
                new CacheEngineImpl<Integer, String>(size, 10000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }
        
        outInfo(cache);

        System.gc();
        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        outInfo(cache);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        outInfo(cache);

        System.gc();
        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
           System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        outInfo(cache);
        cache.dispose();
    }
}
