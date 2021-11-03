package hleb.ledikom;

import hleb.ledikom.model.Employee;
import hleb.ledikom.model.EmployeeCategory;
import hleb.ledikom.model.NotificationTerm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeCategoryDatesTests {

    private Employee employee;
    private NotificationTerm notificationTerm;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.FIRST);
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee.setActive(true);

        notificationTerm = new NotificationTerm();
    }

    @AfterEach
    public void after() {
        employee = null;
        notificationTerm = null;
    }

    @Test
    public void testEmployeeCategoryDeadlineDateWithAssignmentBeforeAct() {
        assertEquals(employee.getCategoryAssignmentDeadlineDate(), Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
    }

    @Test
    public void testEmployeeCategoryAssignmentDeadlineDateCategoryAssignedBeforeAct() {
        notificationTerm.setDays((int) Math.abs(DAYS.between(Employee.ACT_ENTRY_INTO_FORCE_DATE
                .plusYears(Employee.CATEGORY_VERIFICATION_YEARS), LocalDate.of(2025, 7, 23))));
        assertEquals(Math.abs(DAYS.between(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedBeforeAct() {
        notificationTerm.setDays((int) Math.abs(DAYS.between(Employee.ACT_ENTRY_INTO_FORCE_DATE
                .plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), LocalDate.of(2025, 7, 23))));
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeCategoryAssignmentDeadlineDateCategoryAssignedAfterAct() {
        notificationTerm.setDays(LocalDate.of(2021, 1, 1).lengthOfYear());
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(LocalDate.of(2022, 3, 15));
        assertEquals(Math.abs(DAYS.between(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 3, 15))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedAfterAct() {
        LocalDate date = LocalDate.of(2022, 3, 15);
        notificationTerm.setDays((int) Math.abs(DAYS.between(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), date.plusYears(4))));
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2026, 3, 15))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeDocsSubmitDeadlineDateCategoryAssignedSameDayAsAct() {
        LocalDate date = Employee.ACT_ENTRY_INTO_FORCE_DATE;
        notificationTerm.setDays((int) Math.abs(DAYS.between(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), date.plusYears(4))));
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(date);
        assertEquals(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))), notificationTerm.getDays());
    }

    @Test
    public void testEmployeeCategoryAssignmentDate() {
        assertEquals(employee.getCategoryAssignmentDate(), LocalDate.of(2020, 5, 5));
    }
}
