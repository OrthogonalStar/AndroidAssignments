package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private static final int pic_id = 333;
    ImageButton img;
    Switch sw;
    CheckBox chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_list_items);

        img = (ImageButton) findViewById(R.id.image_button);
        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, pic_id);
            }
        });

        sw = (Switch) findViewById(R.id.switch_test);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text;
                int duration;
                if (isChecked){
                    text = "Switch is On";
                    duration = Toast.LENGTH_SHORT;
                } else{
                    text = "Switch is Off";
                    duration = Toast.LENGTH_LONG;
                }
                Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                toast.show();
            }
        });

        chk = (CheckBox) findViewById(R.id.check_test);
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog_message)

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                            }
                        })
                        .show();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == pic_id) {
            Bitmap picture = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(picture);
        }
    }
}