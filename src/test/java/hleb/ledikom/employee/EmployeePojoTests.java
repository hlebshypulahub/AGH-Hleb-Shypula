package hleb.ledikom.employee;


import hleb.ledikom.model.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.model.enumeration.Education;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Unit tests
public class EmployeePojoTests {

    @Test
    public void When_CategoryAndEducationAreSet_Should_RetrunValidCourseHoursLeft() {
        Employee employee = new Employee();
        employee.setCategory(Category.NONE);
        employee.setEducation(Education.SECONDARY);

        assertEquals(50, employee.getCourseHoursLeft());
    }

    @Test
    public void When_CategoryAndEducationAreSet_Should_RetrunValidCourseHoursLeft_2() {
        Employee employee = new Employee();
        employee.setCategory(Category.NONE);
        employee.setEducation(Education.HIGHER);

        assertEquals(100, employee.getCourseHoursLeft());
    }

    @Test
    public void When_CategoryAndEducationAreSet_Should_RetrunValidCourseHoursLeft_3() {
        Employee employee = new Employee();
        employee.setCategory(Category.FIRST);
        employee.setEducation(Education.HIGHER);

        assertEquals(160, employee.getCourseHoursLeft());
    }

    @Test
    public void When_CategoryAndEducationAreSet_Should_RetrunValidCourseHoursLeft_4() {
        Employee employee = new Employee();
        employee.setCategory(Category.FIRST);
        employee.setEducation(Education.SECONDARY);

        assertEquals(80, employee.getCourseHoursLeft());
    }

    @Test
    public void When_AddCourse_Expect_CoursesSizeIncrease() {
        Employee employee = new Employee();
        employee.setCourses(new HashSet<>());

        Course course = new Course();

        assertEquals(0, employee.getCourses().size());

        employee.addCourse(course);

        assertEquals(1, employee.getCourses().size());
    }

}
