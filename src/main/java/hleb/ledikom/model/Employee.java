package hleb.ledikom.model;

import java.time.LocalDate;

public class Employee {
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021,7,23);

    private EmployeeCategory employeeCategory;
    private LocalDate categoryAssignmentDate;
    private LocalDate categoryAssignmentDeadlineDate;
    private LocalDate docsSubmitDeadlineDate;

    private boolean active;

    public Employee() {

    }

    public Employee(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public EmployeeCategory getCategory() {
        return employeeCategory;
    }

    public void setCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
