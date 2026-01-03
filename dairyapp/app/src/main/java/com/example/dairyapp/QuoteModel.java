package com.example.dairyapp;

public class QuoteModel {
    private String mood;
    private String quote;

    public QuoteModel(String mood, String quote) {
        this.mood = mood;
        this.quote = quote;
    }

    public String getMood() {
        return mood;
    }

    public String getQuote() {
        return quote;
    }
}
