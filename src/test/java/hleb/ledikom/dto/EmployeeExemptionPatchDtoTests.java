package hleb.ledikom.dto;

import hleb.ledikom.model.employee.dto.EmployeeExemptionPatchDto;
import hleb.ledikom.model.enumeration.Exemption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class EmployeeExemptionPatchDtoTests {

    @Autowired
    Validator validator;

    private EmployeeExemptionPatchDto employeeExemptionPatchDto;

    @BeforeEach
    public void before() {
        employeeExemptionPatchDto = new EmployeeExemptionPatchDto();
        employeeExemptionPatchDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionPatchDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));
        employeeExemptionPatchDto.setExemptionEndDate(LocalDate.of(2020, 6, 6));
    }

    @AfterEach
    public void after() {
        employeeExemptionPatchDto = null;
    }

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<EmployeeExemptionPatchDto>> violations = validator.validate(employeeExemptionPatchDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_ExemptionIsNull_Should_ThrowConstraintViolationException() {
        employeeExemptionPatchDto.setExemption(null);

        Set<ConstraintViolation<EmployeeExemptionPatchDto>> violations = validator.validate(employeeExemptionPatchDto);

        assertEquals("ExemptionNotNull constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_StartDateIsNull_Should_ThrowConstraintViolationException() {
        employeeExemptionPatchDto.setExemptionStartDate(null);

        Set<ConstraintViolation<EmployeeExemptionPatchDto>> violations = validator.validate(employeeExemptionPatchDto);

        assertEquals("ExemptionNotNull constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EndDateIsNull_Then_ViolationsEmpty() {
        employeeExemptionPatchDto.setExemptionEndDate(null);

        Set<ConstraintViolation<EmployeeExemptionPatchDto>> violations = validator.validate(employeeExemptionPatchDto);

        assertTrue(violations.isEmpty());
    }

}
