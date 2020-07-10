package pl.power.exception;

public class NotFoundIDException extends RuntimeException {
    public NotFoundIDException(Long id) {
        super("Not Found " + id);
    }
}
