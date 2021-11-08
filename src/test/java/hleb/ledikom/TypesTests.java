package hleb.ledikom;

import hleb.ledikom.model.employee.CertificationExemptionReason;
import hleb.ledikom.model.employee.EmployeeCategory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TypesTests {

    @Test
    public void testEmployeeCategoryToString() {
        assertEquals(EmployeeCategory.SECOND.toString(), "Druga");
    }

    @Test
    public void testCertificationExemptionReasonToString() {
        assertEquals(CertificationExemptionReason.LESS_THAN_YEAR_WORK.toString(), "Praca na odpowiednim stanowisku krócej niż rok");
    }

    @Test
    public void testCertificationExemptionReasonMonths() {
        assertEquals(CertificationExemptionReason.CONSCRIPTION.getMonthsOfExemption(), 12);
    }

    @Test
    public void testCertificationExemptionReasonMonthsDefault() {
        assertEquals(CertificationExemptionReason.STUDIES.getMonthsOfExemption(), 0);
    }
}
