package messageSystem;


public abstract class Message {
    public static final String CLASS_NAME_VARIABLE = "className";
    private final String className = getClass().getCanonicalName();
    public abstract void exec(Addressee addressee);

}
