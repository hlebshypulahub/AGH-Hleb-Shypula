package hleb.ledikom.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hleb.ledikom.model.course.Course;
import hleb.ledikom.model.enumeration.Category;
import hleb.ledikom.model.enumeration.Education;
import hleb.ledikom.model.enumeration.Exemption;
import hleb.ledikom.validator.CategoryDatesNotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@CategoryDatesNotNull
public class Employee {
    @Transient
    public static final LocalDate ACT_ENTRY_INTO_FORCE_DATE = LocalDate.of(2021, 7, 23);
    @Transient
    public static final int WORK_EXPIRIANCE_TO_CATEGORY_PROMOTION_YEARS = 3;
    @Transient
    public static final int CATEGORY_VERIFICATION_YEARS = 5;
    @Transient
    public static final int DOCS_SUBMIT_MONTHS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    Long id;

    /// Id from accounting app
    @NotNull(message = "foreignId cannot be null")
    Long foreignId;

    /// Main info
    @NotBlank(message = "fullName cannot be blank")
    private String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(message = "hiringDate cannot be null")
    private LocalDate hiringDate;
    /// ????????????????????????????????? validate
    @NotBlank(message = "jobFacility cannot be blank")
    private String jobFacility;
    @NotBlank(message = "position cannot be blank")
    private String position;

    /// Category
    private String qualification;
    private Category category;
    private String categoryNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate categoryAssignmentDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate categoryAssignmentDeadlineDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate docsSubmitDeadlineDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate categoryPossiblePromotionDate;

    /// Courses
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JsonIgnore
    private Set<Course> courses;
    private int courseHoursSum;

    /// Exemption
    private Exemption exemption;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate exemptionStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate exemptionEndDate;
    private boolean exemptioned;

    /// Active / inactive
    private boolean active;

    /// Education
    private Education education;
    private String eduName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate eduGraduationDate;

    public Employee() {

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", foreignId=" + foreignId +
                ", fullName='" + fullName + '\'' +
                ", hiringDate=" + hiringDate +
                ", jobFacility='" + jobFacility + '\'' +
                ", position='" + position + '\'' +
                ", qualification='" + qualification + '\'' +
                ", category=" + category +
                ", categoryNumber='" + categoryNumber + '\'' +
                ", categoryAssignmentDate=" + categoryAssignmentDate +
                ", categoryAssignmentDeadlineDate=" + categoryAssignmentDeadlineDate +
                ", docsSubmitDeadlineDate=" + docsSubmitDeadlineDate +
                ", categoryPossiblePromotionDate=" + categoryPossiblePromotionDate +
                ", courseHoursSum=" + courseHoursSum +
                ", courses quantity=" + ((courses == null) ? 0 : courses.size()) +
                ", exemption=" + exemption +
                ", exemptionStartDate=" + exemptionStartDate +
                ", exemptionEndDate=" + exemptionEndDate +
                ", exemptioned=" + exemptioned +
                ", active=" + active +
                ", education=" + education +
                ", eduName='" + eduName + '\'' +
                ", eduGraduationDate=" + eduGraduationDate +
                '}';
    }

    public void addCourse(Course course) {
        course.setEmployee(this);
        if (this.courses == null) {
            this.courses = new HashSet<>();
        }
        this.courses.add(course);
    }

    public Integer getCourseHoursLeft() {
        if (education != null && category != null) {
            int hours;

            if (category == Category.NONE) {
                hours = education.getRequiredHoursNoneCategory() - courseHoursSum;
            } else {
                hours = education.getRequiredHours() - courseHoursSum;
            }

            return Math.max(0, hours);
        }

        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForeignId() {
        return foreignId;
    }

    public void setForeignId(Long foreignId) {
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
