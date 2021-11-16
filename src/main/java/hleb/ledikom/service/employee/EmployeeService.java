package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.employee.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.MONTHS;

@Service
public class EmployeeService {

    public Employee process(Employee employee) {
        processCategoryDates(employee);

        processExemption(employee);

        return employee;
    }

    private void processCategoryDates(Employee employee) {
        if (employee.getCategory() != Category.NONE && employee.getCategoryAssignmentDate() != null) {
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

    /// Запускать каждый день
    private void processExemption(Employee employee) {
        if (employee.getCertificationExemptionReason() != null) {
            switch (employee.getCertificationExemptionReason()) {
                case LESS_THAN_YEAR_WORK:
                    processExemptionNoTerms(employee);
                    break;
                case PREGNANCY:
                case STUDIES:
                case CONSCRIPTION:
                case MATERNITY_LEAVE:
                    processExemptionWithTerm(employee);
                    break;
                case TREATMENT:
                case BUSINESS_TRIP:
                    processExemptionWithTermAndDuration(employee);
                    break;
            }
        }
    }

    /// PREGNANCY, STUDIES, CONSCRIPTION, MATERNITY_LEAVE,
    private void processExemptionWithTerm(Employee employee) {
        if (employee.getExemptionEndDate() == null || employee.getExemptionEndDate().isAfter(LocalDate.now())) {
            employee.setExemptioned(true);
        } else {
            if (categoryAssignmentDeadlineIsDuringExemption(employee)) {
                setCategoryDatesAfterExemptionWithTerm(employee);
                employee.setExemptioned(false);
            }
        }
    }

    /// TREATMENT, BUSINESS_TRIP
    private void processExemptionWithTermAndDuration(Employee employee) {
        if (employee.getExemptionEndDate() == null || employee.getExemptionEndDate().isAfter(LocalDate.now())) {
            employee.setExemptioned(true);
        } else {
            if (MONTHS.between(employee.getExemptionStartDate(), employee.getExemptionEndDate()) >= employee.getCertificationExemptionReason().getMonthsDuration()
                    && categoryAssignmentDeadlineIsDuringExemption(employee)) {
                setCategoryDatesAfterExemptionWithTerm(employee);
                employee.setExemptioned(false);
            }
        }
    }

    /// LESS_THAN_YEAR_WORK
    private void processExemptionNoTerms(Employee employee) {
        employee.setCategoryAssignmentDeadlineDate(employee.getCategoryAssignmentDeadlineDate().plusMonths(employee.getCertificationExemptionReason().getMonthsDuration()));
        employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        employee.setExemptioned(false);
    }

    private boolean categoryAssignmentDeadlineIsDuringExemption(Employee employee) {
        return employee.getCategoryAssignmentDeadlineDate().isAfter(employee.getExemptionStartDate())
                && employee.getCategoryAssignmentDeadlineDate().isBefore(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
    }

    private void setCategoryDatesAfterExemptionWithTerm(Employee employee) {
        employee.setCategoryAssignmentDeadlineDate(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
        employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
    }

    public Employee setCategory(Employee employee, Category category, String categoryNumber, LocalDate categoryAssignmentDate) {
        employee.setCategory(category);
        employee.setCategoryNumber(categoryNumber);
        employee.setCategoryAssignmentDate(categoryAssignmentDate);
        employee.setCourseHoursSum(0);

        employee = process(employee);

        return employee;
    }
}
