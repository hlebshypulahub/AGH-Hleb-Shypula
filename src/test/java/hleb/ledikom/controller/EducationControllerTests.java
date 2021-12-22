package hleb.ledikom.controller;

import hleb.ledikom.controller.myenum.EducationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/// Integration tests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EducationControllerTests {

    @Autowired
    private EducationController educationController;

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void Should_GetEducationValues() {
        List<Map<String, String>> list = educationController.getEducationValues();

        assertEquals(2, list.size());
    }

    @Test
    @WithMockUser(username="admin",roles={})
    public void Should_GetEducationValues_When_NoRoles() {
        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            List<Map<String, String>> list = educationController.getEducationValues();
        });
    }
}
