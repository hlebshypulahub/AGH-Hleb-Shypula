package hleb.ledikom.controller.myenum;

import hleb.ledikom.model.employee.Education;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/education")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class EducationController {

    @GetMapping("")
    public List<Map<String, String>> getEducationValues() {
        return Stream.of(Education.values()).parallel().map(temp -> {
            Map<String, String> obj = new HashMap<>();
            obj.put("name", temp.name());
            obj.put("label", temp.toString());
            obj.put("requiredHoursNoneCategory", String.valueOf(temp.getRequiredHoursNoneCategory()));
            obj.put("requiredHours", String.valueOf(temp.getRequiredHours()));
            return obj;
        }).collect(Collectors.toList());
    }

}
