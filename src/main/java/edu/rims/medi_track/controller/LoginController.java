package edu.rims.medi_track.controller;

import edu.rims.medi_track.constants.UserRole;
import edu.rims.medi_track.entity.User;
import edu.rims.medi_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private static final String DASHBOARD_PAGE = "/dashboard";

    @Value("#{T(String).format('redirect:%s', '${admin.page.url}%s')}")
    private String ADMIN_BASE_URL;

    @Value("#{T(String).format('redirect:%s', '${doctor.page.url}%s')}")
    private String DOCTOR_BASE_URL;

    @Value("#{T(String).format('redirect:%s', '${client.page.url}%s')}")
    private String CLIENT_BASE_URL;

    private final UserService userService;

    @PostMapping("/success")
    public String loginSuccess(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        if (user.getUserRole() == UserRole.ADMIN) {
            return String.format(ADMIN_BASE_URL, DASHBOARD_PAGE);
        } else if (user.getUserRole() == UserRole.DOCTOR) {
            return String.format(DOCTOR_BASE_URL, DASHBOARD_PAGE);
        } else  {
            return String.format(CLIENT_BASE_URL, DASHBOARD_PAGE);
        }
    }
}
