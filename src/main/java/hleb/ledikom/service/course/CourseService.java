package hleb.ledikom.service.course;

import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public Employee addCourse(Employee employee, Course course) {
        employee.getCourses().add(course);
        if (course.getStartDate().isAfter(employee.getCategoryAssignmentDeadlineDate().minusYears(Employee.CATEGORY_VERIFICATION_YEARS))) {
            employee = process(employee);
        }

        return employee;
    }

    public Employee process(Employee employee) {
        employee.setCourseHoursSum(employee.getCourses().stream().filter(course ->
                course.getStartDate().isAfter(employee.getCategoryAssignmentDeadlineDate().minusYears(Employee.CATEGORY_VERIFICATION_YEARS))
                        && course.getStartDate().isBefore(employee.getCategoryAssignmentDeadlineDate())
        ).mapToInt(Course::getHours).sum());

        return employee;
    }

}
