package edu.rims.medi_track.controller;

import edu.rims.medi_track.dto.DoctorResponseDTO;
import edu.rims.medi_track.entity.Doctor;
import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.service.DepartmentService;
import edu.rims.medi_track.service.DoctorService;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    @Value("${frontend.client.page}")
    private String FRONTEND_PREFIX;

    private final UserService userService;

    private final DepartmentService departmentService;

    private final DoctorService doctorService;

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
    public String doctorPage(@RequestParam(required = false) String departmentId,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctors;

        if (departmentId != null && !departmentId.isEmpty()) {
            doctors = doctorService.getDoctorsByDepartment(departmentId, pageable);
            model.addAttribute("selectedDepartmentId", departmentId); // Preserve selected filter
        } else {
            doctors = doctorService.getAllDoctors(pageable);
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", doctors.getTotalPages());

        return String.format(FRONTEND_PREFIX, "doctor");
    }


    @GetMapping("/departments")
    String departmentsPage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           Model model) {
        var pageable = PageRequest.of(page, size);
        var departmentPage = departmentService.getDepartments(pageable);

        model.addAttribute("departments", departmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", departmentPage.getTotalPages());
        model.addAttribute("totalItems", departmentPage.getTotalElements());

        return String.format(FRONTEND_PREFIX, "department");
    }

    @GetMapping("/doctors/{doctorId}")
    @ResponseBody
    public DoctorResponseDTO doctorDetail(@PathVariable String doctorId) {
        return doctorService.getDoctorById(doctorId);
    }
}
