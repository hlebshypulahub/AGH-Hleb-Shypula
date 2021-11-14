package hleb.ledikom.controller.employee;

import hleb.ledikom.model.employee.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping("/employee")
    public Employee getEmployee() {


        return null;
    }

}
