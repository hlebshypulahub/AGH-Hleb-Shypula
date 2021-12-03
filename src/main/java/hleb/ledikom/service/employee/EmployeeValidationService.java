package hleb.ledikom.service.employee;

import hleb.ledikom.exception.EmployeeEducationException;
import hleb.ledikom.model.employee.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeValidationService {

    public boolean validateEmployeeEducation(Employee employee) {
        if (employee.getEducation() == null) {
            if (StringUtils.isNotBlank(employee.getEduName()) || employee.getEduGraduationDate() != null) {
                throw new EmployeeEducationException("Należy podać rodzaj wykształcenia");
            }
        } else {
            if (StringUtils.isNotBlank(employee.getEduName())) {
                throw new EmployeeEducationException("Należy podać nazwę szkoły");
            } else if (employee.getEduGraduationDate() != null) {
                throw new EmployeeEducationException("Należy podać datę zakończenia studiów");
            }
        }

        return true;
    }

}
