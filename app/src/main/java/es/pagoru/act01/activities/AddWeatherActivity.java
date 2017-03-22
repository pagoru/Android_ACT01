package es.pagoru.act01.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import es.pagoru.act01.R;
import es.pagoru.act01.activities.WeatherCityListActivity;
import es.pagoru.act01.utils.WeatherApi;
import es.pagoru.act01.utils.city.City;
import es.pagoru.act01.utils.city.CityDataSource;

public class AddWeatherActivity extends Activity {

    private String city_name;

    private CityDataSource cityDataSource;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weather);

        setTitle("Afegir ciutat");

        findViewById(R.id.weather_add_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city_name = ((TextView)findViewById(R.id.weather_add_ciudad)).getText().toString();

                context = view.getContext();
                cityDataSource = new CityDataSource(view.getContext());
                new AsyncFetch().execute(city_name);
            }
        });

        findViewById(R.id.weather_add_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), WeatherCityListActivity.class));
            }
        });
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(AddWeatherActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tCarregant...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return WeatherApi.getCityJsonString(params);
        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();
            if(result.equalsIgnoreCase("unsuccessful")){
                Toast.makeText(context, "Ciutat invalida...", Toast.LENGTH_LONG).show();
                return;
            }
            try{
                cityDataSource.addCity(new City(city_name));
                startActivity(new Intent(context, WeatherCityListActivity.class));

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
