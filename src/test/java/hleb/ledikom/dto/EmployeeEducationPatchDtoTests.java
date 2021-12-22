package hleb.ledikom.dto;

import hleb.ledikom.model.employee.dto.EmployeeEducationPatchDto;
import hleb.ledikom.model.enumeration.Education;
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
public class EmployeeEducationPatchDtoTests {

    @Autowired
    Validator validator;

    private EmployeeEducationPatchDto employeeEducationPatchDto;

    @BeforeEach
    public void before() {
        employeeEducationPatchDto = new EmployeeEducationPatchDto();
        employeeEducationPatchDto.setEducation(Education.HIGHER);
        employeeEducationPatchDto.setEduName("AGH");
        employeeEducationPatchDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));
    }

    @AfterEach
    public void after() {
        employeeEducationPatchDto = null;
    }

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_EducationIsNull_Should_ThrowConstraintViolationException() {
        employeeEducationPatchDto.setEducation(null);

        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);

        assertEquals("Education cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EduNameIsBlank_Should_ThrowConstraintViolationException() {
        employeeEducationPatchDto.setEduName("  ");

        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);

        assertEquals("Education name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EduGraduationDateIsNull_Should_ThrowConstraintViolationException() {
        employeeEducationPatchDto.setEduGraduationDate(null);

        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);

        assertEquals("Education graduation date cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EverythingIsNullOrBlank_Should_ThrowConstraintViolationException() {
        employeeEducationPatchDto.setEducation(null);
        employeeEducationPatchDto.setEduGraduationDate(null);
        employeeEducationPatchDto.setEduName("  ");

        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);

        assertEquals(3, violations.size());
    }
}
