package hleb.ledikom.course;

import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.EmployeeCategory;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseServiceTests {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CourseService courseService;

    private Employee employee;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.FIRST);
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));

        employee.setCourses(new HashSet<>());

        employee = employeeService.process(employee);
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void testCourseAddOutOfTerm() {
        Course course = new Course();
        course.setHours(50);
        course.setStartDate(LocalDate.of(2019, 5, 5));
        course.setEndDate(LocalDate.of(2019, 5, 10));

        employee = courseService.addCourse(employee, course);

        assertEquals(0, employee.getCourseHoursSum());
    }

    @Test
    public void testCourseAddInTerm() {
        Course course = new Course();
        course.setHours(50);
        course.setStartDate(LocalDate.of(2022, 5, 5));
        course.setEndDate(LocalDate.of(2022, 5, 10));

        employee = courseService.addCourse(employee, course);

        assertEquals(50, employee.getCourseHoursSum());
    }

    @Test
    public void testCourseAddInTermTwice() {
        Course course1 = new Course();
        course1.setHours(50);
        course1.setStartDate(LocalDate.of(2022, 5, 5));
        course1.setEndDate(LocalDate.of(2022, 5, 10));
        employee = courseService.addCourse(employee, course1);

        Course course2 = new Course();
        course2.setHours(60);
        course2.setStartDate(LocalDate.of(2023, 5, 5));
        course2.setEndDate(LocalDate.of(2023, 5, 10));
        employee = courseService.addCourse(employee, course2);

        assertEquals(110, employee.getCourseHoursSum());
    }
}
