package pl.power.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.power.services.TaskService;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserInfoController {
    private final TaskService taskService;

    public UserInfoController(TaskService taskService) {
        this.taskService = taskService;
    }

    @CrossOrigin(origins = "http://localhost:8089")
    @GetMapping("/user/info")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) {
        return Collections.singletonMap("user_name", principal.getClaimAsString("preferred_username"));
    }

}
