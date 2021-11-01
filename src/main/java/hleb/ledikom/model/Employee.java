package hleb.ledikom.model;

import java.time.LocalDate;

public class Employee {
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021,7,23);

    private Category category;
    private LocalDate categoryAssignmentDate;
    private LocalDate categoryAssignmentDeadlineDate;
    private LocalDate docsSubmitDeadlineDate;

    public Employee() {

    }

    public Employee(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategoryAssignmentDeadlineDate(LocalDate categoryAssignmentDeadlineDate) {
        this.categoryAssignmentDeadlineDate = categoryAssignmentDeadlineDate;
    }

    public void setDocsSubmitDeadlineDate(LocalDate docsSubmitDeadlineDate) {
        this.docsSubmitDeadlineDate = docsSubmitDeadlineDate;
    }

    public LocalDate getCategoryAssignmentDeadlineDate() {
        return categoryAssignmentDeadlineDate;
    }

    public LocalDate getDocsSubmitDeadlineDate() {
        return docsSubmitDeadlineDate;
    }

    public LocalDate getCategoryAssignmentDate() {
        return categoryAssignmentDate;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
    }
}
