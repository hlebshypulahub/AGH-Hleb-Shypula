package hleb.ledikom.employee.service;

import hleb.ledikom.model.employee.CertificationExemptionReason;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.EmployeeCategory;
import hleb.ledikom.model.employee.NotificationTerm;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeExemptionServiceTests {

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

    /// Test 5
    @Test
    public void testEmployeeLessThanYearWorkExemption() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.LESS_THAN_YEAR_WORK);
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(LocalDate.of(2027, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
        assertEquals(LocalDate.of(2027, 4, 23), employeeBeforeAct.getDocsSubmitDeadlineDate());
    }

    /// Test 6
    @Test
    public void testEmployeePregnancyExemption() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.PREGNANCY);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(employeeBeforeAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 7, 23));
        assertTrue(employeeBeforeAct.isExemptioned());
    }

    /// Test 7
    @Test
    public void testEmployeeConscriptionExemptionStart() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.CONSCRIPTION);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(LocalDate.of(2026, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
        assertTrue(employeeBeforeAct.isExemptioned());
    }

//    /// Test 8
//    @Test
//    public void testEmployeeConscriptionExemptionStartEnd() {
//        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.CONSCRIPTION);
//        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
//        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 10, 14));
//        employeeBeforeAct = employeeService.process(employeeBeforeAct);
//        assertEquals(LocalDate.of(2027, 10, 14), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
//        assertFalse(employeeBeforeAct.isExemptioned());
//    }

    /// Test 9
    @Test
    public void testEmployeeTreatmentExemptionStart() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.TREATMENT);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2026, 1, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(LocalDate.of(2026, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
        assertTrue(employeeBeforeAct.isExemptioned());
    }

//    /// Test 10
//    @Test
//    public void testEmployeeTreatmentExemptionStartEnd() {
//        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.TREATMENT);
//        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2026, 1, 5));
//        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 6, 12));
//        employeeBeforeAct = employeeService.process(employeeBeforeAct);
//        assertEquals(LocalDate.of(2026, 12, 12), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
//        assertFalse(employeeBeforeAct.isExemptioned());
//    }

//    /// Test 11
//    @Test
//    public void testEmployeeTreatmentExemptionStartEndLessThanMonthsOfExemptionDuration() {
//        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.TREATMENT);
//        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2026, 1, 5));
//        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 3, 12));
//        employeeBeforeAct = employeeService.process(employeeBeforeAct);
//        assertEquals(LocalDate.of(2026, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
//        assertFalse(employeeBeforeAct.isExemptioned());
//    }

    /// Test 12
    @Test
    public void testEmployeeBusinessTripExemptionStart() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.BUSINESS_TRIP);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2026, 1, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(LocalDate.of(2026, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
        assertTrue(employeeBeforeAct.isExemptioned());
    }

//    /// Test 13
//    @Test
//    public void testEmployeeBusinessTripExemptionStartEnd() {
//        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.BUSINESS_TRIP);
//        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2026, 1, 5));
//        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 6, 12));
//        employeeBeforeAct = employeeService.process(employeeBeforeAct);
//        assertEquals(LocalDate.of(2026, 12, 12), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
//        assertFalse(employeeBeforeAct.isExemptioned());
//    }

//    /// Test 14
//    @Test
//    public void testEmployeeBusinessTripExemptionStartEndLessThanMonthsOfExemptionDuration() {
//        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.BUSINESS_TRIP);
//        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2026, 1, 5));
//        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 3, 12));
//        employeeBeforeAct = employeeService.process(employeeBeforeAct);
//        assertEquals(LocalDate.of(2026, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
//        assertFalse(employeeBeforeAct.isExemptioned());
//    }

    /// Test 15
    @Test
    public void testEmployeeMaternityLeaveExemptionStart() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.MATERNITY_LEAVE);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(LocalDate.of(2026, 7, 23), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
        assertTrue(employeeBeforeAct.isExemptioned());
    }

