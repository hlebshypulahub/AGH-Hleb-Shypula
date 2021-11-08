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

    private boolean active;

    public Employee() {

    }

    public Employee(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
//        this.setCategoryPossiblePromotionDate();
//        this.setCategoryAssignmentAndDocsSubmitDeadlineDates();
    }

//    private void setCategoryPossiblePromotionDate() {
//        this.categoryPossiblePromotionDate = this.categoryAssignmentDate.plusYears(CATEGORY_POSSIBLE_PROMOTION_YEARS);
//    }

//    private void setCategoryAssignmentAndDocsSubmitDeadlineDates() {
//        if (this.categoryAssignmentDate.isBefore(ACT_ENTRY_INTO_FORCE_DATE)) {
//            this.categoryAssignmentDeadlineDate = ACT_ENTRY_INTO_FORCE_DATE.plusYears(CATEGORY_VERIFICATION_YEARS);
//            this.docsSubmitDeadlineDate =this.categoryAssignmentDeadlineDate.minusMonths(DOCS_SUBMIT_MONTHS);
//        } else {
//            this.categoryAssignmentDeadlineDate = this.categoryAssignmentDate.plusYears(CATEGORY_VERIFICATION_YEARS);
//            this.docsSubmitDeadlineDate = this.categoryAssignmentDeadlineDate.minusMonths(DOCS_SUBMIT_MONTHS);
//        }
//    }

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
}
