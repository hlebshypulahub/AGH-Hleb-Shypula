package hleb.ledikom.model;

public enum CertificationExemptionReason {

    LESS_THAN_YEAR_WORK("Praca na odpowiednim stanowisku krócej niż rok"),
    PREGNANCY("Ciąża"),
    CONSCRIPTION("Zatrudnienie na poprzednie stanowisko pracy po odbyciu służby wojskowej przez pobór", 12),
    TREATMENT("Długotrwałe (ponad 4 miesiące) leczenie", 6),
    BUSINESS_TRIP("Wyjazd służbowy za granicę (na więcej, niż 4 miesiące)", 6),
    STUDIES("studia podyplomowe"),
    MATERNITY_LEAVE("Urlop macierzyński");

    private final String label;
    private final int monthsOfExemption;

    CertificationExemptionReason(String label, int monthsOfExemption) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
    }

    CertificationExemptionReason(String label) {
        this.label = label;
        this.monthsOfExemption = 0;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getMonthsOfExemption() {
        return monthsOfExemption;
    }
}
