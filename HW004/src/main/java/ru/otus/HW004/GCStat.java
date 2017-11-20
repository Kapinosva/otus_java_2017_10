package ru.otus.HW004;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.List;

public class GCStat implements Runnable{
    private static String pathToFile = "./logs/GC.log";
    private static File file = new File(pathToFile);
    private static int statPeriod = 60000;
    static {
        if (file.exists()){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run(){
        while(true) {
            try {
                Thread.sleep(statPeriod);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }finally {
                logGC();
            }
        }
    }
    private void logGC(){
        try(FileWriter writer = new FileWriter(file, true))
        {
            List<GarbageCollectorMXBean> gcMXBeansList = ManagementFactory.getGarbageCollectorMXBeans();
            writer.write( "===================================================================");
            for (GarbageCollectorMXBean gcb : gcMXBeansList) {
                Date curDate = new Date();
                writer.write( curDate + "\n");
                writer.write("GC name = " + gcb.getName() + "\n");
                writer.write("GC CollectionCount = " + gcb.getCollectionCount() + "\n");
                writer.write("GC CollectionTime = " + gcb.getCollectionTime() + "\n");
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
