package hleb.ledikom.service.employee;

import hleb.ledikom.exception.EmptyCoursesListException;
import hleb.ledikom.model.employee.Category;
import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.EmployeeRepository;
import hleb.ledikom.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;

@Service
public class EmployeeLogicService {

    @Autowired
    CourseService courseService;

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee processCategory(Employee employee) {
        if (employee.getCategory() == Category.NONE) {
            processNoneCategory(employee);
        } else {
            processNotNoneCategory(employee);
        }

        return employeeRepository.save(employee);
    }

    private Employee processNoneCategory(Employee employee) {
        /// У сотрудника нет курсов
        /// Или
        /// Прошло более 5 лет от даты последнего курса
        if (employee.getCourses().isEmpty()
                || employee.getCourses().stream().max(Comparator.comparing(Course::getStartDate))
                           .orElseThrow(() -> new EmptyCoursesListException("")).getStartDate().isBefore(LocalDate.now().minusYears(Employee.CATEGORY_VERIFICATION_YEARS))) {
            /// Прошло более 3 лет от даты окончания УЗ
            if (employee.getEduGraduationDate().isBefore(LocalDate.now().minusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS))) {
                /// ?
                return courseService.process(employee);
            }
            /// Прошло менее 3 лет от даты окончания УЗ
            else {
                /// Дедлайн категории устанавливается на { Дата окончания УЗ + 3 года }
                setNoneCategoryDates(employee, employee.getEduGraduationDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS));
                return courseService.process(employee);
            }
        }
        /// Прошло менее 5 лет от даты последнего курса
        else {
            Course course = employee.getCourses().stream().max(Comparator.comparing(Course::getStartDate)).orElseThrow(() -> new EmptyCoursesListException(""));
            /// Дедлайн категории устанавливается на { Дата курса + 5 лет }
            setNoneCategoryDates(employee, course.getStartDate().plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
            return courseService.process(employee);
        }
    }

    private Employee setNoneCategoryDates(Employee employee, LocalDate date) {
        employee.setCategoryNumber(null);
        employee.setCategoryAssignmentDate(null);
        employee.setCategoryAssignmentDeadlineDate(date);
        employee.setDocsSubmitDeadlineDate(date.minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        employee.setCategoryPossiblePromotionDate(date);

        return employee;
    }

    private Employee processNotNoneCategory(Employee employee) {
        if (employee.getCategoryAssignmentDate().isBefore(Employee.ACT_ENTRY_INTO_FORCE_DATE)) {
            employee.setCategoryAssignmentDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
            employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        } else {
            employee.setCategoryAssignmentDeadlineDate(employee.getCategoryAssignmentDate().plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
            employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
        }

        employee.setCategoryPossiblePromotionDate(employee.getCategoryAssignmentDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS));

        return courseService.process(employee);
    }

    public Employee process(Employee employee) {
//        processCategoryDates(employee);
//
//        processExemption(employee);

        return employee;
    }

//    private void processCategoryDates(Employee employee) {
//        if (employee.getCategory() != Category.NONE && employee.getCategoryAssignmentDate() != null) {
//            employee.setCategoryPossiblePromotionDate(employee.getCategoryAssignmentDate().plusYears(Employee.WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS));
//
//            if (employee.getCategoryAssignmentDate().isBefore(Employee.ACT_ENTRY_INTO_FORCE_DATE)) {
//                employee.setCategoryAssignmentDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
//                employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
//            } else {
//                employee.setCategoryAssignmentDeadlineDate(employee.getCategoryAssignmentDate().plusYears(Employee.CATEGORY_VERIFICATION_YEARS));
//                employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
//            }
//        }
//    }

//    /// Запускать каждый день
//    private void processExemption(Employee employee) {
//        if (employee.getCertificationExemptionReason() != null) {
//            switch (employee.getCertificationExemptionReason()) {
//                case LESS_THAN_YEAR_WORK:
//                    processExemptionNoTerms(employee);
//                    break;
//                case PREGNANCY:
//                case STUDIES:
//                case CONSCRIPTION:
//                case MATERNITY_LEAVE:
//                    processExemptionWithTerm(employee);
//                    break;
//                case TREATMENT:
//                case BUSINESS_TRIP:
//                    processExemptionWithTermAndDuration(employee);
//                    break;
//            }
//        }
//    }

//    /// PREGNANCY, STUDIES, CONSCRIPTION, MATERNITY_LEAVE,
//    private void processExemptionWithTerm(Employee employee) {
//        if (employee.getExemptionEndDate() == null || employee.getExemptionEndDate().isAfter(LocalDate.now())) {
//            employee.setExemptioned(true);
//        } else {
//            if (categoryAssignmentDeadlineIsDuringExemption(employee)) {
//                setCategoryDatesAfterExemptionWithTerm(employee);
//                employee.setExemptioned(false);
//            }
//        }
//    }
//
//    /// TREATMENT, BUSINESS_TRIP
//    private void processExemptionWithTermAndDuration(Employee employee) {
//        if (employee.getExemptionEndDate() == null || employee.getExemptionEndDate().isAfter(LocalDate.now())) {
//            employee.setExemptioned(true);
//        } else {
//            if (MONTHS.between(employee.getExemptionStartDate(), employee.getExemptionEndDate()) >= employee.getCertificationExemptionReason().getMonthsDuration()
//                    && categoryAssignmentDeadlineIsDuringExemption(employee)) {
//                setCategoryDatesAfterExemptionWithTerm(employee);
//                employee.setExemptioned(false);
//            }
//        }
//    }
//
//    /// LESS_THAN_YEAR_WORK
//    private void processExemptionNoTerms(Employee employee) {
//        employee.setCategoryAssignmentDeadlineDate(employee.getCategoryAssignmentDeadlineDate().plusMonths(employee.getCertificationExemptionReason().getMonthsDuration()));
//        employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
//        employee.setExemptioned(false);
//    }

    private boolean categoryAssignmentDeadlineIsDuringExemption(Employee employee) {
        return employee.getCategoryAssignmentDeadlineDate().isAfter(employee.getExemptionStartDate())
                && employee.getCategoryAssignmentDeadlineDate().isBefore(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
    }

//    private void setCategoryDatesAfterExemptionWithTerm(Employee employee) {
//        employee.setCategoryAssignmentDeadlineDate(employee.getExemptionEndDate().plusMonths(employee.getCertificationExemptionReason().getMonthsOfExemption()));
//        employee.setDocsSubmitDeadlineDate(employee.getCategoryAssignmentDeadlineDate().minusMonths(Employee.DOCS_SUBMIT_MONTHS));
//    }

    public Employee setCategory(Employee employee, Category category, String categoryNumber, LocalDate categoryAssignmentDate) {
//        employee.setCategory(category);
//        employee.setCategoryNumber(categoryNumber);
//        employee.setCategoryAssignmentDate(categoryAssignmentDate);
//        employee.setCourseHoursSum(0);
//
//        employee = process(employee);

        return employee;
    }
}
