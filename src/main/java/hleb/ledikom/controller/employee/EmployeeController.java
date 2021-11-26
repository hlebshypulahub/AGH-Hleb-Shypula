package hleb.ledikom.controller.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class EmployeeController {

    @Autowired
    EmployeeDataService employeeDataService;

    @GetMapping("")
    public List<Employee> getEmployees() {
        return employeeDataService.getEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(employeeDataService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id)));
    }

}
