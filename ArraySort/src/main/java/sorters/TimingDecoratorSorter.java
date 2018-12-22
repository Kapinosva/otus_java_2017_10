package sorters;

public class TimingDecoratorSorter<T extends Comparable<T>> implements Sorter<T> {
    private long timeBeforeSort, timeAfterSort;
    private Sorter<T> mainSorter;

    public TimingDecoratorSorter(Sorter<T> mainSorter){
        this.mainSorter = mainSorter;
    }

    @Override
    public void sort(T[] array) {
        timeBeforeSort = System.nanoTime();
        mainSorter.sort(array);
        timeAfterSort = System.nanoTime();
    }

    public double getSortTime(){
        return (double) (timeAfterSort - timeBeforeSort)/1_000_000_000;
    }
}
