package hleb.ledikom.model;

public enum Category {

    HIGHEST("Wyższa"),
    FIRST("Pierwsza"),
    SECOND("Druga"),
    NONE("Brak");

    public final String label;

    Category(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
