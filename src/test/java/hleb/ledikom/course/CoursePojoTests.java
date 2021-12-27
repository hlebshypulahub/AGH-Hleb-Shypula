package hleb.ledikom.course;

import hleb.ledikom.model.course.Course;
import hleb.ledikom.model.employee.Employee;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Unit tests
public class CoursePojoTests {

    @Test
    public void When_AddCourseFromEmployeePojo_EmployeeIsSet() {
        Employee employee = new Employee();
        employee.setCourses(new HashSet<>());

        Course course = new Course();

        employee.addCourse(course);

        assertEquals(employee, course.getEmployee());
    }

}
