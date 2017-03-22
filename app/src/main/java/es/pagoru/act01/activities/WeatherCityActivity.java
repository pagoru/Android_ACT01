package es.pagoru.act01.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import es.pagoru.act01.R;
import es.pagoru.act01.utils.WeatherApi;

public class WeatherCityActivity extends Activity {

    private String city_name;
    TextView celsius_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city);

        city_name = getIntent().getStringExtra("city");
        setTitle(city_name);

        celsius_text = (TextView) findViewById(R.id.weather_celsius);

        TextView name_text = (TextView) findViewById(R.id.weather_name);
        name_text.setText(city_name);

        new AsyncFetch().execute(city_name);
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(WeatherCityActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tCargando...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return WeatherApi.getCityJsonString(params);
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject json = new JSONObject(result);    // create JSON obj from string
                JSONObject json2 = json.getJSONObject("main");

                String txt = json2.getDouble("temp") + "ºC";
                celsius_text.setText(txt);
            }catch (Exception e){
                e.printStackTrace();
            }
            pdLoading.dismiss();
        }

    }
}
