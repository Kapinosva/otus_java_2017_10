import tests.human.animal.Cat;

import com.google.gson.Gson;
import tests.human.Human;
import tests.human.Man;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int test1 = 55;
        StringBuilder test2 = new StringBuilder("Some Test");
        Object test3 = null;
        Object[] test4 = new Object[]{};
        double test5 = 5.333;
        Human test6 = new Human(new Cat().setAge(12).setName("CAAAAAt"));
        Human test7 = new Human(null);
        Object[] test8 = new Object[]{new Object()};
        List test9 = new ArrayList(Arrays.asList(test8));
        List test10 = new ArrayList();
        List test11 = new ArrayList(Arrays.asList(1,2,3,4));
        List test12 = null;
        Man test13 = new Man(new Cat());
        Integer test14 = new Integer(5599633);
        Map<Integer,String> test15 = new HashMap<>();
        test15.put(1,"safdsf");
        Object[] test16 = new Object[]{null, null, null};

        Map<Integer,String[]> test17 = new HashMap<>();
        test17.put(1,new String[]{"safdsf",null});

        Object[] tests = new Object[]{
                test1
                , test2
                , test3
                , test4
                , test5
                , test6
                , test7
                , test8
                , test9
                , test10
                , test11
                , test12
                , test13
                , test14
                , test15
                , test16
                , test17
        };

        MyJson myJson = new MyJson();
        Gson g = new Gson();
        for (Object test: tests){
            System.out.println("MY JSON");
            System.out.println(myJson.toJSONString(test));
            System.out.println("GSON JSON");
            System.out.println(g.toJson(test));
            System.out.println("=====================================================");
        }

    }
}
