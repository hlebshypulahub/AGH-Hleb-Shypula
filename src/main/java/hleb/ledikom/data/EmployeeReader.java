package hleb.ledikom.data;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(cron = "0 * * * * *")
    /// "0 0 6 * * *" every day 6 a.m.
    public void readAndFillData() throws FileNotFoundException, URISyntaxException {
//        URL resource = EmployeeReader.class.getResource("/employee_data.csv");
//
//        if (resource == null) {
//            throw new FileNotFoundException("Не найден файл выгрузки сотрудников");
//        }

        ClassPathResource resource = new ClassPathResource("C:\\Users\\hlebs\\Desktop\\Ledikom\\employee_data.csv");

        String filePath = "C:" + File.separator +
                "Users" + File.separator + "hlebs" + File.separator + "Desktop" + File.separator + "Ledikom" + File.separator + "employee_data.csv";
        Path path = Paths.get(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {

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

                    employeeRepository.save(employee);

                    logger.info("Persisting employee " + employee.getForeignId() + " " + employee.getFullName());
                }

                line = br.readLine();
            }

            logger.info("Reading employee data");
        } catch (IOException ioe) {
            logger.error("Cannot read a file");
            ioe.printStackTrace();
        }
    }
}
