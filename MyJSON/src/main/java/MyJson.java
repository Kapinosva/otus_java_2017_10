import org.json.simple.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class MyJson {

    private JSONObject toJSON(Object o, Class classInfo) {
        JSONObject result = new JSONObject();
        superToJSON(o,classInfo,result);
        if (isMap(o)) {
            mapToJSON(o,result);
        }else{
            for (Field field : classInfo.getDeclaredFields()) {
                try {
                    fieldToJSON(o, field, result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void superToJSON(Object o, Class classInfo, JSONObject prevJSON) {
        if (classInfo.getSuperclass() != null){
            JSONObject superJSON = toJSON(o, classInfo.getSuperclass());
            for (Object key : superJSON.keySet()){
                prevJSON.put(key, superJSON.get(key));
            }
        }
    }

    private void mapToJSON(Object o, JSONObject prevJSON){
        Map map = (Map)o;
        for (Object ob: map.keySet()){
            if (isSimple(map.get(ob))){
                prevJSON.put(ob.toString(), map.get(ob));
            } else if (isArray(map.get(ob))) {
                prevJSON.put(ob.toString(), getJSONArray(map.get(ob)));
            }else {
                prevJSON.put(ob.toString(), toJSON(map.get(ob), map.get(ob).getClass()));
            }
        }
    }

    private void fieldToJSON(Object o, Field field, JSONObject prevJSON) throws IllegalAccessException {
        field.setAccessible(true);
        if (null != field.get(o)) {
            if (isSimple(field.get(o))) {
                prevJSON.put(field.getName(), field.get(o));
            } else if (isArray(field.get(o))) {
                prevJSON.put(field.getName(), getJSONArray(field.get(o)));
            } else {
                prevJSON.put(field.getName(), toJSON(field.get(o), field.getType()));
            }
        }

    }

    public String toJSONString(Object o) {
        if (null == o){
            return "null";
        }
        if (isSimple(o)){
            return o.toString();
        }else if (isArray(o)){
            return getJSONArray(o).toJSONString();
        }else{
            return toJSON(o, o.getClass()).toJSONString();
        }
    }

    private JSONArray getJSONArray(Object o){
        JSONArray ar = new JSONArray();
        Object objects = o;
        if (Collection.class.isAssignableFrom(o.getClass())){
            objects = ((Collection)o).toArray();
        }
        for (int i = 0 ; i < Array.getLength(objects); i++){
            if (null == Array.get(objects, i)){
                ar.add(null);
            }else if (isSimple(Array.get(objects, i))){
                ar.add((Array.get(objects, i)));
            }else if(isArray((Array.get(objects, i)))){
                ar.add(getJSONArray(Array.get(objects, i)));
            }else {
                ar.add(toJSON(Array.get(objects, i),Array.get(objects, i).getClass()));
            }
        }
        return ar;
    }

    private boolean isMap(Object o){
        return Map.class.isAssignableFrom(o.getClass());
    }

    private boolean isSimple(Object o){
        Class<?> cls = o.getClass();
        return cls.isPrimitive() || cls == String.class ||
                Number.class.isAssignableFrom(cls) || cls == StringBuilder.class ||
                cls == StringBuffer.class;
    }
    private boolean isArray(Object o){
        return (o.getClass().isArray()) || (Collection.class.isAssignableFrom(o.getClass()));
    }

}
