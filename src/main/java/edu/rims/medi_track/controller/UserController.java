package edu.rims.medi_track.controller;

import edu.rims.medi_track.dto.UserRegistrationDTO;
import edu.rims.medi_track.service.DepartmentService;
import edu.rims.medi_track.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Value("#{T(String).format('redirect:%s', '${user.page.url}%s')}")
    private String USER_BASE_URL;

    @Value("${frontend.user.page}")
    private String FRONTPAGE_PREFIX;

    private final UserService userService;

    private final DepartmentService departmentService;

    @GetMapping("/login")
    String loginPage() {
        return String.format(FRONTPAGE_PREFIX, "login");
    }

    @GetMapping("/registration")
    String registrationPage(@RequestParam(required = false) String message, Model model) {
        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("departments", departmentService.getDepartments());
        return String.format(FRONTPAGE_PREFIX, "registration");
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute UserRegistrationDTO userDTO) {
        userService.registerUser(userDTO);
        String message = "Registration successful!";
        return String.format(USER_BASE_URL, "/registration?message=" + message);
    }
}
