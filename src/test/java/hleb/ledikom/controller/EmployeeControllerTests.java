package hleb.ledikom.controller;

import hleb.ledikom.PracaInzLedikomKursyApplication;
import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.service.employee.EmployeeDataService;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Integration tests
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PracaInzLedikomKursyApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class EmployeeControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeDataService employeeDataService;

    private Employee employee;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Test");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");
    }

    @After
    public void after() {
        employee = null;
    }

    @Test
    public void When_getEmployees_Should_StatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void Should_GetEmployees() throws Exception {
        int size = employeeDataService.getAll().size();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONArray array = new JSONArray(jsonResponse);

        assertEquals(size, array.length());
    }

    @Test
    public void Should_getEmployeesForCoursePlan() throws Exception {
        int size = employeeDataService.getAllForCoursePlan().size();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/for-course-plan")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONArray array = new JSONArray(jsonResponse);

        assertEquals(size, array.length());
    }

    @Test
    public void Should_getEmployeeById() throws Exception {
        employee = employeeDataService.save(employee);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/" + employee.getId())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(jsonResponse);

        assertEquals(employee.getFullName(), jsonObject.get("fullName"));
    }

    @Test
    public void Should_PatchEmployeeEducation() throws Exception {
        employee = employeeDataService.save(employee);

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("education", "HIGHER");
        jsonMap.put("eduName", "AGH");
        jsonMap.put("eduGraduationDate", "05.05.2020");

        String requestBody = new JSONObject(jsonMap).toString();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/employees/" + employee.getId() + "/education")
                                                                    .contentType("application/merge-patch+json").content(requestBody)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(jsonResponse);

        assertEquals("AGH", jsonObject.get("eduName"));
    }
}
