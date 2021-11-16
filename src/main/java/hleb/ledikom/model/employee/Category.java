package hleb.ledikom.model.employee;

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
}
