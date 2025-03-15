package edu.rims.medi_track.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${frontend.admin.page}")
    private String FRONTEND_PREFIX;

    @GetMapping({"/dashboard", "/", ""})
    String dashboardPage() {
        return String.format(FRONTEND_PREFIX, "home");
    }

    @GetMapping("/departments")
    String departmentsPage() {
        return String.format(FRONTEND_PREFIX, "department");
    }

    @GetMapping("/appointments")
    String appointmentsPage() {
        return String.format(FRONTEND_PREFIX, "appointment");
    }

    @GetMapping("/doctors")
    String doctorsPage() {
        return String.format(FRONTEND_PREFIX, "doctor");
    }

    @GetMapping("/patients")
    String patientsPage() {
        return String.format(FRONTEND_PREFIX, "patient");
    }

    @GetMapping("/create")
    String createAdminPage() {
        return String.format(FRONTEND_PREFIX, "create-admin");
    }
}
