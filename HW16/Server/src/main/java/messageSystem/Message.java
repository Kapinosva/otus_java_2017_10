package messageSystem;


public abstract class Message {
    public static final String CLASS_NAME_VARIABLE = "className";
    private final String className = getClass().getCanonicalName();

    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);

}
