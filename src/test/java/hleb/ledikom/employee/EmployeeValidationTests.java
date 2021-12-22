package hleb.ledikom.employee;

import hleb.ledikom.model.employee.Employee;
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
public class EmployeeValidationTests {

    @Autowired
    Validator validator;

    private Employee employee;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Adam Babacki");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void When_EmployeeIsValid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_CategoryDatesNotNull_Then_ViolationsEmpty() {
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 4, 4));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 4, 4));

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_ForeignIdIsNull_Should_ThrowConstraintViolationException() {
        employee.setForeignId(null);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals("foreignId cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void When_FullNameIsBlank_Should_ThrowConstraintViolationException() {
        employee.setFullName(" ");

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals("fullName cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_HiringDateIsNull_Should_ThrowConstraintViolationException() {
        employee.setHiringDate(null);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals("hiringDate cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void When_JobFacilityIsBlank_Should_ThrowConstraintViolationException() {
        employee.setJobFacility(null);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals("jobFacility cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_PositionIsBlank_Should_ThrowConstraintViolationException() {
        employee.setPosition(null);

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals("position cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryCalculatedDatesAreNull_And_FullNameIsBlank_Should_ThrowTwoViolations() {
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 10, 10));
        employee.setFullName("");

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals(2, violations.size());
    }
}
