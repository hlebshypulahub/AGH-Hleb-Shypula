package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EmployeeExemptionService {

    private final EmployeeCategoryService employeeCategoryService;

    @Autowired
    public EmployeeExemptionService(EmployeeCategoryService employeeCategoryService) {
        this.employeeCategoryService = employeeCategoryService;
    }

    public void process(Employee employee) {
        if (employee.getExemption() == null) {
            clearExemption(employee);
        }
        /// Если не указана дата окончания освобождения
        else if (employee.getExemptionEndDate() == null) {
            /// Устанавливаются дата начала освобождения и причина.
            /// А также статус ставится на "освобожден"
            employee.setExemptioned(true);
        }
        /// Если дата окончания указана
        else {
            /// Если длительность освобождения подходит - это для тех по 4+ месяцев. То есть проверяет,
            /// что от даты начала осво-ния до даты окончания прошло больше, чем { количество месяцев * 30 дней (?) }
            /// А также попал ли дедлайн по документам на период освобождения + тот срок, на который освобождается по данной причине
            if (DeadlineDatesBetweenExemption(employee) && exemptionDurationIsValid(employee)) {
                /// Устанавливается дедлайн категории и дедлайн документов соответсвенно с освобождением
                employeeCategoryService.setCategoryAssignmentDeadlineDate(employee, employee.getExemptionEndDate().plusMonths(employee.getExemption().getMonthsOfExemption()));
            }

            /// Очищается освобождение, оно больше не нужно
            clearExemption(employee);
        }
    }

    private void clearExemption(Employee employee) {
        System.out.println(employee);
        employee.setExemptioned(false);
        employee.setExemption(null);
        employee.setExemptionStartDate(null);
        employee.setExemptionEndDate(null);
        System.out.println(employee);
    }

    private boolean DeadlineDatesBetweenExemption(Employee employee) {
        return (!employee.getDocsSubmitDeadlineDate().isBefore(employee.getExemptionStartDate())
                && !employee.getDocsSubmitDeadlineDate().isAfter(employee.getExemptionEndDate().plusMonths(employee.getExemption().getMonthsOfExemption())))
                || (!employee.getCategoryAssignmentDeadlineDate().isBefore(employee.getExemptionStartDate())
                && !employee.getCategoryAssignmentDeadlineDate().isAfter(employee.getExemptionEndDate().plusMonths(employee.getExemption().getMonthsOfExemption())));
    }

    private boolean exemptionDurationIsValid(Employee employee) {
        return DAYS.between(employee.getExemptionStartDate(), employee.getExemptionEndDate()) >= employee.getExemption().getMonthsDuration() * 30L;
    }
}
