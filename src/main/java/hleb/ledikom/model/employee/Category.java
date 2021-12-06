package hleb.ledikom.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    HIGHEST("Wyższa"),
    FIRST("Pierwsza"),
    SECOND("Druga"),
    NONE("Brak");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return this.name();
    }
}
