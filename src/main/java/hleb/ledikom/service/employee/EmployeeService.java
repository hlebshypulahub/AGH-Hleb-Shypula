package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.EmployeeCategory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.MONTHS;

@Service
public class EmployeeService {

    public Employee process(Employee employee) {
        if (employee.getEmployeeCategory() != EmployeeCategory.NONE && employee.getCategoryAssignmentDate() != null) {
            processCategoryDates(employee);

            if (employee.getCertificationExemptionReason() != null) {
                processExemption(employee);
            }
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

    private void processExemption(Employee employee) {
        switch (employee.getCertificationExemptionReason()) {
            case LESS_THAN_YEAR_WORK:
                employee.setCategoryAssignmentDeadlineDate(employee.getCategoryAssignmentDeadlineDate().plusMonths(employee.getCertificationExemptionReason().getMonthsDuration()));
                employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
                break;
//            case PREGNANCY:
//                employee.setExemptioned(true);
//                break;
            case CONSCRIPTION:
            case MATERNITY_LEAVE:
                if (employee.getExemptionEndDate() == null) {
                    employee.setExemptioned(true);
                } else {
                    if (employee.getCategoryAssignmentDeadlineDate().isBefore(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()))) {
                        employee.setCategoryAssignmentDeadlineDate(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
                        employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
                    }
                }
                break;
            case TREATMENT:
            case BUSINESS_TRIP:
                if (employee.getExemptionEndDate() == null) {
                    employee.setExemptioned(true);
                } else {
                    if (MONTHS.between(employee.getExemptionStartDate(), employee.getExemptionEndDate()) >= employee.getCertificationExemptionReason().getMonthsDuration()
                            && employee.getCategoryAssignmentDeadlineDate().isBefore(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()))) {
                        employee.setCategoryAssignmentDeadlineDate(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
                        employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
                    }
                }
                break;
        }
    }

    public Employee setCategory(Employee employee, EmployeeCategory employeeCategory, String categoryNumber, LocalDate categoryAssignmentDate) {
        employee.setEmployeeCategory(employeeCategory);
        employee.setCategoryNumber(categoryNumber);
        employee.setCategoryAssignmentDate(categoryAssignmentDate);
        employee.setCourseHoursSum(0);

        employee = process(employee);

        return employee;
    }
}
