package hleb.ledikom.course;

import hleb.ledikom.exception.EmptyCoursesListException;
import hleb.ledikom.model.course.Course;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/// Unit tests
public class EmptyCoursesListExceptionTests {

    @Test
    public void Should_ThrowException_When_ListIsEmpty() {
        List<Course> courses = new ArrayList<>();

        EmptyCoursesListException exception = assertThrows(EmptyCoursesListException.class, () -> {
            courses.stream().max(Comparator.comparing(Course::getStartDate)).orElseThrow(() -> new EmptyCoursesListException("Test"));
        });

        assertEquals("Test", exception.getMessage());
    }

}
