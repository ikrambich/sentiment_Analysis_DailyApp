package com.example.dairyapp;

public class SentimentResponse {
    private String sentiment;
    private double confidence;
    private String message;

    public String getSentiment() {
        return sentiment;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getMessage() {
        return message;
    }
}
