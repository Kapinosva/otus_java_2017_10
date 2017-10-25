package ru.otus.HW002.Measurment;

import ru.otus.HW002.ObjectCreators.ArrayCreator;
import ru.otus.HW002.ObjectCreators.MyObjectCreator;
import ru.otus.HW002.ObjectCreators.StringPoolCreator;
import ru.otus.HW002.ObjectCreators.StringWithoutPoolCreator;
import ru.otus.HW002.UserInput;

public class MemoryMeter {

    private static final Runtime runtime = Runtime.getRuntime();

    public static long measureMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private MemoryMeasurment memoryMeasurment;

    public void mesure(){
        String userInput = "";

        while (!(userInput = UserInput.getUserInput()).equals("q")){
            memoryMeasurment = new MemoryMeasurment();
            int memoryPerObject = 0;

            if (userInput.equals("1")) {
                memoryPerObject = memoryMeasurment.getMemoryMeasurment(new StringPoolCreator());
            }else if (userInput.equals("2")) {
                memoryPerObject = memoryMeasurment.getMemoryMeasurment(new StringWithoutPoolCreator());
            }else if (userInput.equals("3")) {
                memoryPerObject = memoryMeasurment.getMemoryMeasurment(new MyObjectCreator());
            }else if (userInput.equals("4")) {
                memoryPerObject = memoryMeasurment.getMemoryMeasurment(new ArrayCreator());
            }else{
                System.out.println("Wrong input!");
                continue;
            }
            System.out.println("Memory per object is " + memoryPerObject);

            memoryMeasurment = null;
            System.gc();
        }
    }
}
