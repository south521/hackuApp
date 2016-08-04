package com.example.admin.hackuapp;

import android.provider.BaseColumns;

/**
 * Created by admin on 2016/08/03.
 */
public final class DBJ {
    public DBJ () {};
    public static abstract class DBEntry implements BaseColumns {
        public static final String TABLE_NAME = "list";
        //public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_MS_PROFILE_ID = "msid";
        public static final String COLUMN_NAME_BOOK_ID = "bookid";
        public static final String COLUMN_NAME_MEMO = "memo";
    }
}
