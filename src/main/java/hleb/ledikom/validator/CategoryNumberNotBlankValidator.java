package hleb.ledikom.validator;

import hleb.ledikom.model.employee.Category;
import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryNumberNotBlankValidator implements ConstraintValidator<CategoryNumberNotBlank, EmployeeCategoryPatchDto> {
    @Override
    public void initialize(CategoryNumberNotBlank constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeCategoryPatchDto employeeCategoryPatchDto, ConstraintValidatorContext constraintValidatorContext) {
        return employeeCategoryPatchDto.getCategory() == Category.NONE || StringUtils.isNotBlank(employeeCategoryPatchDto.getCategoryNumber());
    }
}
