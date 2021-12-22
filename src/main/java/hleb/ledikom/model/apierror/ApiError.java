package hleb.ledikom.model.apierror;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ApiError {

    private HttpStatus status;
    private String message;
    private Map<String, String> errors;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message, Map<String, String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status.value();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
