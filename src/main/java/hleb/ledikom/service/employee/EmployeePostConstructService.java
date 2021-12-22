package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.service.course.CourseService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class EmployeePostConstructService {

    private final EmployeeDataService employeeDataService;
    private final CourseService courseService;
    private final Logger logger;

    @Autowired
    public EmployeePostConstructService(EmployeeDataService employeeDataService, CourseService courseService, Logger logger) {
        this.employeeDataService = employeeDataService;
        this.courseService = courseService;
        this.logger = logger;
    }

    /// "0 0 6 * * *" every day 6 a.m.
    /// "0 * * * * *" every minute
    @Scheduled(cron = "0 0 6 * * *")
    @PostConstruct
    public void calculateEmployeesCourseHours() {
        List<Employee> employees = employeeDataService.getAll();
        for (Employee employee : employees) {
            courseService.process(employee);
            employeeDataService.save(employee);
        }
    }

    //    @Scheduled(cron = "0 0 21 * * *")
    /// "0 0 6 * * *" every day 6 a.m.
    /// "0 * * * * *" every minute
//    @PostConstruct
//    public void readAndFillData() throws FileNotFoundException, URISyntaxException {
//        String filePath = "C:" + File.separator + "Users" + File.separator + "hlebs" + File.separator + "Desktop" + File.separator + "Ledikom" + File.separator + "employee_data.csv";
//        Path path = Paths.get(filePath);
//
//        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
//
//            logger.info("Reading employees data file");
//
//            String line = br.readLine();
//
//            while (line != null) {
//                String[] employeeAttributes = line.split(",");
//
//                long foreignId = Long.parseLong(employeeAttributes[0]);
//                if (employeeRepository.findByForeignId(foreignId) == null) {
//                    Employee employee = new Employee();
//                    employee.setForeignId(foreignId);
//                    employee.setFullName(employeeAttributes[1]);
//                    employee.setHiringDate(LocalDate.parse(employeeAttributes[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                    employee.setJobFacility(employeeAttributes[3]);
//                    employee.setPosition(employeeAttributes[4]);
//                    employee.setExemptioned(false);
//                    employee.setActive(true);
//
//                    employeeRepository.save(employee);
//
//                    logger.info("Persisting employee " + employee.getForeignId() + " " + employee.getFullName());
//                }
//
//                line = br.readLine();
//            }
//        } catch (IOException ioe) {
//            logger.error("Cannot read a file with employees data");
//            ioe.printStackTrace();
//        }
//    }
}
