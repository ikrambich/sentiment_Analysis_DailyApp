package com.example.dairyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class displaying_diaries extends AppCompatActivity {

    ArrayList<diariesModel> diariesModels = new ArrayList<>();
    ImageButton btnAdd;
    Button btnMood;
    FirebaseAuth auth;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    Diary_RecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_displaying_diaries);

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ImageButton btnAdd = findViewById(R.id.imageButton);
        btnMood = findViewById(R.id.btnMood);


        // Initialize RecyclerView
        recyclerView = findViewById(R.id.RecyclerViewdiaries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager

        // Set up and load diaries
        setUpDiariesModels();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(displaying_diaries.this,Diaries.class);
                startActivity(j);

            }
        });
        btnMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(displaying_diaries.this, EmotionChartActivity.class);
                startActivity(i);

            }
        });
    }

    private void setUpDiariesModels() {
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("diaries")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    diariesModels.clear();
                    String date;
                    String diary;

                    // Loop through Firestore documents
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());

                        date = sdf.format(document.getTimestamp("timestamp").toDate());
                        diary = document.getString("content");
                        String supportMessage = document.getString("supportMessage");
                        String mood = document.getString("mood");
                        String docId = document.getId(); // 👈 NEW

                        String imagePath = document.getString("localImagePath"); // 🟡 new line
                        diariesModels.add(new diariesModel(diary, date, imagePath, mood,supportMessage,docId));
                    }

                    // Initialize adapter AFTER data is loaded
                    adapter = new Diary_RecycleViewAdapter(this, diariesModels);
                    recyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    // Handle failure (optional)
                });
    }
}
