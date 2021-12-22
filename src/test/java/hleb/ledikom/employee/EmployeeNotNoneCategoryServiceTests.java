package hleb.ledikom.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.service.employee.EmployeeCategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Unit tests
public class EmployeeNotNoneCategoryServiceTests {

    private final EmployeeCategoryService employeeCategoryService = new EmployeeCategoryService();

    private Employee employee;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setCategory(Category.FIRST);
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void When_ProcessNotNoneCategory_Where_EmployeeCategoryAssignmentDate_Is_BeforeActEntryIntoForceDate_Then_DeadlineDate_Is_ActDatePlusCategoryVerYears() {
        LocalDate date = LocalDate.of(2020, 5, 5);

        employee.setCategoryAssignmentDate(date);

        employeeCategoryService.process(employee);

        assertEquals(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
        assertEquals(date.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
    }

    @Test
    public void When_ProcessNotNoneCategory_Where_EmployeeCategoryAssignmentDate_Is_AfterActEntryIntoForceDate_Then_DeadlineDate_Is_ActDatePlusCategoryVerYears() {
        LocalDate date = LocalDate.of(2022, 5, 5);

        employee.setCategoryAssignmentDate(date);

        employeeCategoryService.process(employee);

        assertEquals(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
        assertEquals(date.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
    }
}
