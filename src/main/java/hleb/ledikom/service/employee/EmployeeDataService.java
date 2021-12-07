package hleb.ledikom.service.employee;

import hleb.ledikom.exception.CategoryNotValidException;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;
import hleb.ledikom.model.employee.dto.EmployeeEducationPatchDto;
import hleb.ledikom.model.employee.dto.EmployeePatchDto;
import hleb.ledikom.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeDataService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private Validator validator;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee patch(Long id, EmployeePatchDto employeePatchDto) {
        Employee employee = findById(id);
        BeanUtils.copyProperties(employeePatchDto, employee);
        return save(employee);
    }

    public boolean categoryIsValid(Employee employee) {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        BeanUtils.copyProperties(employee, employeeCategoryPatchDto);
        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);
        if (violations.size() != 0) {
            throw new CategoryNotValidException("Category is not valid to add course");
        }

        return true;
    }

    public boolean educationIsValid(Employee employee) {
        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        BeanUtils.copyProperties(employee, employeeEducationPatchDto);
        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);
        if (violations.size() != 0) {
            throw new CategoryNotValidException("Education is not valid to add course");
        }

        return true;
    }

}
