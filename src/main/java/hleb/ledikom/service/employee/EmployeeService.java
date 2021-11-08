package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    public Employee process(Employee employee) {
        if (employee.getCategoryAssignmentDate() != null) {
            processCategoryDates(employee);
        }

        return employee;
    }

    private void processCategoryDates(Employee employee) {
        employee.setCategoryPossiblePromotionDate(employee.getCategoryAssignmentDate().plusYears(Employee.CATEGORY_POSSIBLE_PROMOTION_YEARS));

        if (employee.getCategoryAssignmentDate().isBefore(Employee.ACT_ENTRY_INTO_FORCE_DATE)) {
            employee.setCategoryAssignmentDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
            employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        } else {
            employee.setCategoryAssignmentDeadlineDate(employee.getCategoryAssignmentDate().plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
            employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        }
    }
}
