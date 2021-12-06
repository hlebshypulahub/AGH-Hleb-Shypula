package hleb.ledikom.dto;

import hleb.ledikom.model.employee.Category;
import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DtoTests {

    @Autowired
    private Validator validator;

    @Test
    public void shouldMarkPatchAsInvalid() {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals(4, violations.size());
    }

    @Test
    public void shouldMarkCategoryAssignmentDateAsInvalid() {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertEquals(3, violations.size());
    }

    @Test
    public void shouldMarkCategoryAssignmentDateAsValid() {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.NONE);
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);

        assertTrue(violations.isEmpty());
    }

}
