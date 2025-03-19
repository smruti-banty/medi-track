package edu.rims.medi_track.controller;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.dto.AdminRegistrationDTO;
import edu.rims.medi_track.dto.DepartmentDTO;
import edu.rims.medi_track.dto.DepartmentResponseDTO;
import edu.rims.medi_track.entity.Department;
import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.service.AppointmentService;
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

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Value("#{T(String).format('redirect:%s', '${admin.page.url}%s')}")
    private String ADMIN_BASE_URL;

    @Value("${frontend.admin.page}")
    private String FRONTEND_PREFIX;

    private final UserService userService;

    private final DoctorService doctorService;

    private final DepartmentService departmentService;

    private final AppointmentService appointmentService;

    @GetMapping({"/dashboard", "/", ""})
    String dashboardPage(Model model) {
        model.addAttribute("totalDoctors", userService.doctors());
        model.addAttribute("totalClients", userService.clients());
        model.addAttribute("totalDepartments", departmentService.departments());
        model.addAttribute("totalAppointments", appointmentService.countAll());
        model.addAttribute("recentUsers", userService.getLast5Users());
        model.addAttribute("appointments", appointmentService.getLast5Appointments());
        return String.format(FRONTEND_PREFIX, "home");
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

    @GetMapping("/appointments")
    public String appointmentsPage(
            Model model,
            @RequestParam(defaultValue = "0") int page,  // Default page number
            @RequestParam(defaultValue = "5") int size   // Default page size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        var appointments = appointmentService.getAppointments(pageable);

        model.addAttribute("appointments", appointments.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointments.getTotalPages());
        model.addAttribute("totalItems", appointments.getTotalElements());

        return String.format(FRONTEND_PREFIX, "appointment");
    }

    @GetMapping("/doctors")
    String doctorsPage(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> doctorPage = userService.getUsersByRole(UserRole.DOCTOR, pageable);

        model.addAttribute("doctors", doctorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", doctorPage.getTotalPages());
        model.addAttribute("totalItems", doctorPage.getTotalElements());

        return String.format(FRONTEND_PREFIX, "doctor");
    }

    @GetMapping("/doctors/specialist")
    String markAsSpecialist(@RequestParam String doctorId) {
        doctorService.makeSpecialist(doctorId);
        return String.format(ADMIN_BASE_URL, "/doctors");
    }

    @GetMapping("/patients")
    String patientsPage(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> patientPage = userService.getUsersByRole(UserRole.CLIENT, pageable);

        model.addAttribute("clients", patientPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", patientPage.getTotalPages());
        model.addAttribute("totalItems", patientPage.getTotalElements());

        return String.format(FRONTEND_PREFIX, "patient");
    }


    @GetMapping("/create")
    String createAdminPage() {
        return String.format(FRONTEND_PREFIX, "create-admin");
    }

    @PostMapping("/create")
    String createAdmin(@ModelAttribute AdminRegistrationDTO dto) {
        userService.registerAdmin(dto);
        return String.format(ADMIN_BASE_URL, "/create");
    }

    @PostMapping("/create/department")
    String createDepartment(@ModelAttribute DepartmentDTO dto) {
        departmentService.createDepartment(dto);
        return String.format(ADMIN_BASE_URL, "/departments");
    }

    @GetMapping("/departments/delete")
    String removeDepartment(@RequestParam String departmentId) {
        departmentService.removeDepartmentById(departmentId);
        return String.format(ADMIN_BASE_URL, "/departments");
    }

    @GetMapping("/departments/{id}")
    @ResponseBody
    DepartmentResponseDTO getDepartment(@PathVariable String id) {
        var department = departmentService.getDepartmentById(id).orElseThrow();
        return new DepartmentResponseDTO(department.getId(),
                department.getDepartmentName(), department.getDepartmentDescription());
    }
}
