package hleb.ledikom.model.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Employee {
    @Transient
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021, 7, 23);
    @Transient
    public static final int CATEGORY_POSSIBLE_PROMOTION_YEARS = 3;
    @Transient
    public static final int CATEGORY_VERIFICATION_YEARS = 5;
    @Transient
    public static final int DOCS_SUBMIT_MONTHS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    long id;

    /// Id from accounting app
    long foreignId;

    /// Main info
    private String fullName;
    private LocalDate hiringDate;
    private String jobFacility;
    private String position;

    /// Category
    private String qualification;
    private Category category;
    private String categoryNumber;
    private LocalDate categoryAssignmentDate;
    private LocalDate categoryAssignmentDeadlineDate;
    private LocalDate docsSubmitDeadlineDate;
    private LocalDate categoryPossiblePromotionDate;

    /// Course hours sum
    private int courseHoursSum;

    /// Courses
    @OneToMany(mappedBy = "employee", cascade=CascadeType.ALL)
    @JsonManagedReference
    private Set<Course> courses;

    /// Exemption
    private CertificationExemptionReason certificationExemptionReason;
    private LocalDate exemptionStartDate;
    private LocalDate exemptionEndDate;
    private boolean exemptioned;

    /// Active / inactive
    private boolean active;

    /// Education
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
        this.id = other.id;
        this.foreignId = other.foreignId;
        this.fullName = other.fullName;
        this.hiringDate = other.hiringDate;
        this.jobFacility = other.jobFacility;
        this.position = other.position;
        this.qualification = other.qualification;
        this.category = other.category;
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
                "employeeCategory=" + category +
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

    public Employee(Category category) {
        this.category = category;
    }

    public void addCourse(Course course) {
        course.setEmployee(this);
        this.courses.add(course);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getForeignId() {
        return foreignId;
    }

    public void setForeignId(long foreignId) {
        this.foreignId = foreignId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
