package hleb.ledikom.controller.myenum;

import hleb.ledikom.model.employee.Category;
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
@RequestMapping("/api/v1/category")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class CategoryController {

    @GetMapping("")
    public List<Map<String, String>> getCategoryValues() {
        return Stream.of(Category.values()).parallel().map(temp -> {
            Map<String, String> obj = new HashMap<>();
            obj.put("name", temp.name());
            obj.put("label", temp.toString());
            return obj;
        }).collect(Collectors.toList());
    }

}
