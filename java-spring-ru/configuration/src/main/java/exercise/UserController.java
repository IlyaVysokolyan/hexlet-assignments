package exercise;

import exercise.component.UserProperties;
import exercise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserProperties userProperties;

    @GetMapping("/admins")
    public List<String> allUsers() {
        return Data.getUsers().stream()
                .filter(user -> userProperties.getAdmins().contains(user.getEmail()))
                .map(User::getName)
                .sorted()
                .toList();
    }
}
