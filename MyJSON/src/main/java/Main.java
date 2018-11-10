import animal.Cat;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Man someBody = new Man(4,"VAAAdwgreg", new Cat().setAge(5).setName("MY CAT"));
        MyJson myJson = new MyJson();
        System.out.println(myJson.toJSON(someBody).toJSONString());
        Gson gson = new Gson();
        System.out.println(gson.toJson(someBody));
        System.out.println("Equals JSONs?");
    }
}
