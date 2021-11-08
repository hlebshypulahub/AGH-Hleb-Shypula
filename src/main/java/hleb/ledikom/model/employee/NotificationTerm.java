package hleb.ledikom.model.employee;

import javax.persistence.*;

@Entity
public class NotificationTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private long id;

    private int days;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
