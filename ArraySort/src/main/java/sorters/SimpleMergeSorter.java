package sorters;

import java.util.Arrays;

public class SimpleMergeSorter<T extends Comparable<T>> implements Sorter<T>{

    @Override
    public void sort(T[] array) {
        if (array.length == 2){
            sortTwoElements(array);
        }else if (array.length > 2){
            T[] arrayLeftPart = (Arrays.copyOfRange(array,0,array.length / 2));
            T[] arrayRightPart = (Arrays.copyOfRange(array,array.length / 2 ,array.length));
            sort(arrayLeftPart);
            sort(arrayRightPart);
            mergeArrays(array, arrayLeftPart, arrayRightPart);
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
