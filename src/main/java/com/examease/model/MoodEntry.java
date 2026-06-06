package com.examease.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mood mood;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<StressTrigger> triggers;

    @Column(length = 500)
    private String bothersomeNote;

    @Column(length = 500)
    private String helpfulNote;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Column(length = 1000)
    private String wellnessSuggestion;

    public MoodEntry() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Mood getMood() { return mood; }
    public void setMood(Mood mood) { this.mood = mood; }
    
    public List<StressTrigger> getTriggers() { return triggers; }
    public void setTriggers(List<StressTrigger> triggers) { this.triggers = triggers; }
    
    public String getBothersomeNote() { return bothersomeNote; }
    public void setBothersomeNote(String bothersomeNote) { this.bothersomeNote = bothersomeNote; }
    
    public String getHelpfulNote() { return helpfulNote; }
    public void setHelpfulNote(String helpfulNote) { this.helpfulNote = helpfulNote; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getWellnessSuggestion() { return wellnessSuggestion; }
    public void setWellnessSuggestion(String wellnessSuggestion) { this.wellnessSuggestion = wellnessSuggestion; }
}
