package hleb.ledikom;

import hleb.ledikom.model.EmployeeCategory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TypesTests {

    @Test
    public void testCategoryToString() {
        assertEquals(EmployeeCategory.SECOND.toString(), "Druga");
    }

}
