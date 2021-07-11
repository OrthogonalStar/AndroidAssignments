package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "ChatWindow";
    ListView lst;
    EditText txtbox;
    Button send;
    ArrayList<String> chatMessages = new ArrayList<String>();
    ChatAdapter messageAdapter;
    ChatDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_chat_window);

        lst = (ListView) findViewById(R.id.list_view);
        messageAdapter = new ChatAdapter(this);
        lst.setAdapter(messageAdapter);

        txtbox = (EditText) findViewById(R.id.edit_text);

        dbHelper = new ChatDataBaseHelper(this);

        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

        send = (Button) findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String message = txtbox.getText().toString();
                chatMessages.add(message);
                ContentValues cValues = new ContentValues();
                cValues.put(dbHelper.KEY_MESSAGE, message);
                dbWrite.insert(dbHelper.TABLE_NAME, "NullPlaceholder", cValues);

                messageAdapter.notifyDataSetChanged();
                txtbox.setText("");
            }
        });

        Cursor dbMessages = dbRead.rawQuery("SELECT * from " + dbHelper.TABLE_NAME, new String[] {});

        dbMessages.moveToFirst();

        int index = dbMessages.getColumnIndex(dbHelper.KEY_MESSAGE);

        while(!dbMessages.isAfterLast()){
            chatMessages.add(dbMessages.getString(index));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + dbMessages.getString(index));
            dbMessages.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor's column count =" + dbMessages.getColumnCount());
        for(int i=0; i < dbMessages.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, dbMessages.getColumnName(i));
        }
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
        dbHelper.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return chatMessages.size();
        }

        public String getItem(int pos){
            return chatMessages.get(pos);
        }

        public View getView(int pos, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if(pos%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(pos));

            return result;
        }
    }
}