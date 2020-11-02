package com.example.myapp;


import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerFrom;
    Spinner spinnerTo;
    EditText amountEditText;
    HashMap<String, Double> exchangeRate;
    DecimalFormat df;
    RequestQueue requestQueue;


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
        amount = Double.parseDouble(df.format(amount * rate));
        Toast.makeText(this, String.valueOf(amount), Toast.LENGTH_SHORT).show();
    }

    public void buildExchangeRate()
    {
        exchangeRate = new HashMap<>();

        Response.Listener<JSONObject> response = response1 -> {
            try
            {
                JSONObject currency = response1.getJSONObject("rates");
                exchangeRate.put("Dollar-$", 1.0);
                exchangeRate.put("Euro-€", currency.getDouble("EUR"));
                exchangeRate.put("Pound-₤", currency.getDouble("GBP"));
                exchangeRate.put("Shekel-₪", currency.getDouble("ILS"));
                exchangeRate.put("Yen-¥", currency.getDouble("JPY"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        };

        Response.ErrorListener errorListener = Throwable::printStackTrace;

        String url = "https://api.exchangerate.host/latest?base=USD&symbols=ILS,EUR,GBP,JPY";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response, errorListener);

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEditText = findViewById(R.id.inputEditText);

        //set the options in the spinner views
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        spinnerFrom.setAdapter(currencyAdapter);
        spinnerTo.setAdapter(currencyAdapter);

        requestQueue = Volley.newRequestQueue(this);
        buildExchangeRate();

        //building format to output
        df = new DecimalFormat("##.#####");
    }
}