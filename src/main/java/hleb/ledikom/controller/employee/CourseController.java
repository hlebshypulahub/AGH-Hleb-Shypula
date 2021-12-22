package hleb.ledikom.controller.employee;

import hleb.ledikom.model.Course;
import hleb.ledikom.service.course.CourseService;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/courses")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class CourseController {

    private final CourseService courseService;
    private final EmployeeDataService employeeDataService;

    @Autowired
    public CourseController(CourseService courseService, EmployeeDataService employeeDataService) {
        this.courseService = courseService;
        this.employeeDataService = employeeDataService;
    }

    @GetMapping("/for-employee/{id}")
    public List<Course> getCoursesForEmployee(@PathVariable Long id) {
        return courseService.getCoursesForEmployee(id);
    }

    @PostMapping("/for-employee/{id}")
    public ResponseEntity<Course> addCourseToEmployee(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourseForEmployee(employeeDataService.findById(id), course));
    }
}
