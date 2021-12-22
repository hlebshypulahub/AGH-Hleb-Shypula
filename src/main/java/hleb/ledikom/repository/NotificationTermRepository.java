package hleb.ledikom.repository;

import hleb.ledikom.model.notification.NotificationTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTermRepository extends JpaRepository<NotificationTerm, Long> {
}
