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
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    @Value("${frontend.doctor.page}")
    private String FRONTEND_PREFIX;

    private final UserService userService;

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("doctorName", user.getName());
        model.addAttribute("doctorId", user.getId());
        model.addAttribute("doctorFirstName", user.getName().split(" ")[0]);
    }

    @GetMapping({"/dashboard", "/", ""})
    String homePage() {
        return String.format(FRONTEND_PREFIX, "home");
    }

    @GetMapping("/appointments")
    String appointmentPage() {
        return String.format(FRONTEND_PREFIX, "appointment");
    }

    @GetMapping("/patients")
    String patientPage() {
        return String.format(FRONTEND_PREFIX, "patient");
    }

    @GetMapping("/messages")
    String messagePage() {
        return String.format(FRONTEND_PREFIX, "message");
    }

    @GetMapping("/profile")
    String profilePage() {
        return String.format(FRONTEND_PREFIX, "profile");
    }

}
