package hleb.ledikom.dto;

import hleb.ledikom.model.employee.dto.EmployeeActivePatchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// Unit tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeActivePatchDtoTests {

    @Autowired
    Validator validator;

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        EmployeeActivePatchDto employeeActivePatchDto = new EmployeeActivePatchDto();
        employeeActivePatchDto.setActive(true);

        Set<ConstraintViolation<EmployeeActivePatchDto>> violations = validator.validate(employeeActivePatchDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_DateIsNull_Should_ThrowConstraintViolationException() {
        EmployeeActivePatchDto employeeActivePatchDto = new EmployeeActivePatchDto();

        Set<ConstraintViolation<EmployeeActivePatchDto>> violations = validator.validate(employeeActivePatchDto);

        assertEquals(1, violations.size());
    }

    @Test
    public void When_DateIsNull_Then_ConstraintViolationMessage() {
        EmployeeActivePatchDto employeeActivePatchDto = new EmployeeActivePatchDto();

        Set<ConstraintViolation<EmployeeActivePatchDto>> violations = validator.validate(employeeActivePatchDto);

        assertEquals("active cannot be null", violations.iterator().next().getMessage());
    }
}
