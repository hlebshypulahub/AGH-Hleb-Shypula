package hleb.ledikom.model.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Exemption {

    /// Может сделать, что когда ставишь старт дату, то автоматом ставится дата конца как { старт дата + 1 год }?
    LESS_THAN_YEAR_WORK("Praca na odpowiednim stanowisku krócej niż rok", 0, 12),
    PREGNANCY("Ciąża", 12),
    CONSCRIPTION("Zatrudnienie na poprzednie stanowisko pracy po odbyciu służby wojskowej przez pobór", 12),
    TREATMENT("Długotrwałe (ponad 4 miesiące) leczenie", 6, 4),
    BUSINESS_TRIP("Wyjazd służbowy za granicę (na więcej, niż 4 miesiące)", 6, 4),
    STUDIES("Studia podyplomowe", 6),
    MATERNITY_LEAVE("Urlop macierzyński", 12);

    private final String label;
    private final int monthsDuration;
    private final int monthsOfExemption;

    Exemption(String label, int monthsOfExemption, int monthsDuration) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
        this.monthsDuration = monthsDuration;
    }

    Exemption(String label, int monthsOfExemption) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
        this.monthsDuration = 0;
    }

    Exemption(String label) {
        this.label = label;
        this.monthsOfExemption = 0;
        this.monthsDuration = 0;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public int getMonthsOfExemption() {
        return monthsOfExemption;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }

    public String getName() {
        return this.name();
    }
}
