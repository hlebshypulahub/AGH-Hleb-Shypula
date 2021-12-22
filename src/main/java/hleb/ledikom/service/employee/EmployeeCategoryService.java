package hleb.ledikom.service.employee;

import hleb.ledikom.exception.EmptyCoursesListException;
import hleb.ledikom.model.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;

@Service
public class EmployeeCategoryService {

    public void process(Employee employee) {
        if (employee.getCategory() == Category.NONE) {
            processNoneCategory(employee);
        } else {
            processNotNoneCategory(employee);
        }
    }

    private void processNoneCategory(Employee employee) {
        /// У сотрудника нет курсов
        /// Или
        /// Прошло более 5 лет от даты последнего курса
        if (employee.getCourses().isEmpty()
                || employee.getCourses().stream().max(Comparator.comparing(Course::getStartDate))
                           .orElseThrow(() -> new EmptyCoursesListException("")).getStartDate().isBefore(LocalDate.now().minusYears(Employee.CATEGORY_VERIFICATION_YEARS))) {
            /// Прошло более 3 лет от даты окончания УЗ
            if (employee.getEduGraduationDate().isBefore(LocalDate.now().minusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS))) {
                /// ?????????????????????????????
                /// Дата возможного повышения устанавливается на { Дата окончания УЗ + 3 года }
                setNoneCategoryDates(employee, LocalDate.now().plusYears(1));
            }
            /// Прошло менее 3 лет от даты окончания УЗ
            else {
                /// Дедлайн категории устанавливается на { Дата окончания УЗ + 3 года }
                /// Дата возможного повышения устанавливается на { Дата окончания УЗ + 3 года }
                setNoneCategoryDates(employee, employee.getEduGraduationDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS));
            }
        }
        /// Прошло менее 5 лет от даты последнего курса
        else {
            Course course = employee.getCourses().stream().max(Comparator.comparing(Course::getStartDate)).orElseThrow(() -> new EmptyCoursesListException(""));
            /// Дедлайн категории устанавливается на { Дата курса + 5 лет }
            /// Дата возможного повышения устанавливается на { Дата окончания УЗ + 3 года }
            setNoneCategoryDates(employee, course.getStartDate().plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
        }
    }

    private void processNotNoneCategory(Employee employee) {
        if (employee.getCategoryAssignmentDate().isBefore(Employee.ACT_ENTRY_INTO_FORCE_DATE)) {
            setCategoryAssignmentDeadlineDate(employee, Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
        } else {
            setCategoryAssignmentDeadlineDate(employee, employee.getCategoryAssignmentDate().plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
        }

        employee.setCategoryPossiblePromotionDate(employee.getCategoryAssignmentDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS));
    }

    private void setNoneCategoryDates(Employee employee, LocalDate date) {
        employee.setCategoryNumber(null);
        employee.setCategoryAssignmentDate(null);
        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        employee.setCategoryPossiblePromotionDate(employee.getEduGraduationDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS));
    }

    public void setCategoryAssignmentDeadlineDate(Employee employee, LocalDate date) {
        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));
    }
}