//    /// Test 16
//    @Test
//    public void testEmployeeMaternityLeaveExemptionStartEnd() {
//        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.MATERNITY_LEAVE);
//        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
//        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 10, 14));
//        employeeBeforeAct = employeeService.process(employeeBeforeAct);
//        assertEquals(LocalDate.of(2027, 10, 14), employeeBeforeAct.getCategoryAssignmentDeadlineDate());
//        assertFalse(employeeBeforeAct.isExemptioned());
//    }

    /// Test 17
    @Test
    public void testEmployeeStudiesExemption() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.STUDIES);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(employeeBeforeAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 7, 23));
        assertTrue(employeeBeforeAct.isExemptioned());
    }

    /// Test 18
    @Test
    public void testEmployeeStudiesExemptionWithEndDate() {
        employeeBeforeAct.setCertificationExemptionReason(CertificationExemptionReason.STUDIES);
        employeeBeforeAct.setExemptionStartDate(LocalDate.of(2025, 5, 5));
        employeeBeforeAct.setExemptionEndDate(LocalDate.of(2026, 10, 5));
        employeeBeforeAct = employeeService.process(employeeBeforeAct);
        assertEquals(employeeBeforeAct.getCategoryAssignmentDeadlineDate(), LocalDate.of(2026, 7, 23));
        assertTrue(employeeBeforeAct.isExemptioned());
    }

    /// Test 19
    @Test
    public void testEmployeeExemptionEndDateAfterNowPregnancy() {
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.NONE);
        employee.setCategoryAssignmentDate(LocalDate.now().minusMonths(50));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.now().plusMonths(10));

        employee.setCertificationExemptionReason(CertificationExemptionReason.PREGNANCY);
        employee.setExemptionStartDate(LocalDate.now().minusMonths(1));
        employee.setExemptionEndDate(LocalDate.now().plusMonths(1));

        employee = employeeService.process(employee);

        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.now().plusMonths(10));
        assertTrue(employee.isExemptioned());
    }

    /// Test 20
    @Test
    public void testEmployeeExemptionEndDateBeforeNowPregnancy() {
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.NONE);
        employee.setCategoryAssignmentDate(LocalDate.now().minusMonths(50));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.now().plusMonths(10));

        employee.setCertificationExemptionReason(CertificationExemptionReason.PREGNANCY);
        employee.setExemptionStartDate(LocalDate.now().minusMonths(3));
        employee.setExemptionEndDate(LocalDate.now().minusMonths(1));

        employee = employeeService.process(employee);

        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.now().plusMonths(10));
        assertFalse(employee.isExemptioned());
    }

    /// Test 21
    @Test
    public void testEmployeeExemptionEndDateAfterNowConscription() {
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.NONE);
        employee.setCategoryAssignmentDate(LocalDate.now().minusMonths(50));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.now().plusMonths(10));

        employee.setCertificationExemptionReason(CertificationExemptionReason.CONSCRIPTION);
        employee.setExemptionStartDate(LocalDate.now().minusMonths(1));
        employee.setExemptionEndDate(LocalDate.now().plusMonths(1));

        employee = employeeService.process(employee);

        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.now().plusMonths(10));
        assertTrue(employee.isExemptioned());
    }

    /// Test 22
    @Test
    public void testEmployeeExemptionEndDateBeforeNowConscription() {
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.NONE);
        employee.setCategoryAssignmentDate(LocalDate.now().minusMonths(50));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.now().plusMonths(10));

        employee.setCertificationExemptionReason(CertificationExemptionReason.CONSCRIPTION);
        employee.setExemptionStartDate(LocalDate.now().minusMonths(5));
        employee.setExemptionEndDate(LocalDate.now().minusMonths(4));

        employee = employeeService.process(employee);

        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.now().plusMonths(10));
        assertFalse(employee.isExemptioned());
    }

    /// Test 23
    @Test
    public void testEmployeeExemptionEndDateBeforeNowAndLessMonthsConscription() {
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.NONE);
        employee.setCategoryAssignmentDate(LocalDate.now().minusMonths(50));
        employee.setCategoryAssignmentDeadlineDate(LocalDate.now().plusMonths(10));

        employee.setCertificationExemptionReason(CertificationExemptionReason.CONSCRIPTION);
        employee.setExemptionStartDate(LocalDate.now().minusMonths(2));
        employee.setExemptionEndDate(LocalDate.now().minusMonths(1));

        employee = employeeService.process(employee);

        assertEquals(employee.getCategoryAssignmentDeadlineDate(), LocalDate.now().minusMonths(1).plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
        assertFalse(employee.isExemptioned());
    }
}
