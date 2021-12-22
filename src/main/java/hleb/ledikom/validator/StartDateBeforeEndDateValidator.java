package hleb.ledikom.validator;

import hleb.ledikom.model.Course;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, Course> {
    @Override
    public void initialize(StartDateBeforeEndDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(@Valid Course course, ConstraintValidatorContext constraintValidatorContext) {
        return course.getStartDate() != null && course.getEndDate() != null && course.getStartDate().isBefore(course.getEndDate());
    }
}
