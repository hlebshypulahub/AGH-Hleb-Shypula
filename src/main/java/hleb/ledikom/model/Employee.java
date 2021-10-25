package hleb.ledikom.model;

import java.time.LocalDate;

public class Employee {
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021,7,23);

    private String category;

    public Employee() {

    }

    public Employee(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
