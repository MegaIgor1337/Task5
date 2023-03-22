package exceptions;

public class NotUniqueUserException extends Exception {
    public NotUniqueUserException(String message) {
        super(message);
    }
}
