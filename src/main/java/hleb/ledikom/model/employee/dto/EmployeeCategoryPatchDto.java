package hleb.ledikom.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hleb.ledikom.model.employee.Category;
import hleb.ledikom.validator.CategoryAssignmentDateNotNull;
import hleb.ledikom.validator.CategoryNumberNotBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@CategoryAssignmentDateNotNull
@CategoryNumberNotBlank
public class EmployeeCategoryPatchDto extends EmployeePatchDto {

    @NotBlank(message = "qualification cannot be blank")
    private String qualification;
    @NotNull(message = "category cannot be null")
    private Category category;
    private String categoryNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate categoryAssignmentDate;

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public LocalDate getCategoryAssignmentDate() {
        return categoryAssignmentDate;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
    }
}
