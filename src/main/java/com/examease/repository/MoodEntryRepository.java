package com.examease.repository;

import com.examease.model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findAllByOrderByTimestampDesc();
    
    List<MoodEntry> findTop7ByOrderByTimestampDesc();
    
    List<MoodEntry> findAllByTimestampAfterOrderByTimestampDesc(LocalDateTime timestamp);
}
