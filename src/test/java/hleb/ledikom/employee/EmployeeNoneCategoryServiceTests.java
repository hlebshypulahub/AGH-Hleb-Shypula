package hleb.ledikom.employee;

import hleb.ledikom.model.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.model.enumeration.Education;
import hleb.ledikom.service.employee.EmployeeCategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Unit tests
public class EmployeeNoneCategoryServiceTests {

    private final EmployeeCategoryService employeeCategoryService = new EmployeeCategoryService();

    private Employee employee;
    private Course course;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setCourses(new HashSet<>());
        employee.setCategory(Category.NONE);
        employee.setEducation(Education.HIGHER);

        course = new Course();
        course.setName("Course 1");
        course.setHours(10);
    }

    @AfterEach
    public void after() {
        employee = null;
        course = null;
    }

    @Test
    public void When_ProcessNoneCategory_Where_EmployeeHasNoCourses_And_3YearsHaveLeftFromEduGraduationDate_Then_CategoryAssignmentDeadLineDate_Is_NowPlus1Year() {
        employee.setEduGraduationDate(LocalDate.of(2015, 5, 5));

        employeeCategoryService.process(employee);

        assertEquals(LocalDate.now().plusYears(1), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(employee.getEduGraduationDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
        assertEquals(LocalDate.now().plusYears(1).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
    }

    @Test
    public void When_ProcessNoneCategory_Where_EmployeeHasNoCourses_And_3YearsHaveNotLeftFromEduGraduationDate_Then_CategoryAssignmentDeadLineDate_Is_EduGraduationDatePlusWorkExToPromotionYears() {
        LocalDate eduGraduationDate = LocalDate.now().minusYears(1);
        employee.setEduGraduationDate(eduGraduationDate);

        employeeCategoryService.process(employee);

        assertEquals(eduGraduationDate.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(eduGraduationDate.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
        assertEquals(eduGraduationDate.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
    }

    @Test
    public void When_ProcessNoneCategory_Where_5YearsHaveLeftFromLastCourse_And_3YearsHaveLeftFromEduGraduationDate_Then_CategoryAssignmentDeadLineDate_Is_NowPlus1Year() {
        employee.setEduGraduationDate(LocalDate.of(2015, 5, 5));

        course.setStartDate(LocalDate.of(2015, 10, 10));
        course.setEndDate(LocalDate.of(2015, 11, 11));

        employee.addCourse(course);

        employeeCategoryService.process(employee);

        assertEquals(LocalDate.now().plusYears(1), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(employee.getEduGraduationDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
        assertEquals(LocalDate.now().plusYears(1).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
    }

    @Test
    public void When_ProcessNoneCategory_Where_5YearsHaveLeftFromLastCourse_And_3YearsHaveNotLeftFromEduGraduationDate_Then_CategoryAssignmentDeadLineDate_Is_EduGraduationDatePlusWorkExToPromotionYears() {
        employee.setEduGraduationDate(LocalDate.of(2015, 5, 5));

        LocalDate eduGraduationDate = LocalDate.now().minusYears(1);
        employee.setEduGraduationDate(eduGraduationDate);

        course.setStartDate(LocalDate.of(2015, 10, 10));
        course.setEndDate(LocalDate.of(2015, 11, 11));

        employee.addCourse(course);

        employeeCategoryService.process(employee);

        assertEquals(eduGraduationDate.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(eduGraduationDate.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
        assertEquals(eduGraduationDate.plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
    }

    @Test
    public void When_ProcessNoneCategory_Where_5YearsHaveNotLeftFromLastCourse_And_3YearsHaveNotLeftFromEduGraduationDate_Then_CategoryAssignmentDeadLineDate_Is_LastCourseStartDatePlusCategoryVerYears() {
        employee.setEduGraduationDate(LocalDate.of(2015, 5, 5));

        LocalDate date = LocalDate.now().minusYears(2);

        course.setStartDate(date);
        course.setEndDate(date.plusDays(10));

        employee.addCourse(course);

        employeeCategoryService.process(employee);

        assertEquals(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS), employee.getCategoryAssignmentDeadlineDate());
        assertEquals(employee.getEduGraduationDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS), employee.getCategoryPossiblePromotionDate());
        assertEquals(date.plusYears(Employee.CATEGORY_VERIFICATION_YEARS).minusMonths(Employee.DOCS_SUBMIT_MONTHS), employee.getDocsSubmitDeadlineDate());
    }
}
