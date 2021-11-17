package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDataService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

}
