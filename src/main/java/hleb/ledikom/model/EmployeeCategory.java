package hleb.ledikom.model;

public enum EmployeeCategory {

    HIGHEST("Wyższa"),
    FIRST("Pierwsza"),
    SECOND("Druga"),
    NONE("Brak");

    private final String label;

    EmployeeCategory(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
