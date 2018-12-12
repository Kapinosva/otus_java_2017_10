package context;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Map<Class, Object> context = new HashMap<>();

    public void add(Class clas, Object o){
        if (context.containsKey(clas)){
            System.out.println("We already have service '" + clas.getName() + "'. We did not add it.");
        }else{
            context.put(clas, o);
        }
    }

    public <T> T get(Class clas){
        if (context.containsKey(clas)){
            return (T)(context.get(clas));
        }else{
            System.out.println("There is no service named '" + clas.getName() + "'. You need to add it first.");
            return null;
        }
    }

}
