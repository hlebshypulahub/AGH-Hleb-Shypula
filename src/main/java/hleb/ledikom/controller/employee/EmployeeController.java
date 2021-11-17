package hleb.ledikom.controller.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    EmployeeDataService employeeDataService;

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeDataService.getEmployees();
    }

}
