package hleb.ledikom;

import hleb.ledikom.model.Employee;
import hleb.ledikom.model.EmployeeCategory;
import hleb.ledikom.model.NotificationTerm;
import hleb.ledikom.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    private Employee employee;
    private NotificationTerm notificationTerm;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.FIRST);
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee.setActive(true);

        employee = employeeService.process(employee);

        notificationTerm = new NotificationTerm();
    }

    @AfterEach
    public void after() {
        employee = null;
        notificationTerm = null;
    }

    /// Test 1
    @Test
    public void testEmployeeCategoryAssignmentDeadlineDateCategoryAssignedBeforeAct() {
        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 7, 23));
    }

    @Test
    public void testEmployeeNotificationCategoryAssignmentDeadlineDateCategoryAssignedBeforeAct() {
        notificationTerm.setDays((int) Math.abs(DAYS.between(Employee.ACT_ENTRY_INTO_FORCE_DATE
                .plusYears(Employee.CATEGORY_VERIFICATION_YEARS), LocalDate.of(2025, 7, 23))));
        assertEquals(Math.abs(DAYS.between(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    /// Test 2
    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedBeforeAct() {
        assertEquals(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 4, 23));
    }

    @Test
    public void testEmployeeNotificationDocsSubmitDeadlineDateCategoryAssignedBeforeAct() {
        notificationTerm.setDays((int) Math.abs(DAYS.between(Employee.ACT_ENTRY_INTO_FORCE_DATE
                .plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), LocalDate.of(2025, 7, 23))));
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    /// Test 3
    @Test
    public void testEmployeeCategoryAssignmentDeadlineDateCategoryAssignedAfterAct() {
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(LocalDate.of(2022, 3, 15));
        employee = employeeService.process(employee);
        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2027, 3, 15));
    }

    @Test
    public void testEmployeeNotificationCategoryAssignmentDeadlineDateCategoryAssignedAfterAct() {
        notificationTerm.setDays(LocalDate.of(2021, 1, 1).lengthOfYear());
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(LocalDate.of(2022, 3, 15));
        employee = employeeService.process(employee);
        assertEquals(Math.abs(DAYS.between(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 3, 15))), notificationTerm.getDays());
    }

    /// Test 4
    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedAfterAct() {
        LocalDate date = LocalDate.of(2022, 3, 15);
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        employee = employeeService.process(employee);
        assertEquals(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 12, 15));
    }

    @Test
    public void testEmployeeNotificationDocsSubmitDeadlineDateCategoryAssignedAfterAct() {
        LocalDate date = LocalDate.of(2022, 3, 15);
        notificationTerm.setDays((int) Math.abs(DAYS.between(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), date.plusYears(4))));
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        employee = employeeService.process(employee);
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 3, 15))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedSameDayAsAct() {
        LocalDate date = Employee.ACT_ENTRY_INTO_FORCE_DATE;
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        employee = employeeService.process(employee);
        assertEquals(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 4, 23));
    }

    @Test
    public void testEmployeeNotificationDocsSubmitDeadlineDateCategoryAssignedSameDayAsAct() {
        LocalDate date = Employee.ACT_ENTRY_INTO_FORCE_DATE;
        notificationTerm.setDays((int) Math.abs(DAYS.between(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), date.plusYears(4))));
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        employee = employeeService.process(employee);
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }
}
