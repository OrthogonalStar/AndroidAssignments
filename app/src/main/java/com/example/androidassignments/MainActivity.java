package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidassignments.assignment2.TestToolbar;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";
    Button submit, chat, toolbar;

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
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(new Intent(MainActivity.this, TestToolbar.class));
                startActivityForResult(intent, 10);
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