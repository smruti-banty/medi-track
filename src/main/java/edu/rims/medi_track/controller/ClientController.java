package edu.rims.medi_track.controller;

import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    @Value("${frontend.client.page}")
    private String FRONTEND_PREFIX;

    private final UserService userService;

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("clientName", user.getName());
        model.addAttribute("clientId", user.getId());
    }

    @GetMapping({"/dashboard", "/", ""})
    String homePage() {
        return String.format(FRONTEND_PREFIX, "home");
    }

    @GetMapping("/appointments")
    String appointmentPage() {
        return String.format(FRONTEND_PREFIX, "appointment");
    }

    @GetMapping("/doctors")
    String doctorPage() {
        return String.format(FRONTEND_PREFIX, "doctor");
    }

    @GetMapping("/departments")
    String departmentPage() {
        return String.format(FRONTEND_PREFIX, "department");
    }
}
