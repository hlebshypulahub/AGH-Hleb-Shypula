package hleb.ledikom.dto;

import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;
import hleb.ledikom.model.enumeration.Category;
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
public class EmployeeCategoryPatchDtoTests {

    @Autowired
    Validator validator;

    private EmployeeCategoryPatchDto employeeCategoryPatchDto;

    @BeforeEach
    public void before() {
        employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");
    }

    @AfterEach
    public void after() {
        employeeCategoryPatchDto = null;
    }

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_QualificationIsBlank_Should_ThrowConstraintViolationException() {
        employeeCategoryPatchDto.setQualification(" ");

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals("qualification cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryIsNull_Should_ThrowConstraintViolationException() {
        employeeCategoryPatchDto.setCategory(null);

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals("category cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryAssignmentDateIsNull_And_CategoryIsNotNone_Should_ThrowConstraintViolationException() {
        employeeCategoryPatchDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals("CategoryAssignmentDateNotNull constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryNumberIsBlank_And_CategoryIsNotNone_Should_ThrowConstraintViolationException() {
        employeeCategoryPatchDto.setCategoryNumber("");

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals("CategoryNumberNotBlank constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryNumberIsBlank_And_CategoryAssignmentDateIsNull_And_CategoryIsNotNone_Should_ThrowConstraintViolationException() {
        employeeCategoryPatchDto.setCategoryNumber("");
        employeeCategoryPatchDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals(2, violations.size());
    }

    @Test
    public void When_CategoryNumberIsBlank_And_CategoryAssignmentDateIsNull_And_CategoryIsNone_Then_ViolationsEmpty() {
        employeeCategoryPatchDto.setCategory(Category.NONE);
        employeeCategoryPatchDto.setCategoryNumber("");
        employeeCategoryPatchDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_EverythingIsNull_Should_ThrowConstraintViolationException() {
        employeeCategoryPatchDto.setCategory(null);
        employeeCategoryPatchDto.setQualification(null);
        employeeCategoryPatchDto.setCategoryNumber(null);
        employeeCategoryPatchDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals(4, violations.size());
    }
}
