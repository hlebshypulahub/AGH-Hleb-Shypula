package hleb.ledikom.service.course;

import hleb.ledikom.model.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.repository.CourseRepository;
import hleb.ledikom.service.employee.EmployeeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EmployeeValidationService employeeValidationService;

    @Autowired
    public CourseService(CourseRepository courseRepository, EmployeeValidationService employeeValidationService) {
        this.courseRepository = courseRepository;
        this.employeeValidationService = employeeValidationService;
    }

    public Course addCourseForEmployee(Employee employee, Course course) {
        return addCourse(employee, course);
    }

    private Course addCourse(Employee employee, Course course) {
        employee.addCourse(course);

        if (employeeValidationService.categoryIsValid(employee) && employeeValidationService.educationIsValid(employee)) {
            process(employee);
        }

        return courseRepository.save(course);
    }

    public void process(@Valid Employee employee) {
        if (employeeValidationService.categoryIsValid(employee)) {
            if (employee.getCategory() == Category.NONE) {
                employee.setCourseHoursSum(employee.getCourses().stream().filter(course ->
                        courseIsBetweenDates(course,
                                employee.getCategoryAssignmentDeadlineDate().minusYears(Employee.CATEGORY_VERIFICATION_YEARS),
                                employee.getCategoryAssignmentDeadlineDate())
                ).mapToInt(Course::getHours).sum());
            } else {
                employee.setCourseHoursSum(employee.getCourses().stream().filter(course ->
                        courseIsBetweenDates(course,
                                employee.getCategoryAssignmentDate(), employee.getCategoryAssignmentDeadlineDate())
                ).mapToInt(Course::getHours).sum());
            }
        }
    }

    public List<Course> getCoursesForEmployee(Long employeeId) {
        return courseRepository.findAddByEmployeeId(employeeId);
    }

    private boolean courseIsBetweenDates(Course course, LocalDate date1, LocalDate date2) {
        return !course.getStartDate().isBefore(date1) && course.getEndDate().isBefore(date2);
    }
}
