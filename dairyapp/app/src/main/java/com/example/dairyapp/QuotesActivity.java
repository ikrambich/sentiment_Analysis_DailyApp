package com.example.dairyapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuotesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuoteAdapter adapter;
    ArrayList<QuoteModel> allQuotes = new ArrayList<>();
    ArrayList<QuoteModel> filteredQuotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        recyclerView = findViewById(R.id.quotesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Step 1: Load all quotes
        allQuotes.add(new QuoteModel("joy", "Let your smile change the world."));
        allQuotes.add(new QuoteModel("joy", "Happiness is a choice, not a result."));
        allQuotes.add(new QuoteModel("joy", "Joy is the simplest form of gratitude."));
        allQuotes.add(new QuoteModel("joy", "Keep shining, the world needs your light."));
        allQuotes.add(new QuoteModel("joy", "Your vibe attracts your tribe."));
        allQuotes.add(new QuoteModel("joy", "Celebrate every tiny victory."));
        allQuotes.add(new QuoteModel("joy", "Laughter is timeless, imagination has no age."));
        allQuotes.add(new QuoteModel("joy", "The sun is shining and so are you."));
        allQuotes.add(new QuoteModel("joy", "Smile, it’s your superpower."));
        allQuotes.add(new QuoteModel("joy", "Joy multiplies when it's shared."));
        allQuotes.add(new QuoteModel("sadness", "Even the darkest night will end and the sun will rise."));
        allQuotes.add(new QuoteModel("sadness", "It’s okay to not be okay."));
        allQuotes.add(new QuoteModel("sadness", "Tears are words the heart can’t express."));
        allQuotes.add(new QuoteModel("sadness", "Healing takes time, and that’s okay."));
        allQuotes.add(new QuoteModel("sadness", "You’ve survived 100% of your worst days."));
        allQuotes.add(new QuoteModel("sadness", "Feelings are not facts."));
        allQuotes.add(new QuoteModel("sadness", "There is hope, even when your brain tells you there isn’t."));
        allQuotes.add(new QuoteModel("sadness", "You are loved more than you know."));
        allQuotes.add(new QuoteModel("sadness", "This too shall pass."));
        allQuotes.add(new QuoteModel("sadness", "Storms make trees take deeper roots."));
        allQuotes.add(new QuoteModel("anger", "You are not your anger."));
        allQuotes.add(new QuoteModel("anger", "Speak when you’re calm, not when you’re mad."));
        allQuotes.add(new QuoteModel("anger", "Breathe in peace, breathe out frustration."));
        allQuotes.add(new QuoteModel("anger", "Calm mind brings inner strength."));
        allQuotes.add(new QuoteModel("anger", "Let your anger flow into action, not destruction."));
        allQuotes.add(new QuoteModel("anger", "Anger is one letter short of danger."));
        allQuotes.add(new QuoteModel("anger", "Don’t let anger control your story."));
        allQuotes.add(new QuoteModel("anger", "Pause. Breathe. Proceed."));
        allQuotes.add(new QuoteModel("anger", "Rage doesn't build, it breaks."));
        allQuotes.add(new QuoteModel("anger", "Respond, don’t react."));
        allQuotes.add(new QuoteModel("love", "Where there is love, there is life."));
        allQuotes.add(new QuoteModel("love", "Love yourself first."));
        allQuotes.add(new QuoteModel("love", "To love and be loved is to feel the sun from both sides."));
        allQuotes.add(new QuoteModel("love", "True love begins when nothing is looked for in return."));
        allQuotes.add(new QuoteModel("love", "Let love lead the way."));
        allQuotes.add(new QuoteModel("love", "Love is not what you say, it’s what you do."));
        allQuotes.add(new QuoteModel("love", "Kindness is love made visible."));
        allQuotes.add(new QuoteModel("love", "The best love is the one that makes you a better person."));
        allQuotes.add(new QuoteModel("love", "In a world full of trends, remain classic — love."));
        allQuotes.add(new QuoteModel("love", "Real love still exists. Stay soft."));
        allQuotes.add(new QuoteModel("fear", "Feel the fear and do it anyway."));
        allQuotes.add(new QuoteModel("fear", "Courage is not the absence of fear, but action despite it."));
        allQuotes.add(new QuoteModel("fear", "What if I fall? Oh, but darling, what if you fly?"));
        allQuotes.add(new QuoteModel("fear", "Don’t be afraid of being a beginner."));
        allQuotes.add(new QuoteModel("fear", "Everything you’ve ever wanted is on the other side of fear."));
        allQuotes.add(new QuoteModel("fear", "Fear is temporary. Regret is forever."));
        allQuotes.add(new QuoteModel("fear", "Your fears are not facts."));
        allQuotes.add(new QuoteModel("fear", "You are braver than you believe."));
        allQuotes.add(new QuoteModel("fear", "Courage doesn’t always roar."));
        allQuotes.add(new QuoteModel("fear", "Take the risk or lose the chance."));


        // Step 2: Get mood passed from previous activity
        String mood = getIntent().getStringExtra("mood");
        if (mood != null) {
            for (QuoteModel q : allQuotes) {
                if (q.getMood().equalsIgnoreCase(mood)) {
                    filteredQuotes.add(q);
                }
            }
        }

        adapter = new QuoteAdapter(this, filteredQuotes);
        recyclerView.setAdapter(adapter);
    }
}
