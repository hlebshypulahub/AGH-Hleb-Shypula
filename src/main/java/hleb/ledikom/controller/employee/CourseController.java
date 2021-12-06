package hleb.ledikom.controller.employee;

import hleb.ledikom.model.employee.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/courses")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    EmployeeDataService employeeDataService;

    @GetMapping("for-employee/{id}")
    public List<Course> getCoursesForEmployee(@PathVariable Long id) {
        return courseService.getCoursesForEmployee(id);
    }

    @PostMapping("for-employee/{id}")
    public Course addCourseToEmployee(@PathVariable Long id, @RequestBody Course course) {
        Employee employee = employeeDataService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));
        courseService.addCourse(employee, course);
        return course;
    }
}
