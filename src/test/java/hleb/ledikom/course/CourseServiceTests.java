package hleb.ledikom.course;

import hleb.ledikom.model.course.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.CourseRepository;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeValidationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

/// Unit tests
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CourseServiceTests {

    @Mock
    private EmployeeValidationService employeeValidationService;
    @Mock
    private CourseRepository courseRepository;

    private CourseService courseService;

    private Course course;
    private Employee employee;

    @BeforeEach
    public void before() {
        courseService = new CourseService(courseRepository, employeeValidationService);

        course = new Course();
        course.setHours(10);
        course.setStartDate(LocalDate.of(2022, 10, 10));
        course.setEndDate(LocalDate.of(2022, 11, 11));

        employee = new Employee();

        doReturn(course).when(courseRepository).save(course);
    }

    @AfterEach
    public void after() {
        course = null;
        employee = null;
    }

    @Test
    public void Should_AddCourseForEmployee() {
        courseService.addCourseForEmployee(employee, course);

        assertEquals(1, employee.getCourses().size());
        assertEquals(employee, course.getEmployee());
    }

    @Test
    public void Should_ReturnCoursesForEmployee_WhenCalled_GetCoursesForEmployee() {
        courseService.addCourseForEmployee(employee, course);

        Course course1 = new Course();
        course1.setHours(10);
        course1.setStartDate(LocalDate.of(2022, 10, 10));
        course1.setEndDate(LocalDate.of(2022, 11, 11));

        courseService.addCourseForEmployee(employee, course1);

        System.out.println(employee.getCourses());

        assertEquals(2, employee.getCourses().size());
    }
}
