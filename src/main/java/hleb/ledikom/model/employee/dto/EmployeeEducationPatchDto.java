package hleb.ledikom.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hleb.ledikom.model.employee.Education;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EmployeeEducationPatchDto extends EmployeePatchDto {

    @NotNull(message = "Education type cannot be empty")
    private Education education;
    @NotBlank(message = "Education name cannot be blank")
    private String eduName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(message = "Education graduation date cannot be empty")
    private LocalDate eduGraduationDate;

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getEduName() {
        return eduName;
    }

    public void setEduName(String eduName) {
        this.eduName = eduName;
    }

    public LocalDate getEduGraduationDate() {
        return eduGraduationDate;
    }

    public void setEduGraduationDate(LocalDate eduGraduationDate) {
        this.eduGraduationDate = eduGraduationDate;
    }
}
