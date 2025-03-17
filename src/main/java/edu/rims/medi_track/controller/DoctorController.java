package edu.rims.medi_track.controller;

import edu.rims.medi_track.constants.AppointmentStatus;

import edu.rims.medi_track.dto.DoctorUpdateRequestDTO;
import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.service.ActivityService;
import edu.rims.medi_track.service.AppointmentService;
import edu.rims.medi_track.service.DoctorService;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    @Value("#{T(String).format('redirect:%s', '${doctor.page.url}%s')}")
    private String DOCTOR_BASE_URL;

    @Value("${frontend.doctor.page}")
    private String FRONTEND_PREFIX;

    private final UserService userService;

    private final AppointmentService appointmentService;

    private final DoctorService doctorService;

    private final ActivityService activityService;

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("doctorName", user.getName());
        model.addAttribute("doctorId", user.getId());
        model.addAttribute("doctorFirstName", user.getName().split(" ")[0]);
        model.addAttribute("totalNotification", getNotifications(user.getId()));
    }

    @GetMapping({"/dashboard", "/", ""})
    String homePage(Model model, Principal principal) {
        var doctorId = userService.getUserByEmail(principal.getName()).getId();

        model.addAttribute("totalPatients", appointmentService.countPatientByDoctorId(doctorId));
        model.addAttribute("upcomingAppointments", appointmentService.upcomingDoctorAppointments(doctorId));
        model.addAttribute("totalEarnings", appointmentService.getDoctorTotalEarning(doctorId));
        model.addAttribute("appointments", appointmentService.upcoming5DoctorAppointments(doctorId));
        model.addAttribute("activities", activityService.getRecent5Activities(doctorId));

        return String.format(FRONTEND_PREFIX, "home");
    }

    @GetMapping("/appointments")
    String appointmentPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal,
            Model model
    ) {
        var userId = userService.getUserByEmail(principal.getName()).getId();

        var pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending());

        var appointments = appointmentService.getDoctorAppointments(userId, pageable);

        model.addAttribute("appointments", appointments.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointments.getTotalPages());

        return String.format(FRONTEND_PREFIX, "appointment");
    }


    @GetMapping("/patients")
    String patientPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal,
            Model model
    ) {
        var doctorId = userService.getUserByEmail(principal.getName()).getId();

        var pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending());

        var appointments = appointmentService.getDoctorPendingAppointments(doctorId, pageable);

        model.addAttribute("appointments", appointments.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointments.getTotalPages());

        return String.format(FRONTEND_PREFIX, "patient");
    }

    @GetMapping("/messages")
    String messagePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal,
            Model model
    ) {
        return String.format(FRONTEND_PREFIX, "message");
    }

    @GetMapping("/profile")
    String profilePage(Principal principal, Model model) {
        var doctor = userService.getUserByEmail(principal.getName());
        model.addAttribute("doctor", doctor);
        return String.format(FRONTEND_PREFIX, "profile");
    }

    @PostMapping("/profile")
    String profileUpdate(@ModelAttribute DoctorUpdateRequestDTO dto) {
        doctorService.updateDoctor(dto);
        return String.format(DOCTOR_BASE_URL, "/profile");
    }

    @GetMapping("/appointment/status-change")
    String appointmentStatusChange(
            @RequestParam String appointmentId,
            @RequestParam AppointmentStatus status
            ) {
        appointmentService.updateAppointmentStatus(appointmentId, status);
        if (status == AppointmentStatus.COMPLETED)
            return String.format(DOCTOR_BASE_URL, "/appointments");
        return String.format(DOCTOR_BASE_URL, "/patients");
    }

    private long getNotifications(String doctorId) {
        return appointmentService.countDoctorPendingAppointments(doctorId);
    }
}
