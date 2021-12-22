package hleb.ledikom.apierror;

import hleb.ledikom.model.apierror.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Unit tests
public class ApiErrorPojoTests {

    @Test
    public void Should_ReturnStatusCode_When_CallingGetStatus() {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.OK);

        assertEquals(200, apiError.getStatus());
    }

}
