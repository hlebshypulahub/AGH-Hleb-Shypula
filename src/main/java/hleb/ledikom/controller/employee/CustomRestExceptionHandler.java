package hleb.ledikom.controller.employee;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
//        List<String> errors = new ArrayList<>();
//        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
//        }
//
//        logger.error(errors);
//
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    @Nonnull
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatus status, @Nonnull WebRequest request) {
//        List<String> errors = new ArrayList<>();
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}