package hleb.ledikom.controller.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
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

    @Autowired
    private ObjectMapper objectMapper;

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

//    @PutMapping("/{id}/update-education")
//    public ResponseEntity<Employee> updateEmployeeEducation(@PathVariable long id, @RequestBody Employee newEmployee) {
//        return ResponseEntity.ok(employeeDataService.findById(id)
//                                                    .map(employee -> {
//                                                        employeeValidationService.validateEmployeeEducation(newEmployee);
//                                                        employee = new Employee(newEmployee);
//                                                        return employeeDataService.save(employee);
//                                                    })
//                                                    .orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id)));
//    }

    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch) throws JsonPatchException, JsonProcessingException {
        Employee employee = employeeDataService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));

        @Valid
        Employee employeePatched = applyPatchToEmployee(jsonMergePatch, employee);

        System.out.println(employeePatched);

        return ResponseEntity.ok(employeeDataService.save(employeePatched));
    }

    private Employee applyPatchToEmployee(JsonMergePatch jsonMergePatch, Employee targetEmployee) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = jsonMergePatch.apply(objectMapper.convertValue(targetEmployee, JsonNode.class));
        return objectMapper.treeToValue(patched, Employee.class);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public Map<String, String> handleValidationExceptions(ConstraintViolationException ex) {
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

    @PostMapping("/{id}/courses")
    public Employee addCourse(@PathVariable(name = "id") long employeeId, @RequestBody Course course) {
        return employeeDataService.addCourse(course, employeeId);
    }

}
