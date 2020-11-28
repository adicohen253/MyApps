package com.example.mybraintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    TextView scoreTextView;
    TextView timeLeftTextView;
    TextView expressionTextView;
    GridLayout optionsGridLayout;

    Button [] optionButtons;
    String [] operators = {" + ", " - ", " * ", " / ", " % "};

    ScriptEngine engine;
    Random random = new Random();
    private int numberOfQuestions = 0;
    private int correctAnswers = 0;
    private final int SecondsToPlay = 10; //time in seconds for the game


    public void startGame(View startButton)
    {
        startButton.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        timeLeftTextView.setVisibility(View.VISIBLE);
        expressionTextView.setVisibility(View.VISIBLE);
        optionsGridLayout.setVisibility(View.VISIBLE);
        new CountDownTimer(SecondsToPlay * 1000, 1000)
        {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                timeLeftTextView.setText(((int)l/1000 + 1) + "s");
            }

            @Override
            public void onFinish() {
                timeLeftTextView.setText("0");
                for (Button optionButton : optionButtons) {
                    optionButton.setEnabled(false);
                }
            }
        }.start();
        buildExercise();
    }

    @SuppressLint("SetTextI18n")
    private void buildExercise()
    {
        String operator = operators[random.nextInt(5)];
        String expression = random.nextInt(20) + operator + random.nextInt(20);
        expressionTextView.setText(expression);
        int answer = 0;
        try {
            answer = (int) (double) engine.eval(expression);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> answersOptions = new ArrayList<>();
        int correctAnswerPosition = random.nextInt(4);
        for(int i=0;i<optionButtons.length;i++)
        {
            if(i==correctAnswerPosition) {
                optionButtons[i].setText(Integer.toString(answer));
                optionButtons[i].setTag(1);
                answersOptions.add(answer);
            }
            else
            {
                int randomOption = random.nextInt(answer+70);
                while(answersOptions.contains(randomOption))
                {
                    randomOption = random.nextInt(answer+70);
                }
                optionButtons[i].setText(Integer.toString(randomOption));
                optionButtons[i].setTag(0);
                answersOptions.add(randomOption);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void chooseAnswer(View view)
    {
        numberOfQuestions++;
        if((int) view.getTag() == 1)
        {
            correctAnswers++;
        }
        scoreTextView.setText(correctAnswers + "/" + numberOfQuestions);
        buildExercise();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTextView = findViewById(R.id.scoreTextView);
        timeLeftTextView = findViewById(R.id.timeLeftTextView);
        expressionTextView = findViewById(R.id.expressionTextView);
        optionsGridLayout = findViewById(R.id.optionsGridLayout);
        optionButtons = new Button[]
                {findViewById(R.id.optionButton1), findViewById(R.id.optionButton2),
                findViewById(R.id.optionButton3), findViewById(R.id.optionButton4)};
        engine = new ScriptEngineManager().getEngineByName("rhino");
    }
}