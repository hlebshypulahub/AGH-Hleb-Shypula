package hleb.ledikom.controller;

import hleb.ledikom.PracaInzLedikomKursyApplication;
import hleb.ledikom.model.Course;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.CourseRepository;
import hleb.ledikom.repository.EmployeeRepository;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Integration tests
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PracaInzLedikomKursyApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class CourseControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Employee employee;

    private Course course;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Test");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");

        course = new Course();
        course.setName("Course 1");
        course.setHours(10);
        course.setStartDate(LocalDate.of(2020, 10, 10));
        course.setEndDate(LocalDate.of(2020, 11, 11));
    }

    @After
    public void after() {
        employee = null;
        course = null;
    }

    @Test
    public void Should_GetCoursesForEmployee() throws Exception {
        employee = employeeRepository.save(employee);

        employee.addCourse(course);

        courseRepository.save(course);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses/for-employee/" + employee.getId())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONArray array = new JSONArray(jsonResponse);

        assertEquals(1, array.length());
    }
}
