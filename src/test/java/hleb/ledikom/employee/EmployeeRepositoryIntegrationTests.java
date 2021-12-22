package hleb.ledikom.employee;

import hleb.ledikom.model.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.CourseRepository;
import hleb.ledikom.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Integration tests
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryIntegrationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    CourseRepository courseRepository;

    private Employee employee;

    @Before
    public void before() {
        employee = new Employee();
        employee.setForeignId(134535L);
        employee.setFullName("Adam Babacki");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");
    }

    @After
    public void after() {
        employee = null;
    }

    @Test
    public void Should_SaveEmployee() {
        employeeRepository.save(employee);
        employeeRepository.flush();

        Long id = employee.getId();

        Employee foundEmployee = employeeRepository.findById(id).get();

        assertEquals(employee, foundEmployee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void Should_ThrowResourceNotFoundException_When_EmployeeNotExist() {
        Employee foundEmployee = employeeRepository.findById(0L).orElseThrow(() -> new ResourceNotFoundException("Employee not found, id: " + 0L));
    }

    //// Must be run on empty database, otherwise will find all the employees, even not faked
    @Test
    public void Should_FindAll() {
        int size = employeeRepository.findAll().size();

        Employee employee1 = new Employee();
        BeanUtils.copyProperties(employee, employee1);

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employees = employeeRepository.findAll();

        assertEquals(size + 2, employees.size());
    }

    @Test
    public void Should_FindByForeignId() {
        employeeRepository.save(employee);

        Long foreignId = employee.getForeignId();

        Employee foundEmployee = employeeRepository.findByForeignId(foreignId).orElseThrow(() -> new ResourceNotFoundException("Employee not found, id: " + foreignId));

        assertEquals(employee, foundEmployee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void Should_ThrowResourceNotFoundException_When_FindByForeignId_And_EmployeeNotExist() {
        Employee foundEmployee = employeeRepository.findByForeignId(0L).orElseThrow(ResourceNotFoundException::new);
    }

    @Test
    public void Should_SaveCourse() {
        Course course = new Course();
        course.setName("Kurs 1");
        course.setStartDate(LocalDate.of(2020, 10, 10));
        course.setEndDate(LocalDate.of(2020, 11, 11));
        course.setHours(10);

        employeeRepository.save(employee);

        course.setEmployee(employee);

        courseRepository.save(course);
        courseRepository.flush();

        Long id = course.getId();

        Course foundCourse = courseRepository.getById(id);

        assertEquals(course, foundCourse);
    }

    @Test
    public void Should_FindAllCoursesByEmployeeId() {
        Course course1 = new Course();
        course1.setName("Kurs 1");
        course1.setStartDate(LocalDate.of(2020, 10, 10));
        course1.setEndDate(LocalDate.of(2020, 11, 11));
        course1.setHours(10);

        Course course2 = new Course();
        course2.setName("Kurs 2");
        course2.setStartDate(LocalDate.of(2020, 10, 10));
        course2.setEndDate(LocalDate.of(2020, 11, 11));
        course2.setHours(20);

        employeeRepository.save(employee);
        employeeRepository.flush();

        course1.setEmployee(employee);
        course2.setEmployee(employee);

        courseRepository.save(course1);
        courseRepository.save(course2);

        Long employeeId = employee.getId();

        List<Course> courses = courseRepository.findAddByEmployeeId(employeeId);

        assertEquals(30, courses.stream().mapToInt(Course::getHours).sum());
    }
}
