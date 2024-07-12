package gift.gift.exception;

public class ForbiddenWordException extends RuntimeException {
    public ForbiddenWordException(String message) {
        super(message);
    }
}