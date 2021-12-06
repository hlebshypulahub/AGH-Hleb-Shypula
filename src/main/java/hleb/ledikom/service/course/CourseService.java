package hleb.ledikom.service.course;

import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.CourseRepository;
import hleb.ledikom.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Course addCourse(Employee employee, Course course) {
        employee.addCourse(course);

        if (course.getStartDate().isAfter(employee.getCategoryAssignmentDeadlineDate().minusYears(Employee.CATEGORY_VERIFICATION_YEARS))) {
            process(employee);
        }

        return courseRepository.save(course);
    }

    public Employee process(Employee employee) {
        employee.setCourseHoursSum(employee.getCourses().stream().filter(course ->
                course.getStartDate().isAfter(employee.getCategoryAssignmentDeadlineDate().minusYears(Employee.CATEGORY_VERIFICATION_YEARS))
                        && course.getStartDate().isBefore(employee.getCategoryAssignmentDeadlineDate())
        ).mapToInt(Course::getHours).sum());

        return employee;
    }

    public List<Course> getCoursesForEmployee(Long employeeId) {
        return courseRepository.findAddByEmployeeId(employeeId);
    }

}
