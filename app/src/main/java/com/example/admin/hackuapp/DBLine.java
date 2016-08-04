package com.example.admin.hackuapp;

public class DBLine {
    //public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public final String MS_PROFILE_ID;
    public final String BOOK_ID;
    public final String MEMO;

    DBLine(String ms_profile_id, String book_id, String memo) {
        this.MS_PROFILE_ID = ms_profile_id;
        this.BOOK_ID = book_id;
        this.MEMO = memo;
    }
}
