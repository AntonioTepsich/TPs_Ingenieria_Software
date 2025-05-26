package src;

public class EmptyDeckException extends RuntimeException {
    public EmptyDeckException() { super("Deck is empty"); }
    public EmptyDeckException(String message) { super(message); }
}