package pl.power.exception;

public class StartDateIsAfterDateEndException extends RuntimeException {
    public StartDateIsAfterDateEndException(String message) {
        super(message);
    }
}
