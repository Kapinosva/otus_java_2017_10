import sorters.MultiThreadSorter;
import sorters.SimpleMergeSorter;
import sorters.Sorter;
import sorters.TimingDecoratorSorter;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] a = new Integer[1000];
        Integer[] b = new Integer[1000];
        for (int i = 0; i < a.length; i++){
            a[i] = random.nextInt(10000);
            b[i] = a[i];
        }

        Sorter<Integer> mainSorter = new SimpleMergeSorter<>();
        TimingDecoratorSorter<Integer> sorter = new TimingDecoratorSorter<>(mainSorter);
        sorter.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(sorter.getSortTime());

        mainSorter = new MultiThreadSorter<>();
        sorter = new TimingDecoratorSorter<>(mainSorter);
        sorter.sort(b);
        System.out.println(Arrays.toString(b));
        System.out.println(sorter.getSortTime());
    }
}
