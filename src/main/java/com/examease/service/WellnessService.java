package com.examease.service;

import com.examease.dto.MoodEntryDto;
import com.examease.model.Mood;
import com.examease.model.MoodEntry;
import com.examease.model.StressTrigger;
import com.examease.model.StudentProfile;
import com.examease.repository.MoodEntryRepository;
import com.examease.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WellnessService {

    private final MoodEntryRepository repository;
    private final StudentProfileRepository profileRepository;

    public WellnessService(MoodEntryRepository repository, StudentProfileRepository profileRepository) {
        this.repository = repository;
        this.profileRepository = profileRepository;
    }

    public StudentProfile getProfile() {
        return profileRepository.findById(1L).orElse(null);
    }

    public void saveProfile(StudentProfile profile) {
        profile.setId(1L); // Single-user hackathon mode
        profileRepository.save(profile);
    }

    public MoodEntry saveEntry(MoodEntryDto dto) {
        MoodEntry entry = new MoodEntry();
        entry.setMood(dto.getMood());
        entry.setTriggers(dto.getTriggers());
        entry.setBothersomeNote(dto.getBothersomeNote());
        entry.setHelpfulNote(dto.getHelpfulNote());
        entry.setTimestamp(LocalDateTime.now());
        
        entry.setWellnessSuggestion(generateSuggestion(dto.getMood(), dto.getTriggers()));
        
        return repository.save(entry);
    }

    public List<MoodEntry> getAllEntries() {
        return repository.findAllByOrderByTimestampDesc();
    }
    
    // FR-10: Consistency/Streak Tracking
    public int calculateStreak() {
        // Fetch only last 60 days to be efficient instead of whole DB
        List<MoodEntry> entries = repository.findAllByTimestampAfterOrderByTimestampDesc(LocalDateTime.now().minusDays(60));
        if (entries.isEmpty()) return 0;

        int streak = 0;
        LocalDate currentDate = LocalDate.now();
        Set<LocalDate> entryDates = entries.stream()
                .map(e -> e.getTimestamp().toLocalDate())
                .collect(Collectors.toSet());

        // Check if there's an entry today or yesterday to continue the streak
        if (!entryDates.contains(currentDate) && !entryDates.contains(currentDate.minusDays(1))) {
            return 0; // Streak broken
        }

        LocalDate dateToCheck = entryDates.contains(currentDate) ? currentDate : currentDate.minusDays(1);

        while (entryDates.contains(dateToCheck)) {
            streak++;
            dateToCheck = dateToCheck.minusDays(1);
        }

        return streak;
    }

    // FR-9: Wellness Score Generation
    public int calculateWellnessScore() {
        List<MoodEntry> recentEntries = repository.findTop7ByOrderByTimestampDesc();
        if (recentEntries.isEmpty()) return 0;

        double totalScore = 0;
        for (MoodEntry entry : recentEntries) {
            totalScore += getMoodWeight(entry.getMood());
        }
        
        // Average score (0 to 1) scaled to 100
        double averageScore = totalScore / recentEntries.size();
        return (int) Math.round(averageScore * 100);
    }

    private double getMoodWeight(Mood mood) {
        return switch (mood) {
            case MOTIVATED, HAPPY -> 1.0;
            case CALM -> 0.8;
            case NEUTRAL -> 0.6;
            case TIRED -> 0.4;
            case ANXIOUS, SAD -> 0.2;
            case OVERWHELMED -> 0.1;
            default -> 0.5;
        };
    }

    // FR-7: Stress Trigger Analytics
    public List<Map.Entry<String, Long>> getTopTriggers() {
        // Look at last 30 days for top triggers to be relevant and efficient
        List<MoodEntry> entries = repository.findAllByTimestampAfterOrderByTimestampDesc(LocalDateTime.now().minusDays(30));
        return entries.stream()
                .filter(e -> e.getTriggers() != null)
                .flatMap(e -> e.getTriggers().stream())
                .collect(Collectors.groupingBy(StressTrigger::getDisplayValue, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    // FR-6: Mood Frequency Statistics
    public Map<String, Long> getMoodFrequencies() {
        return getAllEntries().stream()
                .collect(Collectors.groupingBy(e -> e.getMood().name(), Collectors.counting()));
    }

    // FR-8: Personalized Wellness Recommendations
    private String generateSuggestion(Mood mood, List<StressTrigger> triggers) {
        if (mood == Mood.OVERWHELMED || mood == Mood.ANXIOUS || mood == Mood.SAD) {
            return "Take a deep breath. Try the 4-7-8 breathing technique. Remember it's okay to feel this way.";
        }
        if (triggers != null) {
            if (triggers.contains(StressTrigger.SLEEP_ISSUES)) {
                return "A well-rested brain performs 30% better. Prioritize 7-8 hours of sleep tonight.";
            }
            if (triggers.contains(StressTrigger.COMPARISON_WITH_OTHERS)) {
                return "Your journey is unique. Unplug from social media and focus on your own micro-goals.";
            }
            if (triggers.contains(StressTrigger.TIME_MANAGEMENT)) {
                return "Try the Pomodoro technique: 25 minutes of focused study, followed by a 5-minute break.";
            }
        }
        if (mood == Mood.CALM || mood == Mood.HAPPY || mood == Mood.MOTIVATED) {
            return "You are doing great! Maintain this momentum and remember to take short breaks every 45 minutes.";
        }
        return "Stay hydrated, take short breaks, and remember that your exams don't define your worth.";
    }
}
