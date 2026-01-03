package com.example.dairyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dairy_details extends AppCompatActivity {

    TextView tvDate, tvContent;
    Button back, quote;
    ImageView imgView;

    String moodLabel; // 👈 Declare moodLabel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_details);

        tvDate = findViewById(R.id.tvDetailDate);
        tvContent = findViewById(R.id.tvDetailContent);
        imgView = findViewById(R.id.imageViewDetail);
        back = findViewById(R.id.btnBack);
        quote = findViewById(R.id.btnQuote);

        // ✅ Get data from intent
        String diaryText = getIntent().getStringExtra("diary");
        String date = getIntent().getStringExtra("date");
        String imagePath = getIntent().getStringExtra("imagePath");
        moodLabel = getIntent().getStringExtra("mood"); // 👈 Get the mood

        tvDate.setText(date);
        tvContent.setText(diaryText);

        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                imgView.setImageBitmap(bitmap);
            }
        }

        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dairy_details.this, QuotesActivity.class);
                i.putExtra("mood", moodLabel); // ✅ Pass the mood
                startActivity(i);
            }
        });
    }

    public void goBack(View view) {
        finish(); // closes the details page
    }
}
