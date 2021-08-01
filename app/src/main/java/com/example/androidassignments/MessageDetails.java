package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MessageDetails extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MessageDetails";
    String message;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        if(savedInstanceState == null){
            MessageFragment msgFragment = new MessageFragment();
            Bundle mInfo = new Bundle();
            Intent intent = getIntent();

            message = intent.getStringExtra("message");
            id = intent.getIntExtra("id", 0);

            mInfo.putInt("id", id);
            mInfo.putString("message", message);

            msgFragment.setArguments(mInfo);
            FragmentTransaction ft =
                    getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.message_details, msgFragment);
            ft.commit();
            Log.i(ACTIVITY_NAME, "Started Message Frame");
        }
    }

    public void deleteMessage(View v){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("id", id);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}