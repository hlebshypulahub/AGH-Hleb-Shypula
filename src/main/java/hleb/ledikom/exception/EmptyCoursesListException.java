package hleb.ledikom.exception;

public class EmptyCoursesListException extends RuntimeException {
    public EmptyCoursesListException(String message) {
        super(message);
    }
}