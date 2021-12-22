package hleb.ledikom.course;

/// Integration tests
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest
//@ActiveProfiles("test")
public class CourseRepositoryIntegrationTests {

//    @Autowired
//    CourseRepository courseRepository;
//
//    @Autowired
//    EmployeeRepository employeeRepository;

    //// Cannot be tested due to employee validation, scope of test is missing then.
    //// This functionality is tested in EmployeeRepositoryTests class.
    //// findAllByEmployeeId will be tested there as well.
//    @Test
//    public void Should_SaveCourse() {
//        Course course = new Course();
//        course.setName("Kurs 1");
//        course.setStartDate(LocalDate.of(2020, 10, 10));
//        course.setEndDate(LocalDate.of(2020, 11, 11));
//        course.setHours(10);
//
//        Employee employee = new Employee();
//        employeeRepository.save(employee);
//
//        course.setEmployee(employee);
//
//        courseRepository.save(course);
//        courseRepository.flush();
//
//        Long id = course.getId();
//
//        Course foundCourse = courseRepository.getById(id);
//
//        assertEquals(course, foundCourse);
//    }
}
