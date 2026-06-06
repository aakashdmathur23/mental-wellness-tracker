package com.examease.model;

public enum StressTrigger {
    EXAM_PRESSURE("Exam Pressure"),
    MOCK_TEST_SCORE("Mock Test Scores"),
    TIME_MANAGEMENT("Time Management"),
    PARENTAL_EXPECTATIONS("Parental Expectations"),
    COMPARISON_WITH_OTHERS("Comparison With Others"),
    SOCIAL_MEDIA("Social Media"),
    SLEEP_ISSUES("Sleep Issues"),
    HEALTH_ISSUES("Health Issues"),
    FUTURE_UNCERTAINTY("Future Uncertainty"),
    RELATIONSHIP_PROBLEMS("Relationship Problems");

    private final String displayValue;

    StressTrigger(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
