package hexlet.code.exception;

public class ResourceIsInUseException extends RuntimeException {
    public ResourceIsInUseException(String message) {
        super(message);
    }
}
