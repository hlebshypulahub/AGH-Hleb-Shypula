package hleb.ledikom.model.employee;

import javax.persistence.Transient;
import java.time.LocalDate;

public class Employee {
    @Transient
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021, 7, 23);
    @Transient
    public static final int CATEGORY_POSSIBLE_PROMOTION_YEARS = 3;
    @Transient
    public static final int CATEGORY_VERIFICATION_YEARS = 5;
    @Transient
    public static final int DOCS_SUBMIT_MONTHS = 3;

    private EmployeeCategory employeeCategory;
    private LocalDate categoryAssignmentDate;
    private LocalDate categoryAssignmentDeadlineDate;
    private LocalDate docsSubmitDeadlineDate;
    private LocalDate categoryPossiblePromotionDate;

    private CertificationExemptionReason certificationExemptionReason;
    private LocalDate exemptionStartDate;
    private LocalDate exemptionEndDate;
    private boolean exemptioned;

    private boolean active;

    public Employee() {

    }

    public Employee(Employee other) {
        this.employeeCategory = other.employeeCategory;
        this.categoryAssignmentDate = other.categoryAssignmentDate;
        this.categoryAssignmentDeadlineDate = other.categoryAssignmentDeadlineDate;
        this.docsSubmitDeadlineDate = other.docsSubmitDeadlineDate;
        this.categoryPossiblePromotionDate = other.categoryPossiblePromotionDate;
        this.certificationExemptionReason = other.certificationExemptionReason;
        this.exemptionStartDate = other.exemptionStartDate;
        this.exemptionEndDate = other.exemptionEndDate;
        this.exemptioned = other.exemptioned;
        this.active = other.active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeCategory=" + employeeCategory +
                ", categoryAssignmentDate=" + categoryAssignmentDate +
                ", categoryAssignmentDeadlineDate=" + categoryAssignmentDeadlineDate +
                ", docsSubmitDeadlineDate=" + docsSubmitDeadlineDate +
                ", categoryPossiblePromotionDate=" + categoryPossiblePromotionDate +
                ", certificationExemptionReason=" + certificationExemptionReason +
                ", exemptionStartDate=" + exemptionStartDate +
                ", exemptionEndDate=" + exemptionEndDate +
                ", exemptioned=" + exemptioned +
                ", active=" + active +
                '}';
    }

    public Employee(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public LocalDate getCategoryAssignmentDate() {
        return categoryAssignmentDate;
    }

    public LocalDate getCategoryAssignmentDeadlineDate() {
        return categoryAssignmentDeadlineDate;
    }

    public void setCategoryAssignmentDeadlineDate(LocalDate categoryAssignmentDeadlineDate) {
        this.categoryAssignmentDeadlineDate = categoryAssignmentDeadlineDate;
    }

    public LocalDate getDocsSubmitDeadlineDate() {
        return docsSubmitDeadlineDate;
    }

    public void setDocsSubmitDeadlineDate(LocalDate docsSubmitDeadlineDate) {
        this.docsSubmitDeadlineDate = docsSubmitDeadlineDate;
    }

    public LocalDate getCategoryPossiblePromotionDate() {
        return categoryPossiblePromotionDate;
    }

    public void setCategoryPossiblePromotionDate(LocalDate categoryPossiblePromotionDate) {
        this.categoryPossiblePromotionDate = categoryPossiblePromotionDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CertificationExemptionReason getCertificationExemptionReason() {
        return certificationExemptionReason;
    }

    public void setCertificationExemptionReason(CertificationExemptionReason certificationExemptionReason) {
        this.certificationExemptionReason = certificationExemptionReason;
    }

    public LocalDate getExemptionStartDate() {
        return exemptionStartDate;
    }

    public void setExemptionStartDate(LocalDate exemptionStartDate) {
        this.exemptionStartDate = exemptionStartDate;
    }

    public LocalDate getExemptionEndDate() {
        return exemptionEndDate;
    }

    public void setExemptionEndDate(LocalDate exemptionEndDate) {
        this.exemptionEndDate = exemptionEndDate;
    }

    public boolean isExemptioned() {
        return exemptioned;
    }

    public void setExemptioned(boolean exemptioned) {
        this.exemptioned = exemptioned;
    }
}
