package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerFrom;
    Spinner spinnerTo;
    EditText amountEditText;
    HashMap<String, Double> exchangeRate;
    DecimalFormat df;
    TextView result;


    public void convert(View view) throws InterruptedException {
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e) {
            Log.i("Error", "Cant hide keyboard");
        }
        TimeUnit.MILLISECONDS.sleep(100); // wait for keyboard to hide
        String currencyFrom = spinnerFrom.getSelectedItem().toString();
        String currencyTo = spinnerTo.getSelectedItem().toString();
        double rate = exchangeRate.get(currencyTo) / exchangeRate.get(currencyFrom);
        double amount = Double.parseDouble(amountEditText.getText().toString());
        result.setText(df.format(amount * rate));

    }

    public void buildExchangeRate(JSONObject jsonObject) {
        try {
            jsonObject = jsonObject.getJSONObject("rates");
            exchangeRate.put("Dollar-$", 1.0);
            exchangeRate.put("Euro-€", jsonObject.getDouble("EUR"));
            exchangeRate.put("Pound-₤", jsonObject.getDouble("GBP"));
            exchangeRate.put("Shekel-₪", jsonObject.getDouble("ILS"));
            exchangeRate.put("Yen-¥", jsonObject.getDouble("JPY"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static class Downloader extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... urls) {
            StringBuilder data = new StringBuilder();
            HttpURLConnection urlConnection;
            try
            {
                urlConnection = (HttpURLConnection) new URL(urls[0]).openConnection();
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                int temp = streamReader.read();
                while (temp != -1)
                {
                    data.append((char) temp);
                    temp = streamReader.read();
                }
                return new JSONObject(data.toString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.amountEditText = findViewById(R.id.inputEditText);
        this.result = findViewById(R.id.resultTextView);
        //set the options in the spinner views
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerFrom = findViewById(R.id.spinnerFrom);
        this.spinnerTo = findViewById(R.id.spinnerTo);
        this.spinnerFrom.setAdapter(currencyAdapter);
        this.spinnerTo.setAdapter(currencyAdapter);

        this.exchangeRate = new HashMap<>();
        Downloader downloader = new Downloader();
        try
        {
            buildExchangeRate(downloader.execute("https://api.exchangerate.host/latest?" +
                    "base=USD&symbols=ILS,EUR,GBP,JPY").get());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //building format to output
        this.df = new DecimalFormat("##.#####");
    }
}