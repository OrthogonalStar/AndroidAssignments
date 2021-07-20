package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidassignments.assignment2.TestToolbar;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";
    Button submit, chat, toolbar, weather;
    Spinner dropdown;
    String city = "Ottawa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(MainActivity.this, ListItemsActivity.class));
                startActivityForResult(intent, 10);
            }
        });

        chat = (Button) findViewById(R.id.chat_button);
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(new Intent(MainActivity.this, ChatWindow.class));
                startActivityForResult(intent, 10);
            }
        });

        toolbar = (Button) findViewById(R.id.toolbar_button);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Toolbar");
                Intent intent = new Intent(new Intent(MainActivity.this, TestToolbar.class));
                startActivityForResult(intent, 10);
            }
        });

        weather = (Button) findViewById(R.id.weather_button);
        weather.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
                Intent intent = new Intent(new Intent(MainActivity.this, WeatherForecast.class));
                intent.putExtra("city", city);
                startActivityForResult(intent, 10);
            }
        });

        dropdown = findViewById(R.id.city_list);
        String[] cities = new String[]{"Ottawa", "Toronto", "Vancouver", "Calgary"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                Log.i(ACTIVITY_NAME, "User selected the city " + selection);
                city = selection;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data) {

        super.onActivityResult(requestCode, responseCode, data);

        if(requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
        }
        if(responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            CharSequence text = "ListItemsActivity passed: " + messagePassed;
            Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}