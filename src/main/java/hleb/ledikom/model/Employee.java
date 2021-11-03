package hleb.ledikom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
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

    {
        this.employeeCategory = EmployeeCategory.NONE;
    }

    private boolean active;

    public Employee(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
        this.setCategoryPossiblePromotionDate();
        this.setCategoryAssignmentAndDocsSubmitDeadlineDates();
    }

    private void setCategoryPossiblePromotionDate() {
        this.categoryPossiblePromotionDate = this.categoryAssignmentDate.plusYears(CATEGORY_POSSIBLE_PROMOTION_YEARS);
    }

    private void setCategoryAssignmentAndDocsSubmitDeadlineDates() {
        if (this.categoryAssignmentDate.isBefore(ACT_ENTRY_INTO_FORCE_DATE)) {
            this.categoryAssignmentDeadlineDate = ACT_ENTRY_INTO_FORCE_DATE.plusYears(CATEGORY_VERIFICATION_YEARS);
            this.docsSubmitDeadlineDate =this.categoryAssignmentDeadlineDate.minusMonths(DOCS_SUBMIT_MONTHS);
        } else {
            this.categoryAssignmentDeadlineDate = this.categoryAssignmentDate.plusYears(CATEGORY_VERIFICATION_YEARS);
            this.docsSubmitDeadlineDate = this.categoryAssignmentDeadlineDate.minusMonths(DOCS_SUBMIT_MONTHS);
        }
    }
}
