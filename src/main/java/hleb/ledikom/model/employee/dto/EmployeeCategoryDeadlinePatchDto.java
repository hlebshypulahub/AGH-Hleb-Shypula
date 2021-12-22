package hleb.ledikom.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EmployeeCategoryDeadlinePatchDto extends EmployeePatchDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(message = "categoryAssignmentDeadlineDate cannot be null")
    private LocalDate categoryAssignmentDeadlineDate;

    public LocalDate getCategoryAssignmentDeadlineDate() {
        return categoryAssignmentDeadlineDate;
    }

    public void setCategoryAssignmentDeadlineDate(LocalDate categoryAssignmentDeadlineDate) {
        this.categoryAssignmentDeadlineDate = categoryAssignmentDeadlineDate;
    }
}
