package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "items.db";
    private static final Integer VERSION_NUM = 2;

    public static final String TABLE_NAME = "messages";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME +
            "(" + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE +
            " text not null);";

    public ChatDataBaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        onCreate(db);
    }

    public void deleteMessage(SQLiteDatabase db, long id){
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }
}
