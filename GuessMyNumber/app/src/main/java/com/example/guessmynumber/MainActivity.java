package com.example.guessmynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    Spinner levelsSpinner;
    TextView guessesTextView;
    TextView rangeTextView;
    HashMap<String, Integer> guesses;
    HashMap<String, Integer> topRange;

    public void play(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("TopRange", this.topRange.get(this.levelsSpinner.getSelectedItem().toString()));
        intent.putExtra("GuessesNumber", this.guesses.get(this.levelsSpinner.getSelectedItem().toString()));
        startActivity(intent);
    }

    private void buildLevelMaps()
    {
        this.topRange.put("Easy", 20);
        this.topRange.put("Normal", 50);
        this.topRange.put("Hard", 100);
        this.topRange.put("Insane", 500);
        this.topRange.put("Impossible", 1000);
        this.guesses.put("Easy", 5);
        this.guesses.put("Normal", 7);
        this.guesses.put("Hard", 10);
        this.guesses.put("Insane", 13);
        this.guesses.put("Impossible", 16);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.guesses = new HashMap<>();
        this.topRange = new HashMap<>();
        buildLevelMaps();

        this.guessesTextView = findViewById(R.id.guessesTextView);
        this.rangeTextView = findViewById(R.id.rangeTextView);
        this.levelsSpinner = findViewById(R.id.levelSelectSpinner);

        this.levelsSpinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.levels,
                android.R.layout.simple_spinner_item));
        this.levelsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                guessesTextView.setText(Integer.toString(guesses.get(levelsSpinner.getSelectedItem().toString())));
                rangeTextView.setText("1-" + topRange.get(levelsSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
}