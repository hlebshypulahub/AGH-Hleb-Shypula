package hleb.ledikom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import hleb.ledikom.model.Employee;
import hleb.ledikom.model.EmployeeCategory;
import hleb.ledikom.model.NotificationTerm;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmployeeTests {

    private Employee employee;
    private NotificationTerm notificationTerm;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.FIRST);
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee.setActive(true);

        notificationTerm = new NotificationTerm();
    }

    @AfterEach
    public void after() {
        employee = null;
        notificationTerm = null;
    }

    @Test
    public void testEmployeeCategory() {
        assertEquals(employee.getEmployeeCategory().toString(), "Pierwsza");
    }

    @Test
    public void testEmployeeCategoryNotExistence() {
        Employee employee = new Employee(EmployeeCategory.NONE);
        assertEquals(employee.getEmployeeCategory().toString(), "Brak");
    }

    static class DateTest {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate date;
    }

    @Test
    public void testActEntryIntoForceDate() throws JSONException, JsonProcessingException {
        JSONObject jo = new JSONObject();
        jo.put("date", "23.07.2021");
        ObjectMapper objectMapper = new ObjectMapper();
        DateTest dateTest = objectMapper.readValue(jo.toString(), DateTest.class);
        assertEquals(Employee.ACT_ENTRY_INTO_FORCE_DATE, dateTest.date);
    }

    @Test
    public void testEmployeeActive() {
        assertTrue(employee.isActive());
    }

    @Test
    public void testEmployeeDefaultCategory() {
        employee = new Employee();
        assertEquals(employee.getEmployeeCategory().toString(), "Brak");
    }
}
