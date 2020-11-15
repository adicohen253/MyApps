package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    boolean isRedsTurn = true;
    boolean isThereWinner = false;
    int turnsCounter = 1;
    int [] positions = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 1 - Red, 2 - Yellow, 0 - Empty
    int [] [] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    GridLayout gridLayout;
    TextView winnerTextView;
    Button playAgainButton;


    public void dropIn(View view)
    {
        ImageView imageView = (ImageView) view;

        if(positions[Integer.parseInt(imageView.getTag().toString())] == 0 &&  ! isThereWinner)
        {
            imageView.setTranslationY(-1500);
            if(isRedsTurn)
            {
                imageView.setImageResource(R.drawable.red);
                positions[Integer.parseInt(imageView.getTag().toString())] = 1;
            }

            else
            {
                imageView.setImageResource(R.drawable.yellow);
                positions[Integer.parseInt(imageView.getTag().toString())] = 2;

            }
            isRedsTurn = ! isRedsTurn;
            imageView.animate().setDuration(250).rotation(3600).translationYBy(1500);
            isThereWinner = isSomeoneWin();
            turnsCounter++;
        }
        if(turnsCounter == 9 && !isThereWinner)
            draw();

    }

    @SuppressLint("SetTextI18n")
    private void draw()
    {
        winnerTextView.setText("Draw");
        playAgainButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private boolean isSomeoneWin()
    {
       for(int [] winningPosition : winningPositions)
       {
           if(positions[winningPosition[0]] == positions[winningPosition[1]] &&
                   positions[winningPosition[0]] == positions[winningPosition[2]] &&
                   positions[winningPosition[0]] != 0)
           {
               if(positions[winningPosition[0]] == 1)
               {
                    winnerTextView.setText("Red wins");
               }

               else
               {
                   winnerTextView.setText("Yellow wins");
               }
               playAgainButton.setVisibility(View.VISIBLE);
               return true;
           }
       }
       return false;
    }

    public void playAgain(View view)
    {
        playAgainButton.setVisibility(View.INVISIBLE);
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }
        winnerTextView.setText("");
        isRedsTurn = true;
        isThereWinner = false;
        turnsCounter = 0;
        Arrays.fill(positions, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gridLayout = findViewById(R.id.gridLayout);

    }
}