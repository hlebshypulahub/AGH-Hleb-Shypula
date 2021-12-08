package hleb.ledikom.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    NONE("Brak"),
    SECOND("Druga"),
    FIRST("Pierwsza"),
    HIGHEST("Wyższa");

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
