package com.example.guessmynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    TextView guessesLeft;
    EditText userGuessEditText;

    @SuppressLint("SetTextI18n")
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

            if(userGuess == 0)
                Toast.makeText(this, "Number not in the range Try again", Toast.LENGTH_SHORT).show();
            else if(userGuess < this.mySecretNumber)
            {
                this.guesses--;
                if(this.guesses == 0)
                {
                    Toast.makeText(this, "There are no guesses left, you lost", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(this, "Higher!", Toast.LENGTH_SHORT).show();
            }

            else if(userGuess > this.mySecretNumber)
            {
                this.guesses--;
                if(this.guesses == 0)
                {
                    Toast.makeText(this, "There are no guesses left, you lost", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(this, "Lower!", Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(this, "That's my number!", Toast.LENGTH_SHORT).show();
                finish();
            }
            this.guessesLeft.setText(this.guesses + " guesses left");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        this.guesses = intent.getIntExtra("GuessesNumber", -1);
        this.topRange = intent.getIntExtra("TopRange", -1);

        this.guessesLeft = findViewById(R.id.gussesLeftTextView);
        this.userGuessEditText = findViewById(R.id.guessEditText);
        TextView title = findViewById(R.id.titleTextView);
        title.append(" " + topRange);
        this.guessesLeft.setText(this.guesses + " guesses left");

        this.mySecretNumber = (int) Math.ceil(Math.random() * topRange);

    }
}