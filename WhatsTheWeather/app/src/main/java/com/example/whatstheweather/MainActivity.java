package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private class weatherDownloader extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) (new URL(urls[0])).openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
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
        JSONObject weatherData = null;
        try {
            weatherData = new weatherDownloader().execute("http://api.openweathermap.org/data/2.5/weather?q=J9e9ea7ed975cf747f7d5e1bd1cab8d").get();
            int y = 3;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}