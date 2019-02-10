package messageSystem;

public abstract class Message {
    private final Addressee from;
    private final Addressee to;

    public Message(Addressee from, Addressee to) {
        this.from = from;
        this.to = to;
    }

    public Addressee getFrom() {
        return from;
    }

    public Addressee getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
