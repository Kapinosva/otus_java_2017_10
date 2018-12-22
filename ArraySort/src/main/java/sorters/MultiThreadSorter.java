package sorters;

import java.util.Arrays;

public class MultiThreadSorter<T extends Comparable<T>> implements Sorter<T> {

    private final class MultiThreadSort extends Thread{
        private T[] unsorted, sorted;
        private static final int MAX_THREADS = 12;
        MultiThreadSort(T[] unsorted){
            this.unsorted = unsorted;
            this.sorted = unsorted;
        }

        public T[] getSorted(){
            return sorted;
        }

        @Override
        public void run(){
            if (unsorted.length == 2){
                sortTwoElements(unsorted);
            }else if (unsorted.length > 2){
                T[] arrayLeftPart = (Arrays.copyOfRange(unsorted,0,unsorted.length / 2));
                T[] arrayRightPart = (Arrays.copyOfRange(unsorted,unsorted.length / 2 ,unsorted.length));
                if ( activeCount() < MAX_THREADS ) {
                    MultiThreadSort leftSort = new MultiThreadSort( arrayLeftPart );
                    MultiThreadSort rightSort = new MultiThreadSort( arrayRightPart );
                    leftSort.start();
                    rightSort.start();
                    try {
                        leftSort.join();
                        rightSort.join();
                        mergeArrays(sorted, arrayLeftPart, arrayRightPart);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    sort(arrayLeftPart);
                    sort(arrayRightPart);
                    mergeArrays(sorted, arrayLeftPart, arrayRightPart);
                }
            }
        }
        private void mergeArrays(T[] resultArray, T[] leftPart, T[]  rightPart){
            int resultLength = resultArray.length;
            int j = 0;
            int k = 0;
            for (int i = 0; i < resultLength; i++){
                if ((j <= leftPart.length -1) && ((k > rightPart.length -1) || (leftPart[j].compareTo(rightPart[k]) <= 0))){
                    resultArray[i] = leftPart[j];
                    j++;
                }else {
                    resultArray[i] = (rightPart[k]);
                    k++;
                }
            }
        }

        private void sortTwoElements(T[] twoElements){
            if (twoElements[0].compareTo(twoElements[1]) > 0){
                T temp = twoElements[0];
                twoElements[0] = twoElements[1];
                twoElements[1] = temp;
            }
        }
    }

    @Override
    public void sort(T[] array) {
        MultiThreadSort sorter = new MultiThreadSort(array);
        sorter.run();
        array = sorter.getSorted();
    }

}
