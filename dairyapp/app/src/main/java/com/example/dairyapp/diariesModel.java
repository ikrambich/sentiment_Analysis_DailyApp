package com.example.dairyapp;

public class diariesModel {
    String diaries;
    String dates;
    String imagePath;
    String mood;

    String supportMessage;
    String documentId;





    public diariesModel(String diaries,String dates,String imagePath,String mood,String supportMessage, String documentId) {
        this.diaries = diaries;
        this.dates=dates;
        this.imagePath = imagePath;
        this.mood = mood;
        this.documentId = documentId;

        this.supportMessage = supportMessage;

    }

    public String getDiaries() {
        return diaries;
    }

    public String getDates(){
        return dates;

    }
    public String getDocumentId() {
        return documentId;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getMood() {
        return mood;
    }


    public String getSupportMessage() {
        return supportMessage;
    }
}
