package com.example.dairyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Diaries extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    EditText text;
    Button btnSave, btnUpload;
    ImageView imageView;
    private static final int IMAGE_PICK_CODE = 1000;
    Uri selectedImageUri;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diaries);

        text = findViewById(R.id.edWriting);
        btnSave = findViewById(R.id.btnSave);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        btnSave.setOnClickListener(v -> {
            String content = text.getText().toString();
            if (content.isEmpty()) {
                Toast.makeText(Diaries.this, "Please the content can not be Empty", Toast.LENGTH_LONG).show();
            } else {
                SentimentRequest request = new SentimentRequest(content);
                apiService.analyzeText(request).enqueue(new Callback<SentimentResponse>() {
                    @Override
                    public void onResponse(Call<SentimentResponse> call, Response<SentimentResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String moodLabel = response.body().getSentiment();
                            double confidence = response.body().getConfidence();

                            String userId = auth.getCurrentUser().getUid();
                            Map<String, Object> diaryEntry = new HashMap<>();
                            diaryEntry.put("content", content);
                            diaryEntry.put("timestamp", FieldValue.serverTimestamp());
                            diaryEntry.put("mood", moodLabel);
                            diaryEntry.put("confidence", confidence);
                            diaryEntry.put("supportMessage", getSupportMessage(moodLabel));

                            String localPath = null;
                            if (selectedImageUri != null) {
                                localPath = saveImageLocally(selectedImageUri);
                            }
                            diaryEntry.put("localImagePath", localPath);

                            db.collection("users").document(userId).collection("diaries")
                                    .add(diaryEntry)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(Diaries.this, "Diary saved with mood: " + moodLabel, Toast.LENGTH_LONG).show();
                                        text.setText("");
                                        startActivity(new Intent(Diaries.this, displaying_diaries.class));
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(Diaries.this, "Failed to save diary", Toast.LENGTH_LONG).show());
                        } else {
                            Toast.makeText(Diaries.this, "API response error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SentimentResponse> call, Throwable t) {
                        Toast.makeText(Diaries.this, "API failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }

    private String saveImageLocally(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            String fileName = "diary_" + System.currentTimeMillis() + ".jpg";
            File directory = new File(getExternalFilesDir(null), "diary_images");
            if (!directory.exists()) directory.mkdirs();
            File file = new File(directory, fileName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSupportMessage(String mood) {
        switch (mood.toLowerCase()) {
            case "sadness": return "I see you’re feeling down. Please don’t be. You’re stronger than you think 💙";
            case "joy": return "I’m so happy you’re feeling joyful today! Keep shining ☀️";
            case "anger": return "Breathe. It’s okay to feel angry sometimes. You’re doing your best ❤️";
            case "fear": return "You’re not alone. Whatever scares you, you’ll overcome it 💪";
            case "love": return "Love is beautiful. Cherish it and let it grow 💕";
            default: return "I’m here for you, always.";
        }
    }
}
