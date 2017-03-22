package es.pagoru.act01.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pablo on 22/03/2017.
 */

public class WeatherApi {

    public static String getCityJsonString(String... params){
        HttpURLConnection conn;
        URL url = null;

        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?units=metric&APPID=105eacf379fe0716301eac91c48fa524&q=" + params[0]);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setDoOutput(true);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return e1.toString();
        }

        try {

            int response_code = conn.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {

                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return (result.toString());

            } else {
                return ("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            conn.disconnect();
        }
    }

}
