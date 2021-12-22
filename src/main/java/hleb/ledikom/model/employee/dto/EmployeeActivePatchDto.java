package hleb.ledikom.model.employee.dto;

import javax.validation.constraints.NotNull;

public class EmployeeActivePatchDto extends EmployeePatchDto {

    @NotNull(message = "active cannot be null")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
