package hleb.ledikom.data;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EmployeeReader {

    @Autowired
    EmployeeRepository employeeRepository;

    Logger logger = LoggerFactory.getLogger(EmployeeReader.class);

    //    @Scheduled(cron = "0 0 21 * * *")
    /// "0 0 6 * * *" every day 6 a.m.
    /// "0 * * * * *" every minute
    @PostConstruct
    public void readAndFillData() throws FileNotFoundException, URISyntaxException {
        String filePath = "C:" + File.separator + "Users" + File.separator + "hlebs" + File.separator + "Desktop" + File.separator + "Ledikom" + File.separator + "employee_data.csv";
        Path path = Paths.get(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {

            logger.info("Reading employees data file");

            String line = br.readLine();

            while (line != null) {
                String[] employeeAttributes = line.split(",");

                long foreignId = Long.parseLong(employeeAttributes[0]);
                if (employeeRepository.findByForeignId(foreignId) == null) {
                    Employee employee = new Employee();
                    employee.setForeignId(foreignId);
                    employee.setFullName(employeeAttributes[1]);
                    employee.setHiringDate(LocalDate.parse(employeeAttributes[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    employee.setJobFacility(employeeAttributes[3]);
                    employee.setPosition(employeeAttributes[4]);
                    employee.setExemptioned(false);
                    employee.setActive(true);

                    employeeRepository.save(employee);

                    logger.info("Persisting employee " + employee.getForeignId() + " " + employee.getFullName());
                }

                line = br.readLine();
            }
        } catch (IOException ioe) {
            logger.error("Cannot read a file with employees data");
            ioe.printStackTrace();
        }
    }
}
