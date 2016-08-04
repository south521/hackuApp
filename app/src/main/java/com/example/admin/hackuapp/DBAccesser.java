package com.example.admin.hackuapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by admin on 2016/08/04.
 */
public class DBAccesser {
    String[] proj = {
            DBJ.DBEntry._ID,
            DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID,
            DBJ.DBEntry.COLUMN_NAME_BOOK_ID,
            DBJ.DBEntry.COLUMN_NAME_MEMO
    };

    DBHelper dbh;

    public DBAccesser(Context context) {
        dbh = new DBHelper(context);
    }

    public DBLine getByMs_profile_id(String ms_profile_id) {
        SQLiteDatabase db = dbh.getReadableDatabase();
        String[] args = {ms_profile_id};
        Cursor c = db.query(
                DBJ.DBEntry.TABLE_NAME,
                proj,
                DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID + " = ?",
                args,
                null,
                null,
                null
        );

        if (c.getCount()==0) {
            c.close();
            db.close();
            return null;
        }

        c.moveToFirst();
        int msidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID);
        int bookidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_BOOK_ID);
        int memoidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_MEMO);

        DBLine dbLine = new DBLine(c.getString(msidx),c.getString(bookidx), c.getString(memoidx));
        c.close();
        db.close();
        return dbLine;
    }

    public DBLine getBybook_id(String book_id) {
        SQLiteDatabase db = dbh.getReadableDatabase();
        String[] args = {book_id};
        Cursor c = db.query(
                DBJ.DBEntry.TABLE_NAME,
                proj,
                DBJ.DBEntry.COLUMN_NAME_BOOK_ID + " = ?",
                args,
                null,
                null,
                null
        );

        if (c.getCount()==0) {
            c.close();
            db.close();
            return null;
        }

        c.moveToFirst();
        int msidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID);
        int bookidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_BOOK_ID);
        int memoidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_MEMO);

        DBLine dbLine = new DBLine(c.getString(msidx),c.getString(bookidx), c.getString(memoidx));
        c.close();
        db.close();
        return dbLine;
    }

    public DBLine[] getAll() {
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor c = db.query(
                DBJ.DBEntry.TABLE_NAME,
                proj,
                null,
                null,
                null,
                null,
                null
        );

        if (c.getCount()==0) {
            c.close();
            db.close();
            return null;
        }


        int msidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID);
        int bookidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_BOOK_ID);
        int memoidx = c.getColumnIndexOrThrow(DBJ.DBEntry.COLUMN_NAME_MEMO);

        ArrayList<DBLine> al = new ArrayList<DBLine>();

        c.moveToFirst();
        while(true) {
            al.add(new DBLine(c.getString(msidx),c.getString(bookidx), c.getString(memoidx)));
            if(c.isLast()) break;
            c.moveToNext();
        };

        c.close();
        db.close();
        return al.toArray(new DBLine[0]);
    }

    public void deleteByMs_profile_id(String ms_profile_id) {
        String selection = DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID + " = ?";
        String[] args = {ms_profile_id};
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.delete(DBJ.DBEntry.TABLE_NAME, selection, args);
        db.close();
    }

    public void deleteByBook_id(String book_id) {
        String selection = DBJ.DBEntry.COLUMN_NAME_BOOK_ID + " = ?";
        String[] args = {book_id};
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.delete(DBJ.DBEntry.TABLE_NAME, selection, args);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.delete(DBJ.DBEntry.TABLE_NAME, null, null);
        db.close();
    }

    public long put (DBLine data) {
        SQLiteDatabase db  = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBJ.DBEntry.COLUMN_NAME_MS_PROFILE_ID, data.MS_PROFILE_ID);
        values.put(DBJ.DBEntry.COLUMN_NAME_BOOK_ID, data.BOOK_ID);
        values.put(DBJ.DBEntry.COLUMN_NAME_MEMO, data.MEMO);

        long rowid = db.insert(DBJ.DBEntry.TABLE_NAME, null, values);
        db.close();
        return rowid;
    }


}
