package com.example.dairyapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/api/analyze_text")
    Call<SentimentResponse> analyzeText(@Body SentimentRequest request);
}
