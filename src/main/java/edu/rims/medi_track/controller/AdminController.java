package edu.rims.medi_track.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${frontend.admin.page}")
    private String FRONTPAGE_PREFIX;
}
