package com.example.push_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class tagDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public tagDBHelper(@Nullable Context context) {
        super(context, "tagDB", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SaveSQL = "create table tagsave(" +
                "_id integer primary key autoincrement," +
                "tagname text," +
                "tagdate text)";

        db.execSQL(SaveSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table tagsave");
            onCreate(db);
        }
    }
}
