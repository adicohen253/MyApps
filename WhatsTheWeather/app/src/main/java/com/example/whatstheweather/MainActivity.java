package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    final String MyUrl = "https://api.openweathermap.org/data/2.5/weather?units=metric&appid=f09e9ea7ed975cf747f7d5e1bd1cab8d&q=";
    DecimalFormat df = new DecimalFormat("###.##");
    Button submitButton;
    EditText cityEditText;
    TextView mainWeatherTextView;
    TextView descriptionWeatherTextView;
    TextView cityTextView;
    TextView temperatureTextView;
    TextView pressureTextView;
    String [] myHeaders = {"Weather: ", "Description: ", "temperature: ", "pressure: "};

    private static class weatherDownloader extends AsyncTask<String, Void, JSONObject>
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


    @SuppressLint("SetTextI18n")
    public void displayWeather(View view)
    {
        cityEditText.setEnabled(false);
        cityEditText.setEnabled(true);

        weatherDownloader downloader = new weatherDownloader();
        String city = cityEditText.getText().toString();

        cityEditText.setText("");
        cityTextView.setText("");
        mainWeatherTextView.setText("");
        descriptionWeatherTextView.setText("");

        JSONObject weatherData;
        try {  // trying to get data
            weatherData = downloader.execute(MyUrl + city).get();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Cant get weather data!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {  // checking if there is weather information
            mainWeatherTextView.setText(myHeaders[0] + weatherData.getJSONArray("weather").getJSONObject(0).getString("main"));
            descriptionWeatherTextView.setText(myHeaders[1] + weatherData.getJSONArray("weather").getJSONObject(0).getString("description"));
            temperatureTextView.setText(myHeaders[2] + df.format(weatherData.getJSONObject("main").getDouble("temp")));
            pressureTextView.setText(myHeaders[3] + weatherData.getJSONObject("main").getInt("pressure"));
        }
        catch (Exception e) { // input was invalid
            Toast.makeText(this, "City not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        cityTextView.setText(city);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = findViewById(R.id.submitButton);
        cityEditText = findViewById(R.id.cityInputEditText);
        mainWeatherTextView = findViewById(R.id.mainWeatherTextView);
        descriptionWeatherTextView = findViewById(R.id.descriptionWeatherTextView);
        cityTextView = findViewById(R.id.cityTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        pressureTextView = findViewById(R.id.pressureTextView);


    }
}