import animal.Animal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Man extends Human implements Serializable{

    private int someField=5;

    /*если не transient беде быть, так как такое же в суперклассе*/
    private transient int age=99;

    int[] a = new int[]{1,2,3,4,5,4,3,2,1};
    List<Integer> b = Arrays.asList(new Integer[]{1,2,3,4,5,0,0,0,0});

    public Man(int age, String name, Animal animal) {
        super(age,name,animal);
    }

}
