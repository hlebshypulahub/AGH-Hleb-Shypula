package hleb.ledikom.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Education {
    HIGHER("Wyższe", 100, 160),
    SECONDARY("Średnie", 50, 80);

    private final String label;
    private final int requiredHoursNoneCategory;
    private final int requiredHours;

    Education(String label, int requiredHoursNoneCategory, int requiredHours) {
        this.label = label;
        this.requiredHoursNoneCategory = requiredHoursNoneCategory;
        this.requiredHours = requiredHours;
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

    public int getRequiredHoursNoneCategory() {
        return requiredHoursNoneCategory;
    }

    public int getRequiredHours() {
        return requiredHours;
    }
}
