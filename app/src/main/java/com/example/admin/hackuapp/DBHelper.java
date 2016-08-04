package com.example.admin.hackuapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2016/08/03.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "DB1.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBJ.DBEntry.TABLE_NAME + " (" +
                    DBJ.DBEntry._ID + " INTEGER PRIMARY KEY," +
                    DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID + TEXT_TYPE + " UNIQUE" + COMMA_SEP +
                    DBJ.DBEntry.COLUMN_NAME_BOOK_ID + TEXT_TYPE + " UNIQUE"+ COMMA_SEP +
                    DBJ.DBEntry.COLUMN_NAME_MEMO + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBJ.DBEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
