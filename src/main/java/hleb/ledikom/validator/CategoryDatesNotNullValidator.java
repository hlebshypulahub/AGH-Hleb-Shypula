package hleb.ledikom.validator;

import hleb.ledikom.model.employee.Employee;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryDatesNotNullValidator implements ConstraintValidator<CategoryDatesNotNull, Employee> {
    @Override
    public void initialize(CategoryDatesNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext constraintValidatorContext) {
        return employee.getCategoryAssignmentDate() == null || (employee.getCategoryAssignmentDeadlineDate() != null
                && employee.getDocsSubmitDeadlineDate() != null && employee.getCategoryPossiblePromotionDate() != null);
    }
}
