package hleb.ledikom.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Exemption;
import hleb.ledikom.service.employee.EmployeeExemptionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/// Unit tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeExemptionServiceTests {

    @Autowired
    EmployeeExemptionService employeeExemptionService;

    private Employee employee;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setExemptioned(false);
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void When_ExemptionEndDateIsNull_Should_SetExemptionedTrue() {
        employee.setExemption(Exemption.PREGNANCY);
        employee.setExemptionStartDate(LocalDate.of(2020, 5, 5));

        employeeExemptionService.process(employee);

        assertTrue(employee.isExemptioned());
    }

    @Test
    public void LessThanYearWork_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates_And_ClearExemption() {
        LocalDate date = LocalDate.of(2020, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.LESS_THAN_YEAR_WORK;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusYears(1);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate, employee.getCategoryAssignmentDeadlineDate());
        assertNull(employee.getExemption());
        assertNull(employee.getExemptionStartDate());
        assertNull(employee.getExemptionEndDate());
        assertFalse(employee.isExemptioned());
    }

    @Test
    public void LessThanYearWork_When_ExemptionEndDateNotNull_And_ExemptionDurationNotValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(10);

        employee.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(date, employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void LessThanYearWork_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesNotDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2021, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.LESS_THAN_YEAR_WORK;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusYears(1);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(date, employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Pregnancy_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.PREGNANCY;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Pregnancy_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates_2() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.PREGNANCY;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Conscription_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.CONSCRIPTION;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Conscription_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates_2() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.CONSCRIPTION;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Treatment_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.TREATMENT;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(5);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Treatment_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesNotDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.TREATMENT;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(date, employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void BusinessTrip_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.BUSINESS_TRIP;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(5);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void BusinessTrip_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesNotDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.BUSINESS_TRIP;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(date, employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Studies_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.STUDIES;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(10);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Studies_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates_2() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.STUDIES;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Studies_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesNotDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2021, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.STUDIES;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(date, employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void MaternityLeave_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2020, 7, 7);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.MATERNITY_LEAVE;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(10);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void MaternityLeave_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesAreDuringExemption_Should_RecalcAndSetCategoryDates_2() {
        LocalDate date = LocalDate.of(2020, 3, 3);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.MATERNITY_LEAVE;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(endDate.plusMonths(exemption.getMonthsOfExemption()), employee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void MaternityLeave_When_ExemptionEndDateNotNull_And_ExemptionDurationIsValid_And_DeadlineDatesNotDuringExemption_Should_RecalcAndSetCategoryDates() {
        LocalDate date = LocalDate.of(2021, 11, 11);

        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));

        Exemption exemption = Exemption.MATERNITY_LEAVE;

        LocalDate startDate = LocalDate.of(2020, 2, 2);
        LocalDate endDate = startDate.plusMonths(3);

        employee.setExemption(exemption);
        employee.setExemptionStartDate(startDate);
        employee.setExemptionEndDate(endDate);

        employeeExemptionService.process(employee);

        assertEquals(date, employee.getCategoryAssignmentDeadlineDate());
    }
}
