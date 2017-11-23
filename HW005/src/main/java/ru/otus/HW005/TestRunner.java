package ru.otus.HW005;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void runTest(Class<?> c) {
        Method beforeTest = null;
        Method afterTest = null;
        List<Method> tests = new ArrayList<>();
        for (Method method : c.getDeclaredMethods()) {
            if (method.getAnnotation(Test.class) != null) {
                tests.add(method);
                continue;
            }
            if (method.getAnnotation(BeforeTest.class) != null) {
                beforeTest = method;
                continue;
            }
            if (method.getAnnotation(AfterTest.class) != null) {
                afterTest = method;
            }
        }

        if (tests.isEmpty()) {
            return;
        }
        System.out.println("run test for class: " + c.getCanonicalName());
        try {
            for (Method test : tests) {
                Object obj = c.newInstance();
                if (beforeTest != null) {
                    beforeTest.invoke(obj);
                }
                try {
                    test.invoke(obj);
                } catch (InvocationTargetException ae) {
                    if (ae.getTargetException() instanceof AssertionError) {
                        System.out.println("test " + test.getName() + " FAILED!");
                        System.out.print(ae.getTargetException().getMessage());
                        break;
                    }
                }
                System.out.println("test " + test.getName() + " PASSED!");
                if (afterTest != null) {
                    afterTest.invoke(obj);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
