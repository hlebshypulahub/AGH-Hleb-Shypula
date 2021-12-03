package hleb.ledikom.controller.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.EmployeeEducationPatchDto;
import hleb.ledikom.service.employee.EmployeeDataService;
import hleb.ledikom.service.employee.EmployeeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class EmployeeController {

    @Autowired
    EmployeeDataService employeeDataService;

    @Autowired
    EmployeeValidationService employeeValidationService;

    @GetMapping("")
    public List<Employee> getEmployees() {
        return employeeDataService.getEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(employeeDataService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee newEmployee) {
        return ResponseEntity.ok(employeeDataService.findById(id)
                                                    .map(employee -> {
                                                        employee = new Employee(newEmployee);
                                                        return employeeDataService.save(employee);
                                                    })
                                                    .orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id)));
    }

    @PatchMapping(path = "/{id}/education", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeEducationPatchDto employeeEducationPatchDto) throws JsonPatchException, JsonProcessingException {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeEducationPatchDto));
    }

    @PostMapping("/{id}/courses")
    public Employee addCourse(@PathVariable(name = "id") long employeeId, @RequestBody Course course) {
        return employeeDataService.addCourse(course, employeeId);
    }

}
