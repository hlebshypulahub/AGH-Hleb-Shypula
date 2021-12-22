package hleb.ledikom.service.employee;

import hleb.ledikom.exception.CategoryNotValidException;
import hleb.ledikom.exception.EducationNotValidException;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.*;
import hleb.ledikom.repository.EmployeeRepository;
import hleb.ledikom.service.course.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final CourseService courseService;
    private final EmployeeValidationService employeeValidationService;
    private final EmployeeExemptionService employeeExemptionService;
    private final EmployeeFilteringService employeeFilteringService;
    private final EmployeeCategoryService employeeCategoryService;

    @Autowired
    public EmployeeDataService(EmployeeRepository employeeRepository, CourseService courseService,
                               EmployeeValidationService employeeValidationService, EmployeeExemptionService employeeExemptionService,
                               EmployeeFilteringService employeeFilteringService, EmployeeCategoryService employeeCategoryService) {
        this.employeeRepository = employeeRepository;
        this.courseService = courseService;
        this.employeeValidationService = employeeValidationService;
        this.employeeExemptionService = employeeExemptionService;
        this.employeeFilteringService = employeeFilteringService;
        this.employeeCategoryService = employeeCategoryService;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllForCoursePlan() {
        return employeeFilteringService.filterForCoursePlan(employeeRepository.findAll());
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee patch(Long id, EmployeePatchDto employeePatchDto) {
        Employee employee = findById(id);
        patch(employee, employeePatchDto);
        return save(employee);
    }

    private void patch(Employee employee, EmployeePatchDto employeePatchDto) {
        if (!employee.isExemptioned()) {
            if (employeePatchDto instanceof EmployeeCategoryPatchDto) {
                patchCategory(employee, employeePatchDto);
            } else if (employeePatchDto instanceof EmployeeCategoryDeadlinePatchDto) {
                patchCategoryDeadline(employee, employeePatchDto);
            }
        }

        if (employeePatchDto instanceof EmployeeExemptionPatchDto) {
            patchEmployeeExemption(employee, employeePatchDto);
        } else if (employeePatchDto instanceof EmployeeEducationPatchDto) {
            patchEmployeeEducation(employee, employeePatchDto);
        } else if (employeePatchDto instanceof EmployeeActivePatchDto) {
            patchEmployeeActive(employee, employeePatchDto);
        }
    }

    private void patchCategory(Employee employee, EmployeePatchDto employeePatchDto) {
        if (!employeeValidationService.educationIsValid(employee)) {
            throw new EducationNotValidException("Education is not valid to add category");
        }
        BeanUtils.copyProperties(employeePatchDto, employee);
        employeeCategoryService.process(employee);
        courseService.process(employee);
    }

    private void patchCategoryDeadline(Employee employee, EmployeePatchDto employeePatchDto) {
        if (!employeeValidationService.categoryIsValid(employee)) {
            throw new CategoryNotValidException("Category is not valid to edit assignment deadline date");
        }
        BeanUtils.copyProperties(employeePatchDto, employee);
        employeeCategoryService.setCategoryAssignmentDeadlineDate(employee, employee.getCategoryAssignmentDeadlineDate());
        courseService.process(employee);
    }

    private void patchEmployeeExemption(Employee employee, EmployeePatchDto employeePatchDto) {
        if (!employeeValidationService.categoryIsValid(employee)) {
            throw new CategoryNotValidException("Category is not valid to add exemption");
        }
        BeanUtils.copyProperties(employeePatchDto, employee);
        employeeExemptionService.process(employee);
    }

    private void patchEmployeeEducation(Employee employee, EmployeePatchDto employeePatchDto) {
        BeanUtils.copyProperties(employeePatchDto, employee);
    }

    private void patchEmployeeActive(Employee employee, EmployeePatchDto employeePatchDto) {
        BeanUtils.copyProperties(employeePatchDto, employee);
        System.out.println(employeePatchDto);
        employeeExemptionService.process(employee);
    }
}
