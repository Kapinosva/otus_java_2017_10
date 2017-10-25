package ru.otus.HW002.Measurment;


import ru.otus.HW002.Const;
import ru.otus.HW002.ObjectCreators.ObjectCreator;

public class MemoryMeasurment {
    private ObjectCreator objectCreator;

    public int getMemoryMeasurment(ObjectCreator objectCreator){
        this.objectCreator = objectCreator;

        long memoryBefore = MemoryMeter.measureMemory();
        this.objectCreator.FillMemory();
        long memoryAfter = MemoryMeter.measureMemory();

        return (int)((memoryAfter - memoryBefore) / Const.OBJECT_COUNT);
    }
}
