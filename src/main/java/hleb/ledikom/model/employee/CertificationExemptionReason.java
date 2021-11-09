package hleb.ledikom.model.employee;

public enum CertificationExemptionReason {

    LESS_THAN_YEAR_WORK("Praca na odpowiednim stanowisku krócej niż rok", 0, 12),
    PREGNANCY("Ciąża"),
    CONSCRIPTION("Zatrudnienie na poprzednie stanowisko pracy po odbyciu służby wojskowej przez pobór", 12),
    TREATMENT("Długotrwałe (ponad 4 miesiące) leczenie", 6, 4),
    BUSINESS_TRIP("Wyjazd służbowy za granicę (na więcej, niż 4 miesiące)", 6, 4),
    STUDIES("Studia podyplomowe"),
    MATERNITY_LEAVE("Urlop macierzyński", 12);

    private final String label;
    private final int monthsDuration;
    private final int monthsOfExemption;

    CertificationExemptionReason(String label, int monthsOfExemption, int monthsDuration) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
        this.monthsDuration = monthsDuration;
    }

    CertificationExemptionReason(String label, int monthsOfExemption) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
        this.monthsDuration = 0;
    }

    CertificationExemptionReason(String label) {
        this.label = label;
        this.monthsOfExemption = 0;
        this.monthsDuration = 0;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getMonthsOfExemption() {
        return monthsOfExemption;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }
}
