package hleb.ledikom.validator;

import hleb.ledikom.model.employee.Course;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, Course> {
    @Override
    public void initialize(StartDateBeforeEndDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Course course, ConstraintValidatorContext constraintValidatorContext) {
        return course.getStartDate().isBefore(course.getEndDate());
    }
}
