package hleb.ledikom.employee;

import hleb.ledikom.exception.CategoryNotValidException;
import hleb.ledikom.exception.EducationNotValidException;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.EmployeeCategoryDeadlinePatchDto;
import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;
import hleb.ledikom.model.employee.dto.EmployeeEducationPatchDto;
import hleb.ledikom.model.employee.dto.EmployeeExemptionPatchDto;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.model.enumeration.Education;
import hleb.ledikom.model.enumeration.Exemption;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/// Integration tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeDataServiceIntegrationTests {

    @Autowired
    EmployeeDataService employeeDataService;

    private Employee employee;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Test");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void Should_SaveEmployee() {
        Employee newEmployee = employeeDataService.save(employee);

        assertNotNull(newEmployee);
        assertNotNull(newEmployee.getId());
        assertEquals("Test", newEmployee.getFullName());
    }

    @Test
    public void Should_FindById() {
        Employee newEmployee = employeeDataService.save(employee);

        Long id = newEmployee.getId();

        Employee foundEmployee = employeeDataService.findById(id);

        assertNotNull(foundEmployee);
        assertEquals(id, foundEmployee.getId());
        assertEquals(newEmployee.getFullName(), foundEmployee.getFullName());
    }

    @Test
    public void Should_GetAll() {
        /// Actual size
        int size = employeeDataService.getAll().size();

        LocalDate date = LocalDate.now();

        /// Employee 0
        employeeDataService.save(employee);

        /// Employee 1
        Employee employee1 = new Employee();
        employee1.setForeignId(1L);
        employee1.setFullName("Test");
        employee1.setHiringDate(LocalDate.of(2020, 5, 5));
        employee1.setJobFacility("Apteka 9");
        employee1.setPosition("Farmaceuta");

        employeeDataService.save(employee1);

        /// Employee 2
        Employee employee2 = new Employee();
        employee2.setForeignId(1L);
        employee2.setFullName("Test");
        employee2.setHiringDate(LocalDate.of(2020, 5, 5));
        employee2.setJobFacility("Apteka 9");
        employee2.setPosition("Farmaceuta");

        employeeDataService.save(employee2);

        /// Get
        List<Employee> employees = employeeDataService.getAll();

        assertEquals(size + 3, employees.size());
    }

    @Test
    public void Should_GetAllForCoursePlan() {
        /// Actual size
        int size = employeeDataService.getAllForCoursePlan().size();

        LocalDate date = LocalDate.now();

        /// Employee 0
        employee.setActive(true);
        employee.setEducation(Education.HIGHER);
        employee.setEduName("AGH");
        employee.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee.setCategory(Category.FIRST);
        employee.setCategoryNumber("285");
        employee.setQualification("Farmaceuta");
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryAssignmentDeadlineDate(date.plusYears(1).plusMonths(6));

        employeeDataService.save(employee);

        /// Employee 1
        Employee employee1 = new Employee();
        employee1.setForeignId(1L);
        employee1.setFullName("Test");
        employee1.setHiringDate(LocalDate.of(2020, 5, 5));
        employee1.setJobFacility("Apteka 9");
        employee1.setPosition("Farmaceuta");
        employee1.setActive(true);
        employee1.setEducation(Education.SECONDARY);
        employee1.setEduName("AGH");
        employee1.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee1.setCategory(Category.FIRST);
        employee1.setCategoryNumber("285");
        employee1.setQualification("Farmaceuta");
        employee1.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee1.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee1.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));
        employee1.setCategoryAssignmentDeadlineDate(date.plusMonths(6));

        employeeDataService.save(employee1);

        /// Employee 2
        Employee employee2 = new Employee();
        employee2.setForeignId(1L);
        employee2.setFullName("Test");
        employee2.setHiringDate(LocalDate.of(2020, 5, 5));
        employee2.setJobFacility("Apteka 9");
        employee2.setPosition("Farmaceuta");
        employee2.setActive(true);
        employee2.setEducation(Education.SECONDARY);
        employee2.setEduName("AGH");
        employee2.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee2.setCategory(Category.FIRST);
        employee2.setCategoryNumber("285");
        employee2.setQualification("Farmaceuta");
        employee2.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee2.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee2.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));
        employee2.setCategoryAssignmentDeadlineDate(date.plusYears(1).plusMonths(6));

        employeeDataService.save(employee2);

        /// Get
        List<Employee> employees = employeeDataService.getAllForCoursePlan();

        assertEquals(size + 2, employees.size());
    }

    @Test
    public void Should_PatchCategory_When_EmployeeNotExemptioned_AndEducationIsValid() {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        employeeEducationPatchDto.setEducation(Education.HIGHER);
        employeeEducationPatchDto.setEduName("AGH");
        employeeEducationPatchDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        BeanUtils.copyProperties(employeeEducationPatchDto, employee);
        BeanUtils.copyProperties(employeeCategoryPatchDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeCategoryPatchDto);

        assertNotNull(newEmployee);
        assertEquals(Category.FIRST, newEmployee.getCategory());
    }

    @Test
    public void Should_PatchCategory_When_EmployeeNotExemptioned_AndEducationNotValid() {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();

        employee = employeeDataService.save(employee);

        EducationNotValidException exception = assertThrows(EducationNotValidException.class, () -> {
            employeeDataService.patch(employee.getId(), employeeCategoryPatchDto);
        });

        assertEquals("Education is not valid to add category", exception.getMessage());
    }

    @Test
    public void Should_NotPatchCategory_When_EmployeeIsExemptioned_AndEducationIsValid() {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);

        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        employeeEducationPatchDto.setEducation(Education.HIGHER);
        employeeEducationPatchDto.setEduName("AGH");
        employeeEducationPatchDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        BeanUtils.copyProperties(employeeEducationPatchDto, employee);

        employee.setExemptioned(true);

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeCategoryPatchDto);

        assertNotNull(newEmployee);
        assertNull(newEmployee.getCategory());
    }

    @Test
    public void Should_PatchCategoryDeadline_When_EmployeeNotExemptioned_AndCategoryIsValid() {
        LocalDate date = LocalDate.of(2020, 5, 5);

        EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto = new EmployeeCategoryDeadlinePatchDto();
        employeeCategoryDeadlinePatchDto.setCategoryAssignmentDeadlineDate(date);

        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        employeeEducationPatchDto.setEducation(Education.HIGHER);
        employeeEducationPatchDto.setEduName("AGH");
        employeeEducationPatchDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeEducationPatchDto, employee);
        BeanUtils.copyProperties(employeeCategoryPatchDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeCategoryDeadlinePatchDto);

        assertNotNull(newEmployee);
        assertEquals(date, newEmployee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Should_NotPatchCategoryDeadline_When_EmployeeNotExemptioned_AndCategoryNotValid() {
        LocalDate date = LocalDate.of(2020, 5, 5);

        EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto = new EmployeeCategoryDeadlinePatchDto();
        employeeCategoryDeadlinePatchDto.setCategoryAssignmentDeadlineDate(date);

        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        employeeEducationPatchDto.setEducation(Education.HIGHER);
        employeeEducationPatchDto.setEduName("AGH");
        employeeEducationPatchDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
//        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeEducationPatchDto, employee);
        BeanUtils.copyProperties(employeeCategoryPatchDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        CategoryNotValidException exception = assertThrows(CategoryNotValidException.class, () -> {
            employeeDataService.patch(employee.getId(), employeeCategoryDeadlinePatchDto);
        });

        assertEquals("Category is not valid to edit assignment deadline date", exception.getMessage());
    }

    @Test
    public void Should_PatchExemptionWithEndDateIsNull_When_CategoryIsValid() {
        EmployeeExemptionPatchDto employeeExemptionPatchDto = new EmployeeExemptionPatchDto();
        employeeExemptionPatchDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionPatchDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));

        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeCategoryPatchDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeExemptionPatchDto);

        assertNotNull(newEmployee);
        assertEquals(Exemption.LESS_THAN_YEAR_WORK, newEmployee.getExemption());
    }

    @Test
    public void Should_PatchExemptionWithEndDateNotNull_When_CategoryIsValid() {
        EmployeeExemptionPatchDto employeeExemptionPatchDto = new EmployeeExemptionPatchDto();
        employeeExemptionPatchDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionPatchDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));
        employeeExemptionPatchDto.setExemptionEndDate(LocalDate.of(2021, 5, 5));

        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeCategoryPatchDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeExemptionPatchDto);

        assertNotNull(newEmployee);
        assertNull(newEmployee.getExemption());
    }

    @Test
    public void Should_NotPatchExemptionWithEndDateNotNull_When_CategoryNotValid() {
        EmployeeExemptionPatchDto employeeExemptionPatchDto = new EmployeeExemptionPatchDto();
        employeeExemptionPatchDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionPatchDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));
        employeeExemptionPatchDto.setExemptionEndDate(LocalDate.of(2021, 5, 5));

        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        employeeCategoryPatchDto.setCategory(Category.FIRST);
//        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryPatchDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryPatchDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeCategoryPatchDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        CategoryNotValidException exception = assertThrows(CategoryNotValidException.class, () -> {
            employeeDataService.patch(employee.getId(), employeeExemptionPatchDto);
        });

        assertEquals("Category is not valid to add exemption", exception.getMessage());
    }

    @Test
    public void Should_PatchEducation() {
        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        employeeEducationPatchDto.setEducation(Education.HIGHER);
        employeeEducationPatchDto.setEduName("AGH");
        employeeEducationPatchDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeEducationPatchDto);

        assertNotNull(newEmployee);
        assertEquals(Education.HIGHER, newEmployee.getEducation());
    }
}
