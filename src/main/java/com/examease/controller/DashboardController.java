package com.examease.controller;

import com.examease.model.MoodEntry;
import com.examease.model.StudentProfile;
import com.examease.service.WellnessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final WellnessService wellnessService;

    public DashboardController(WellnessService wellnessService) {
        this.wellnessService = wellnessService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<MoodEntry> entries = wellnessService.getAllEntries();
        StudentProfile profile = wellnessService.getProfile();
        
        // Data for charts
        List<String> labels = entries.stream()
                .map(e -> e.getTimestamp().toLocalDate().toString())
                .collect(Collectors.toList());
        
        List<String> moods = entries.stream()
                .map(e -> e.getMood().name())
                .collect(Collectors.toList());

        model.addAttribute("profile", profile);
        model.addAttribute("entries", entries);
        model.addAttribute("streak", wellnessService.calculateStreak());
        model.addAttribute("wellnessScore", wellnessService.calculateWellnessScore());
        model.addAttribute("topTriggers", wellnessService.getTopTriggers());
        model.addAttribute("moodFrequencies", wellnessService.getMoodFrequencies());
        
        model.addAttribute("labels", labels);
        model.addAttribute("moods", moods);
        return "dashboard";
    }

    @GetMapping("/support")
    public String supportPage(Model model) {
        model.addAttribute("profile", wellnessService.getProfile());
        return "support";
    }
}
