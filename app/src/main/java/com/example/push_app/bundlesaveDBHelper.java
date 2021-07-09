package com.example.push_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bundlesaveDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public bundlesaveDBHelper(@Nullable Context context) {
        super(context, "BundleSaveDB", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SaveSQL = "create table BundleSave(" +
                "_id integer primary key autoincrement," +
                "data text)";

        db.execSQL(SaveSQL);
        SaveSQL = "Insert into BundleSave(data) values ('dataS')";
        db.execSQL(SaveSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table BundleSave");
            onCreate(db);
        }
    }
}