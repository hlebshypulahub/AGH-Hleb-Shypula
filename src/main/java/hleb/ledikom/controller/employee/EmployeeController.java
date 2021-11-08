package hleb.ledikom.controller.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.EmployeeCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class EmployeeController {

    @GetMapping("/employee")
    public Employee getEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.FIRST);
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryAssignmentDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(5));
        employee.setDocsSubmitDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(4).plusMonths(9));
        employee.setActive(true);

        return employee;
    }

}
