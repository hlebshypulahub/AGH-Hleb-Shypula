package hleb.ledikom.course;

import hleb.ledikom.model.course.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.model.enumeration.Education;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/// Integration tests
@SpringBootTest
@ActiveProfiles("test")
public class CourseServiceIntegrationTests {

    @Autowired
    CourseService courseService;

    @Autowired
    EmployeeDataService employeeDataService;

    private Course course;
    private Employee employee;

    @BeforeEach
    public void before() {
        course = new Course();
        course.setName("Course 1");
        course.setHours(10);
        course.setStartDate(LocalDate.of(2022, 10, 10));
        course.setEndDate(LocalDate.of(2022, 11, 11));

        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Test");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");
    }

    @AfterEach
    public void after() {
        course = null;
        employee = null;
    }

    @Test
    public void Should_AddCourseForEmployee() {
        Employee newEmployee = employeeDataService.save(employee);

        Course newCourse = courseService.addCourseForEmployee(employee, course);

        assertNotNull(newCourse);
        assertEquals(newEmployee, course.getEmployee());
        assertNotNull(newCourse.getId());
        assertEquals("Course 1", newCourse.getName());
    }

    @Test
    public void Should_FindAllByEmployeeId() {
        employee = employeeDataService.save(employee);
        Long employeeId = employee.getId();

        Course course1 = new Course();
        course1.setName("Course 1");
        course1.setHours(20);
        course1.setStartDate(LocalDate.of(2022, 10, 10));
        course1.setEndDate(LocalDate.of(2022, 11, 11));

        courseService.addCourseForEmployee(employee, course1);
        courseService.addCourseForEmployee(employee, course);

        List<Course> courses = courseService.getCoursesForEmployee(employeeId);

        assertEquals(30, courses.stream().mapToInt(Course::getHours).sum());
    }

    @Test
    public void Should_Process_When_EmployeeEducationAndCategoryAreValid_And_CourseIsBetweenDates() {
        employee.setCategory(Category.FIRST);
        employee.setQualification("Farmaceuta");
        employee.setCategoryNumber("285");
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 4, 4));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2025, 4, 4));
        employee.setEducation(Education.HIGHER);
        employee.setEduName("AGH");
        employee.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employeeDataService.save(employee);

        courseService.addCourseForEmployee(employee, course);

        assertEquals(10, employee.getCourseHoursSum());
    }

    @Test
    public void ShouldNot_Process_When_EmployeeEducationNotValid_And_CourseIsBetweenDates() {
        employee.setCategory(Category.FIRST);
        employee.setQualification("Farmaceuta");
        employee.setCategoryNumber("285");
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 4, 4));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2025, 4, 4));
        employee.setEducation(Education.HIGHER);
        employee.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employeeDataService.save(employee);

        courseService.addCourseForEmployee(employee, course);

        assertEquals(0, employee.getCourseHoursSum());
    }

    @Test
    public void ShouldNot_Process_When_EmployeeEducationAndCategoryAreValid_And_CourseIsNotBetweenDates() {
        employee.setCategory(Category.FIRST);
        employee.setQualification("Farmaceuta");
        employee.setCategoryNumber("285");
        employee.setCategoryAssignmentDate(LocalDate.of(2016, 4, 4));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 4, 4));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2021, 4, 4));
        employee.setEducation(Education.HIGHER);
        employee.setEduName("AGH");
        employee.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employeeDataService.save(employee);

        courseService.addCourseForEmployee(employee, course);

        assertEquals(0, employee.getCourseHoursSum());
    }

}
