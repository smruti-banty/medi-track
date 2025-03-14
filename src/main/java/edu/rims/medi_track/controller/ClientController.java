package edu.rims.medi_track.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Value("${frontend.client.page}")
    private String FRONTEND_PREFIX;

    @GetMapping({"/dashboard", "/", ""})
    String homePage() {
        return String.format(FRONTEND_PREFIX, "home");
    }
}
