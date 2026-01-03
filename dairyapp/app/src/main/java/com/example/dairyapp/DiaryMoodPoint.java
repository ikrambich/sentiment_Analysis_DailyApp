package com.example.dairyapp;

public class DiaryMoodPoint {
    private String date;
    private String mood;

    public DiaryMoodPoint(String date, String mood) {
        this.date = date;
        this.mood = mood;
    }

    public String getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }
}
