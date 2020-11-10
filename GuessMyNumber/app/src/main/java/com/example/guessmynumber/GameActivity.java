package com.example.guessmynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class GameActivity extends AppCompatActivity {
    int mySecretNumber;
    int topRange;
    int guesses;
    EditText userGuessEditText;

    public void guess(View view) {
        String input = userGuessEditText.getText().toString();
        if(input.isEmpty())
        {
            Toast.makeText(this, "Please enter your guess", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.parseInt(input) > this.topRange)
        {
            Toast.makeText(this, "Number not in the range Try again", Toast.LENGTH_SHORT).show();
        }

        else
        {
            int userGuess = Integer.parseInt(input);
            if(userGuess < this.mySecretNumber)
            {
                Toast.makeText(this, "Higher!", Toast.LENGTH_SHORT).show();
            }

            else if(userGuess > this.mySecretNumber)
            {
                Toast.makeText(this, "Lower!", Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(this, "That's my number!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        this.guesses = intent.getIntExtra("GuessesNumber", -1);
        this.topRange = intent.getIntExtra("TopRange", -1);

        this.userGuessEditText = findViewById(R.id.guessEditText);
        TextView title = findViewById(R.id.titleTextView);
        title.append(" " + topRange);

        this.mySecretNumber = (int) Math.ceil(Math.random() * topRange);

    }
}