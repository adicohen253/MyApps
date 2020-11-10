package com.example.mycalculator;
import android.os.Bundle;
import android.system.ErrnoException;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {

    public void buttonPressed(View view)
    {
        TextView screen = findViewById(R.id.Screen);
        int id = view.getId();
        switch (id)
        {
            case R.id.b0:
                screen.append("0");
                break;
            case R.id.b1:
                screen.append("1");
                break;
            case R.id.b2:
                screen.append("2");
                break;
            case R.id.b3:
                screen.append("3");
                break;
            case R.id.b4:
                screen.append("4");
                break;

            case R.id.b5:
                screen.append("5");
                break;

            case R.id.b6:
                screen.append("6");
                break;

            case R.id.b7:
                screen.append("7");
                break;

            case R.id.b8:
                screen.append("8");
                break;

            case R.id.b9:
                screen.append("9");
                break;

            case R.id.LeftBracket:
                screen.append("(");
                break;

            case R.id.RightBracket:
                screen.append(")");
                break;

            case R.id.Dot:
                screen.append(".");
                break;

            case R.id.Mul:
                screen.append("X");
                break;

            case R.id.Div:
                screen.append("÷");
                break;

            case R.id.Add:
                screen.append("+");
                break;

            case R.id.Sub:
                screen.append("-");
                break;

            case R.id.Equals:
                try
                {
                    double x = eval(screen.getText().toString());
                    if(x%1==0)
                        screen.setText(String.valueOf((int)x));
                    else
                        screen.setText(String.valueOf(x));
                }
                catch (Exception e) {
                    screen.setText("");
                    opendialog();
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void opendialog()
    {
        ErrorDialog dialog = new ErrorDialog();
        dialog.show(getSupportFragmentManager(), "OK");
    }

    public static double eval(final String str)
    {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('X')) x *= parseFactor(); // multiplication
                    else if (eat('÷'))  // division
                    {
                        double denominator = parseFactor();
                        if(denominator==0)
                            throw new ArithmeticException();
                        else
                            x /= denominator;
                    }
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('('))
                {
                    x = parseExpression();
                    eat(')');
                }
                else if ((ch >= '0' && ch <= '9') || ch == '.')
                {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }
                else
                {
                    throw new RuntimeException();
                } 

                return x;
            }
        }.parse();

    }
}