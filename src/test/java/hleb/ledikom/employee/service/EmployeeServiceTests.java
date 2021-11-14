package hleb.ledikom.employee.service;

import hleb.ledikom.model.employee.*;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CourseService courseService;

    private Employee employeeBeforeAct;
    private Employee employeeAfterAct;
    private NotificationTerm notificationTerm;

    @BeforeEach
    public void before() {
        /// Employee with category assigned before act
        employeeBeforeAct = new Employee();
        employeeBeforeAct.setEmployeeCategory(EmployeeCategory.FIRST);
        employeeBeforeAct.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));

        employeeBeforeAct.setCourses(new HashSet<>());

        employeeBeforeAct = employeeService.process(employeeBeforeAct);

        /// Employee with category assigned after act
        employeeAfterAct = new Employee();
        employeeAfterAct.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employeeAfterAct.setCategoryAssignmentDate(LocalDate.of(2022, 3, 15));
        employeeAfterAct = employeeService.process(employeeAfterAct);

        employeeAfterAct = employeeService.process(employeeAfterAct);

        /// Notification term
        notificationTerm = new NotificationTerm();
    }

    @AfterEach
    public void after() {
        employeeBeforeAct = null;
        employeeAfterAct = null;
        notificationTerm = null;
    }

    /// Test 1
    @Test
    public void testEmployeeCategoryAssignmentDeadlineDateCategoryAssignedBeforeAct() {
        assertEquals(employeeBeforeAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 7, 23));
    }

    @Test
    public void testEmployeeNotificationCategoryAssignmentDeadlineDateCategoryAssignedBeforeAct() {
        notificationTerm.setDays((int) Math.abs(DAYS.between(Employee.ACT_ENTRY_INTO_FORCE_DATE
                .plusYears(Employee.CATEGORY_VERIFICATION_YEARS), LocalDate.of(2025, 7, 23))));
        assertEquals(Math.abs(DAYS.between(employeeBeforeAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    /// Test 2
    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedBeforeAct() {
        assertEquals(employeeBeforeAct.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 4, 23));
    }

    @Test
    public void testEmployeeNotificationDocsSubmitDeadlineDateCategoryAssignedBeforeAct() {
        notificationTerm.setDays((int) Math.abs(DAYS.between(Employee.ACT_ENTRY_INTO_FORCE_DATE
                .plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), LocalDate.of(2025, 7, 23))));
        assertEquals(Math.abs(DAYS.between(employeeBeforeAct.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    /// Test 3
    @Test
    public void testEmployeeCategoryAssignmentDeadlineDateCategoryAssignedAfterAct() {
        assertEquals(employeeAfterAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2027, 3, 15));
    }

    @Test
    public void testEmployeeNotificationCategoryAssignmentDeadlineDateCategoryAssignedAfterAct() {
        notificationTerm.setDays(LocalDate.of(2021, 1, 1).lengthOfYear());
        assertEquals(Math.abs(DAYS.between(employeeAfterAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 3, 15))), notificationTerm.getDays());
    }

    /// Test 4
    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedAfterAct() {
        LocalDate date = LocalDate.of(2022, 3, 15);
        assertEquals(employeeAfterAct.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 12, 15));
    }

    @Test
    public void testEmployeeNotificationDocsSubmitDeadlineDateCategoryAssignedAfterAct() {
        LocalDate date = LocalDate.of(2022, 3, 15);
        notificationTerm.setDays((int) Math.abs(DAYS.between(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), date.plusYears(4))));
        assertEquals(Math.abs(DAYS.between(employeeAfterAct.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 3, 15))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedSameDayAsAct() {
        LocalDate date = Employee.ACT_ENTRY_INTO_FORCE_DATE;
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        employee = employeeService.process(employee);
        assertEquals(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 4, 23));
    }

    @Test
    public void testEmployeeNotificationDocsSubmitDeadlineDateCategoryAssignedSameDayAsAct() {
        LocalDate date = Employee.ACT_ENTRY_INTO_FORCE_DATE;
        notificationTerm.setDays((int) Math.abs(DAYS.between(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), date.plusYears(4))));
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        employee = employeeService.process(employee);
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeDoubleProcess() {
        Employee employeeBeforeProcess = new Employee(employeeBeforeAct);
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        Employee employeeAfterProcess = new Employee(employeeBeforeAct);
        assertEquals(employeeBeforeProcess.toString(), employeeAfterProcess.toString());
        assertNotSame(employeeBeforeProcess, employeeAfterProcess);
    }

    /// Test 16
    @Test
    public void testEmployeeSetCategory() {
        Course course = new Course();
        course.setHours(50);
        course.setStartDate(LocalDate.of(2022, 5, 5));
        course.setEndDate(LocalDate.of(2022, 5, 10));
        employeeBeforeAct = courseService.addCourse(employeeBeforeAct, course);

        assertEquals(50, employeeBeforeAct.getCourseHoursSum());

        employeeBeforeAct = employeeService.setCategory(employeeBeforeAct, EmployeeCategory.HIGHEST, "Kat n. 430", LocalDate.of(2025, 5,5));

        assertEquals(0, employeeBeforeAct.getCourseHoursSum());
    }
}
