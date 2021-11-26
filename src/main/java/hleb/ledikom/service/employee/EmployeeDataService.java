package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
