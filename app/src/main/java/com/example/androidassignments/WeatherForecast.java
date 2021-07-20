package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "WeatherForecast";
    ProgressBar progressBar;
    TextView currentTemp, minTemp, maxTemp, cityName;
    ImageView weatherImage;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        Intent intent = getIntent();
        city = intent.getStringExtra("city");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        currentTemp = findViewById(R.id.current_temp);
        minTemp = findViewById(R.id.min_temp);
        maxTemp = findViewById(R.id.max_temp);
        cityName = findViewById(R.id.city_name);

        weatherImage = findViewById(R.id.weather_image);

        ForecastQuery query = new ForecastQuery();
        query.execute();
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min, max, current;
        Bitmap weatherPic;

        @Override
        protected String doInBackground(String... strings){

            try{
                URL apiURL = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + ",ca&APPID=5c50f2c292da75d78576f463f80352a1&mode=xml&units=metric");
                HttpsURLConnection conn = (HttpsURLConnection) apiURL.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream in = conn.getInputStream();

                try{
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;
                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT){
                        if(parser.getEventType() == XmlPullParser.START_TAG){
                            if(parser.getName().equals("temperature")){
                                current = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);

                            }else if(parser.getName().equals("weather")){
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME,"Found the file locally");
                                    weatherPic = BitmapFactory.decodeStream(fis);
                                }
                                else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    weatherPic = getImage(new URL(iconUrl));

                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

                                    weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME,"Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                        }
                        parser.next();
                    }
                }finally{
                    in.close();
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            return "";
        }

        public boolean fileExistance(String fileName){
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection)
                        url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return
                            BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        public void onProgressUpdate(Integer... values){
            progressBar.setProgress(values[0]);
        }

        @Override
        public void onPostExecute(String a){
            progressBar.setVisibility(View.INVISIBLE);
            cityName.setText("The current weather in " + city + ".");
            currentTemp.setText("Current temperature: " + current + "C\u00b0");
            minTemp.setText("Minimum temperature: " + min + "C\u00b0");
            maxTemp.setText("Maximum temperature: " + max + "C\u00b0");
            weatherImage.setImageBitmap(weatherPic);
        }
    }
}