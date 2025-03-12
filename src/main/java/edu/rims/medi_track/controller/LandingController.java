package edu.rims.medi_track.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
    @GetMapping
    String landingPage() {
        return "landing";
    }
}
