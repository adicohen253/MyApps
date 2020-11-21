package com.example.mycalculator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity  {
    TextView screen;
    ScriptEngine engine;

    public void buttonPressed(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.Equals:
                try
                {
                    String expression = screen.getText().toString().replace('X', '*').replace('÷', '/');
                    double x = (double) engine.eval(expression);
                    if(Double.isInfinite(x))
                    {
                        throw new ArithmeticException();
                    }
                    if(x%1==0)
                        screen.setText(String.valueOf((int)x));
                    else
                        screen.setText(String.valueOf(x));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    openDialog();
                }
                break;

            case R.id.Del:
                String s = screen.getText().toString();
                if(s.length() > 0)
                    s = s.substring(0, s.length()-1);
                screen.setText(s);
                break;

            case R.id.Clear:
                screen.setText("");
                break;

            default:
                screen.append(view.getTag().toString());
                break;
        }


    }

    public void openDialog()
    {
        ErrorDialog dialog = new ErrorDialog();
        dialog.show(getSupportFragmentManager(), "OK");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.Screen);
        engine = new ScriptEngineManager().getEngineByName("rhino");

    }


}