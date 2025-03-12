package edu.rims.medi_track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Value("${frontend.user.page}")
    private String FRONTPAGE_PREFIX;

    @GetMapping("/login")
    String loginPage() {
        return String.format(FRONTPAGE_PREFIX, "login");
    }

    @GetMapping("/registration")
    String registrationPage() {
        return String.format(FRONTPAGE_PREFIX, "registration");
    }
}
