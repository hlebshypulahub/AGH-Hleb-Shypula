package hleb.ledikom.model.employee;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Set;

public class Employee {
    @Transient
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021, 7, 23);
    @Transient
    public static final int CATEGORY_POSSIBLE_PROMOTION_YEARS = 3;
    @Transient
    public static final int CATEGORY_VERIFICATION_YEARS = 5;
    @Transient
    public static final int DOCS_SUBMIT_MONTHS = 3;

    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate hiringDate;
    private String jobFacility;
    private String position;

    private String qualification;

    private EmployeeCategory employeeCategory;
    private String categoryNumber;
    private LocalDate categoryAssignmentDate;
    private LocalDate categoryAssignmentDeadlineDate;
    private LocalDate docsSubmitDeadlineDate;
    private LocalDate categoryPossiblePromotionDate;

    private int courseHoursSum;
    private Set<Course> courses;

    private CertificationExemptionReason certificationExemptionReason;
    private LocalDate exemptionStartDate;
    private LocalDate exemptionEndDate;
    private boolean exemptioned;

    private boolean active;

    private Education education;
    private String eduName;
    private LocalDate eduGraduationDate;

    {
        this.active = true;
        this.exemptioned = false;
    }

    public Employee() {

    }

    public Employee(Employee other) {
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.middleName = other.middleName;
        this.hiringDate = other.hiringDate;
        this.jobFacility = other.jobFacility;
        this.position = other.position;
        this.qualification = other.qualification;
        this.employeeCategory = other.employeeCategory;
        this.categoryNumber = other.categoryNumber;
        this.categoryAssignmentDate = other.categoryAssignmentDate;
        this.categoryAssignmentDeadlineDate = other.categoryAssignmentDeadlineDate;
        this.docsSubmitDeadlineDate = other.docsSubmitDeadlineDate;
        this.categoryPossiblePromotionDate = other.categoryPossiblePromotionDate;
        this.courseHoursSum = other.courseHoursSum;
        this.courses = other.courses;
        this.certificationExemptionReason = other.certificationExemptionReason;
        this.exemptionStartDate = other.exemptionStartDate;
        this.exemptionEndDate = other.exemptionEndDate;
        this.exemptioned = other.exemptioned;
        this.active = other.active;
        this.education = other.education;
        this.eduName = other.eduName;
        this.eduGraduationDate = other.eduGraduationDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeCategory=" + employeeCategory +
                ", categoryAssignmentDate=" + categoryAssignmentDate +
                ", categoryAssignmentDeadlineDate=" + categoryAssignmentDeadlineDate +
                ", docsSubmitDeadlineDate=" + docsSubmitDeadlineDate +
                ", categoryPossiblePromotionDate=" + categoryPossiblePromotionDate +
                ", certificationExemptionReason=" + certificationExemptionReason +
                ", exemptionStartDate=" + exemptionStartDate +
                ", exemptionEndDate=" + exemptionEndDate +
                ", exemptioned=" + exemptioned +
                ", active=" + active +
                '}';
    }

    public Employee(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
    }

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

    public CertificationExemptionReason getCertificationExemptionReason() {
        return certificationExemptionReason;
    }

    public void setCertificationExemptionReason(CertificationExemptionReason certificationExemptionReason) {
        this.certificationExemptionReason = certificationExemptionReason;
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

    public boolean isExemptioned() {
        return exemptioned;
    }

    public void setExemptioned(boolean exemptioned) {
        this.exemptioned = exemptioned;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getJobFacility() {
        return jobFacility;
    }

    public void setJobFacility(String jobFacility) {
        this.jobFacility = jobFacility;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

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

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public int getCourseHoursSum() {
        return courseHoursSum;
    }

    public void setCourseHoursSum(int courseHoursSum) {
        this.courseHoursSum = courseHoursSum;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
