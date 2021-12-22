package hleb.ledikom.model.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NotificationTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;

    private int monthsSecondaryEducation;
    private int monthsHigherEducation;

    public NotificationTerm() {
    }

    public NotificationTerm(int monthsSecondaryEducation, int monthsHigherEducation) {
        this.monthsSecondaryEducation = monthsSecondaryEducation;
        this.monthsHigherEducation = monthsHigherEducation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonthsSecondaryEducation() {
        return monthsSecondaryEducation;
    }

    public void setMonthsSecondaryEducation(int monthsSecondaryEducation) {
        this.monthsSecondaryEducation = monthsSecondaryEducation;
    }

    public int getMonthsHigherEducation() {
        return monthsHigherEducation;
    }

    public void setMonthsHigherEducation(int monthsHigherEducation) {
        this.monthsHigherEducation = monthsHigherEducation;
    }
}
