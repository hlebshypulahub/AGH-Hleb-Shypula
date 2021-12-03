package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.EmployeePatchDto;
import hleb.ledikom.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDataService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee addCourse(Course course, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + employeeId));

        employee.addCourse(course);

        return employeeRepository.save(employee);
    }

    public Employee patch(Long id, EmployeePatchDto employeePatchDto) {
        Employee employee = findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));
        BeanUtils.copyProperties(employeePatchDto, employee);
        return save(employee);
    }

}
