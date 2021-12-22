package hleb.ledikom.dto;

import hleb.ledikom.model.employee.dto.EmployeeCategoryDeadlinePatchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// Unit tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeCategoryDeadlinePatchDtoTests {

    @Autowired
    Validator validator;

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto = new EmployeeCategoryDeadlinePatchDto();
        employeeCategoryDeadlinePatchDto.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));

        Set<ConstraintViolation<EmployeeCategoryDeadlinePatchDto>> violations = validator.validate(employeeCategoryDeadlinePatchDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_DateIsNull_Should_ThrowConstraintViolationException() {
        EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto = new EmployeeCategoryDeadlinePatchDto();

        Set<ConstraintViolation<EmployeeCategoryDeadlinePatchDto>> violations = validator.validate(employeeCategoryDeadlinePatchDto);

        assertEquals(1, violations.size());
    }

    @Test
    public void When_DateIsNull_Then_ConstraintViolationMessage() {
        EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto = new EmployeeCategoryDeadlinePatchDto();

        Set<ConstraintViolation<EmployeeCategoryDeadlinePatchDto>> violations = validator.validate(employeeCategoryDeadlinePatchDto);

        assertEquals("categoryAssignmentDeadlineDate cannot be null", violations.iterator().next().getMessage());
    }

}
