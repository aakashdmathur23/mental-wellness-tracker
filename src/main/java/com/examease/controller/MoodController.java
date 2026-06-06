package com.examease.controller;

import com.examease.dto.MoodEntryDto;
import com.examease.model.Mood;
import com.examease.model.StressTrigger;
import com.examease.service.WellnessService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkin")
public class MoodController {

    private final WellnessService wellnessService;

    public MoodController(WellnessService wellnessService) {
        this.wellnessService = wellnessService;
    }

    @GetMapping
    public String showCheckinForm(Model model) {
        model.addAttribute("moodEntryDto", new MoodEntryDto());
        model.addAttribute("moods", Mood.values());
        model.addAttribute("triggers", StressTrigger.values());
        return "checkin";
    }

    @PostMapping
    public String submitCheckin(@Valid @ModelAttribute("moodEntryDto") MoodEntryDto dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moods", Mood.values());
            model.addAttribute("triggers", StressTrigger.values());
            return "checkin";
        }
        
        wellnessService.saveEntry(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Check-in saved successfully! Check out your wellness suggestion on the dashboard.");
        return "redirect:/";
    }
}
