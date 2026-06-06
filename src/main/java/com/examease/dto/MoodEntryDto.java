package com.examease.dto;

import com.examease.model.Mood;
import com.examease.model.StressTrigger;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class MoodEntryDto {
    @NotNull(message = "Please select a mood.")
    private Mood mood;

    private List<StressTrigger> triggers;
    
    @Size(max = 500, message = "Bothersome note must be under 500 characters")
    private String bothersomeNote;

    @Size(max = 500, message = "Helpful note must be under 500 characters")
    private String helpfulNote;

    public Mood getMood() { return mood; }
    public void setMood(Mood mood) { this.mood = mood; }
    
    public List<StressTrigger> getTriggers() { return triggers; }
    public void setTriggers(List<StressTrigger> triggers) { this.triggers = triggers; }
    
    public String getBothersomeNote() { return bothersomeNote; }
    public void setBothersomeNote(String bothersomeNote) { this.bothersomeNote = bothersomeNote; }
    
    public String getHelpfulNote() { return helpfulNote; }
    public void setHelpfulNote(String helpfulNote) { this.helpfulNote = helpfulNote; }
}
