package exceptions;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String massage) {
        super(massage);
    }
}
