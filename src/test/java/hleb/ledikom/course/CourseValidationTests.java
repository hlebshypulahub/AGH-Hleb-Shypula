package hleb.ledikom.course;

import hleb.ledikom.model.course.Course;
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
public class CourseValidationTests {

    @Autowired
    private Validator validator;

    private Course course;

    @BeforeEach
    public void before() {
        course = new Course();
        course.setName("Course 1");
        course.setHours(10);
        course.setStartDate(LocalDate.of(2020, 10, 10));
        course.setEndDate(LocalDate.of(2020, 11, 11));
    }

    @AfterEach
    public void after() {
        course = null;
    }

    @Test
    public void When_CourseIsValid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<Course>> violations = validator.validate(course);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_EndDateBeforeStartDate_Should_ThrowConstraintViolationException() {
        course.setStartDate(LocalDate.of(2020, 10, 10));
        course.setEndDate(LocalDate.of(2020, 9, 9));

        Set<ConstraintViolation<Course>> violations = validator.validate(course);

        assertEquals("StartDateBeforeEndDate constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_NameIsBlank_Should_ThrowConstraintViolationException() {
        course.setName(" ");

        Set<ConstraintViolation<Course>> violations = validator.validate(course);

        assertEquals("Course name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_HoursLessThan1_Should_ThrowConstraintViolationException() {
        course.setHours(0);

        Set<ConstraintViolation<Course>> violations = validator.validate(course);

        assertEquals("Course hours must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    public void When_DateIsNull_Should_ThrowConstraintViolationException() {
        course.setStartDate(null);

        Set<ConstraintViolation<Course>> violations = validator.validate(course);

        assertEquals("StartDateBeforeEndDate constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EndDateBeforeStartDate_And_HoursLessThan1_Should_ThrowTwoViolations() {
        course.setHours(0);
        course.setStartDate(LocalDate.of(2020, 10, 10));
        course.setEndDate(LocalDate.of(2020, 9, 9));

        Set<ConstraintViolation<Course>> violations = validator.validate(course);

        assertEquals(2, violations.size());
    }
}
