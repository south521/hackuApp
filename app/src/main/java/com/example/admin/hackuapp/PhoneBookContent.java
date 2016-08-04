package com.example.admin.hackuapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/08/04.
 */
public class PhoneBookContent {
    public static final List<PhoneBookItem> ITEMS = new ArrayList<PhoneBookItem>();

    public static final Map<String, PhoneBookItem> ITEM_MAP = new HashMap<String, PhoneBookItem>();


    public PhoneBookContent(){
    }



    public List<PhoneBookItem> getItems(){
        return ITEMS;
    }

    public Map<String, PhoneBookItem> ITEM_MAP(){
        return ITEM_MAP;
    }

    public static void addItem(PhoneBookItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class PhoneBookItem{
        public  String id;
        public  String name;
        public  String phoneNumber;
        public  String belongs;
        public  String email;

        public PhoneBookItem(String id, String name, String phoneNumber, String email, String belongs) {
            this.id = id;
            this.name = name;
            this.belongs = belongs;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
