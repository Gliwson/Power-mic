package pl.power.exception;

public class TaskDTONotFoundException extends RuntimeException {

    public TaskDTONotFoundException(Long id) {
        super("Could not find taskDTO " + id);
    }

    public TaskDTONotFoundException() {
        super("Could not find taskDTO");
    }
}
