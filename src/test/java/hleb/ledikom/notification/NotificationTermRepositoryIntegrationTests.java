package hleb.ledikom.notification;

import hleb.ledikom.model.notification.NotificationTerm;
import hleb.ledikom.repository.NotificationTermRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Integration tests
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
public class NotificationTermRepositoryIntegrationTests {

    @Autowired
    NotificationTermRepository notificationTermRepository;

    @Test
    public void Should_SaveNotificationTerm() {
        NotificationTerm notificationTerm = new NotificationTerm();
        notificationTerm.setMonthsHigherEducation(24);
        notificationTerm.setMonthsSecondaryEducation(12);

        notificationTermRepository.saveAndFlush(notificationTerm);

        Long id = notificationTerm.getId();

        NotificationTerm found = notificationTermRepository.findById(id).get();

        assertEquals(12, found.getMonthsSecondaryEducation());
        assertEquals(notificationTerm, found);
    }

}
