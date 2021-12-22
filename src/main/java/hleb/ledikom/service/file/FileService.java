package hleb.ledikom.service.file;

import hleb.ledikom.exception.FileStorageException;
import hleb.ledikom.service.employee.EmployeeFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${spring.servlet.multipart.location}")
    public String uploadDir;

    @Autowired
    EmployeeFileService employeeFileService;

    public void uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), UUID.randomUUID().toString());
                String tmpdir = Files.createDirectories(path).toFile().getAbsolutePath();

                String orgName = file.getOriginalFilename();
                String filePath = tmpdir + File.separator + orgName;
                File dest = new File(filePath);
                file.transferTo(dest);

                employeeFileService.readAndFillData(filePath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                        + ". Please try again!");
            }
        }
    }
}
