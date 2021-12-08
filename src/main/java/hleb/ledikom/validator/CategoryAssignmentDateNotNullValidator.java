package hleb.ledikom.validator;

import hleb.ledikom.model.employee.Category;
import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryAssignmentDateNotNullValidator implements ConstraintValidator<CategoryAssignmentDateNotNull, EmployeeCategoryPatchDto> {

    @Override
    public void initialize(CategoryAssignmentDateNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeCategoryPatchDto employeeCategoryPatchDto, ConstraintValidatorContext constraintValidatorContext) {
        return employeeCategoryPatchDto.getCategory() == Category.NONE || employeeCategoryPatchDto.getCategoryAssignmentDate() != null;
    }
}
