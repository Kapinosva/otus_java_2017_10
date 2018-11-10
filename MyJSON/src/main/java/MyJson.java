import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class MyJson {

    private JSONObject toJSON(Object o, Class classInfo) {
        JSONObject result = new JSONObject();
        for (Field field: classInfo.getDeclaredFields()){
            try {
                field.setAccessible(true);;
                if ((field.getType().isPrimitive()) || (field.getType().getCanonicalName().equals("java.lang.String"))) {
                    result.put(field.getName(), field.get(o));
                }else if (field.getType().isArray()){
                    JSONArray ar = new JSONArray();
                    for (int i = 0 ; i < Array.getLength(field.get(o)); i++){
                        ar.add(Array.get(field.get(o), i));
                    }
                    result.put(field.getName(), ar);
                }else if (Collection.class.isAssignableFrom(field.getType())) {
                    JSONArray ar = new JSONArray();
                    for (Object object: (Collection)field.get(o)){
                        ar.add(object);
                    }
                    result.put(field.getName(), ar);
                }else{
                    result.put(field.getName(), toJSON(field.get(o), field.getType()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (classInfo.getSuperclass() != null){
            JSONObject superJSON = toJSON(o, classInfo.getSuperclass());
            for (Object key : superJSON.keySet()){
                result.put(key, superJSON.get(key));
            }
        }
        return result;
    }

    public JSONObject toJSON(Object o) {
        return toJSON(o, o.getClass());
    }

}
