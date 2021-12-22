package hleb.ledikom.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hleb.ledikom.model.enumeration.Exemption;
import hleb.ledikom.validator.ExemptionNotNull;

import java.time.LocalDate;

@ExemptionNotNull
public class EmployeeExemptionPatchDto extends EmployeePatchDto {

    private Exemption exemption;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate exemptionStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate exemptionEndDate;

    public Exemption getExemption() {
        return exemption;
    }

    public void setExemption(Exemption exemption) {
        this.exemption = exemption;
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
}
