package com.example.dairyapp;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.*;

public class EmotionChartActivity extends AppCompatActivity {
    LineChart lineChart;
    FirebaseFirestore db;
    FirebaseAuth auth;
    ArrayList<Entry> moodEntries = new ArrayList<>();
    ArrayList<String> dateLabels = new ArrayList<>();

    Map<String, Integer> moodMap = new HashMap<String, Integer>() {{
        put("sadness", 0);
        put("fear", 1);
        put("anger", 2);
        put("love", 3);
        put("joy", 4);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_summary);

        lineChart = findViewById(R.id.lineChart);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchMoodData();
    }

    private void fetchMoodData() {
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("diaries")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int index = 0;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String mood = doc.getString("mood");
                        if (mood != null && moodMap.containsKey(mood.toLowerCase())) {
                            moodEntries.add(new Entry(index, moodMap.get(mood.toLowerCase())));

                            // Format and save the date for X-axis
                            Date timestamp = doc.getTimestamp("timestamp").toDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.getDefault());
                            dateLabels.add(sdf.format(timestamp));

                            index++;
                        }

                    }
                    setupChart();
                });
    }

    private void setupChart() {
        LineDataSet dataSet = new LineDataSet(moodEntries, "Mood Over Time");
        dataSet.setColor(Color.MAGENTA);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawValues(false); // remove raw value labels on points

        // Apply data
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setText("Emotional Evolution 📈");

        // 🟣 Format Y-Axis with mood names
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setGranularity(1f);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) return "Sadness";
                if (value == 1) return "Fear";
                if (value == 2) return "Anger";
                if (value == 3) return "Love";
                if (value == 4) return "Joy";
                return "";
            }
        });

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // Show 1 label per step
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < dateLabels.size()) {
                    return dateLabels.get(index);
                }
                return "";
            }
        });
        lineChart.invalidate();  // 🔁 Refresh to show updated labels


    }

}

