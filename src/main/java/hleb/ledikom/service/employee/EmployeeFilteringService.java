package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Education;
import hleb.ledikom.model.notification.NotificationTerm;
import hleb.ledikom.repository.NotificationTermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeFilteringService {

    private final EmployeeValidationService employeeValidationService;
    private final NotificationTermRepository notificationTermRepository;

    @Autowired
    public EmployeeFilteringService(EmployeeValidationService employeeValidationService, NotificationTermRepository notificationTermRepository) {
        this.employeeValidationService = employeeValidationService;
        this.notificationTermRepository = notificationTermRepository;
    }

    public List<Employee> filterForCoursePlan(List<Employee> employees) {
        List<Employee> resultEmployees = new ArrayList<>();

        NotificationTerm notificationTerm = notificationTermRepository.findById(791L).orElse(new NotificationTerm(12, 24));

        for (@Valid Employee employee : employees) {
            if (employeeValidationService.educationIsValid(employee)
                    && employeeValidationService.categoryIsValid(employee)
                    && employee.isActive()
                    && (employee.getEducation() == Education.HIGHER && employee.getCategoryAssignmentDeadlineDate().isBefore(LocalDate.now().plusMonths(notificationTerm.getMonthsHigherEducation())))
                    || (employee.getEducation() == Education.SECONDARY && employee.getCategoryAssignmentDeadlineDate().isBefore(LocalDate.now().plusMonths(notificationTerm.getMonthsSecondaryEducation())))) {
                resultEmployees.add(employee);
            }
        }

        return resultEmployees;
    }

}
