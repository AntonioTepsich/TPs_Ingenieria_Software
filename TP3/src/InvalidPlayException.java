package src;

public class InvalidPlayException extends RuntimeException {
    public InvalidPlayException() { super("Invalid play"); }
    public InvalidPlayException(String message) { super(message); }
}
