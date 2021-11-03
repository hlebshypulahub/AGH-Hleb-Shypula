package hleb.ledikom;

import hleb.ledikom.model.EmployeeCategory;
import hleb.ledikom.model.Employee;
import hleb.ledikom.model.NotificationTerm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DAOIntegrationTests {

    @Autowired
    private TestEntityManager entityManager;

    private Employee employee;
    private NotificationTerm notificationTerm;

    @Before
    public void before() {
        employee = new Employee();
        employee.setEmployeeCategory(EmployeeCategory.HIGHEST);
        employee.setCategoryAssignmentDate(LocalDate.of(2020,5,5));
        employee.setCategoryAssignmentDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(5));
        employee.setDocsSubmitDeadlineDate(Employee.ACT_ENTRY_INTO_FORCE_DATE.plusYears(4).plusMonths(9));

        notificationTerm = new NotificationTerm();
    }

    @After
    public void after() {
        employee = null;
        notificationTerm = null;
    }

    @Test
    public void testNotificationTermDAO() {
        notificationTerm.setDays(365);
        entityManager.persist(notificationTerm);
        entityManager.flush();
        NotificationTerm foundNotificationTerm = entityManager.find(NotificationTerm.class, notificationTerm.getId());
        assertEquals(Math.abs(DAYS.between(employee.getCategoryAssignmentDeadlineDate(), LocalDate.of(2025, 7, 23))), foundNotificationTerm.getDays());

        notificationTerm.setDays(200);
        entityManager.persist(notificationTerm);
        entityManager.flush();
        foundNotificationTerm = entityManager.find(NotificationTerm.class, notificationTerm.getId());
        assertTrue(Math.abs(DAYS.between(employee.getDocsSubmitDeadlineDate(), LocalDate.of(2025, 7, 23))) > foundNotificationTerm.getDays());
    }

    @Test
    public void testNotificationTermFakeDAO() {
        NotificationTerm notificationTermFake = new NotificationTerm();

        entityManager.persist(notificationTerm);
        entityManager.flush();
        entityManager.persist(notificationTermFake);
        entityManager.flush();

        NotificationTerm foundNotificationTerm = entityManager.find(NotificationTerm.class, notificationTerm.getId());

        assertNotEquals(notificationTermFake, foundNotificationTerm);
    }

}
