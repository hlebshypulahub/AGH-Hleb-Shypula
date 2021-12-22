package hleb.ledikom.controller.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.dto.*;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final EmployeeDataService employeeDataService;

    @Autowired
    public EmployeeController(EmployeeDataService employeeDataService) {
        this.employeeDataService = employeeDataService;
    }

    @GetMapping("")
    public List<Employee> getEmployees() {
        return employeeDataService.getAll();
    }

    @GetMapping("/for-course-plan")
    public List<Employee> getEmployeesForCoursePlan() {
        return employeeDataService.getAllForCoursePlan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(employeeDataService.findById(id));
    }

    @PatchMapping(path = "/{id}/education", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeEducationPatchDto employeeEducationPatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeEducationPatchDto));
    }

    @PatchMapping(path = "/{id}/category", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeCategoryPatchDto employeeCategoryPatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeCategoryPatchDto));
    }

    @PatchMapping(path = "/{id}/category-deadline", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeCategoryDeadlinePatchDto));
    }

    @PatchMapping(path = "/{id}/active", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeActivePatchDto employeeActivePatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeActivePatchDto));
    }

    @PatchMapping(path = "/{id}/exemption", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeExemptionPatchDto employeeExemptionPatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeExemptionPatchDto));
    }
}
