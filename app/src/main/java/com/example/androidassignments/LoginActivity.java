package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL_KEY = "DefaultEmail";
    Button submit;
    EditText email;
    SharedPreferences sharedPreferences;
    String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        emailText = sharedPreferences.getString(EMAIL_KEY, "email@domain.com");

        email = (EditText) findViewById(R.id.login_field);
        email.setText(emailText);
        submit = (Button) findViewById(R.id.login_button);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                emailText = email.getText().toString();
                editor.putString(EMAIL_KEY, emailText);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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
}