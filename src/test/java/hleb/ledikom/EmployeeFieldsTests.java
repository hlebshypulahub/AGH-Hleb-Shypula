package hleb.ledikom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import hleb.ledikom.model.Employee;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeFieldsTests {

    @Test
    public void testEmployeeCategory() {
        Employee employee = new Employee("Kategoria X");
        assertEquals(employee.getCategory(), "Kategoria X");
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

}
