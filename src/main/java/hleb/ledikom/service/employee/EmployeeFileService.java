package hleb.ledikom.service.employee;

import hleb.ledikom.model.employee.Employee;
import hleb.ledikom.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeFileService {

    private final EmployeeRepository employeeRepository;
    private final Logger logger;

    @Autowired
    public EmployeeFileService(EmployeeRepository employeeRepository, Logger logger) {
        this.employeeRepository = employeeRepository;
        this.logger = logger;
    }

    public void readAndFillData(String filePath) throws FileNotFoundException, URISyntaxException {
        Path path = Paths.get(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {

            logger.info("Reading employees data file");

            String line = br.readLine();

            while (line != null) {
                String[] employeeAttributes = line.split(",");

                long foreignId = Long.parseLong(employeeAttributes[0]);
                if (employeeRepository.findByForeignId(foreignId).isEmpty()) {
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
