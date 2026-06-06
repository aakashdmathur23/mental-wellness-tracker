package com.examease.controller;

import com.examease.model.StudentProfile;
import com.examease.service.WellnessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final WellnessService wellnessService;

    public ProfileController(WellnessService wellnessService) {
        this.wellnessService = wellnessService;
    }

    @GetMapping("/setup")
    public String setupPage(Model model) {
        StudentProfile profile = wellnessService.getProfile();
        if (profile == null) {
            profile = new StudentProfile();
        }
        model.addAttribute("profile", profile);
        return "profile";
    }

    @PostMapping("/setup")
    public String saveProfile(@ModelAttribute StudentProfile profile) {
        wellnessService.saveProfile(profile);
        return "redirect:/";
    }
}
