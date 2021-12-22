package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.EmployeeCategoryPatchDto;
import hleb.ledikom.model.employee.dto.EmployeeEducationPatchDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class EmployeeValidationService {

    private final Validator validator;

    @Autowired
    public EmployeeValidationService(Validator validator) {
        this.validator = validator;
    }

    public boolean categoryIsValid(Employee employee) {
        EmployeeCategoryPatchDto employeeCategoryPatchDto = new EmployeeCategoryPatchDto();
        BeanUtils.copyProperties(employee, employeeCategoryPatchDto);
        Set<ConstraintViolation<EmployeeCategoryPatchDto>> violations = validator.validate(employeeCategoryPatchDto);
        return violations.isEmpty();
    }

    public boolean educationIsValid(Employee employee) {
        EmployeeEducationPatchDto employeeEducationPatchDto = new EmployeeEducationPatchDto();
        BeanUtils.copyProperties(employee, employeeEducationPatchDto);
        Set<ConstraintViolation<EmployeeEducationPatchDto>> violations = validator.validate(employeeEducationPatchDto);
        return violations.isEmpty();
    }
}
