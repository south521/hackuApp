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
    public static List<PhoneBookItem> ITEMS ;

    public static Map<String, PhoneBookItem> ITEM_MAP;


    public PhoneBookContent(){
        ITEMS = new ArrayList<PhoneBookItem>();
        ITEM_MAP = new HashMap<String, PhoneBookItem>();
        reset();
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

    public static void reset(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static class PhoneBookItem{
        public  String id;
        public  String name;
        public  String phoneNumber;
        public  String company;
        public  String depart;
        public  String posit;
        public  String email;

        public PhoneBookItem(String id, String name, String phoneNumber, String email, String company, String depart, String posit) {
            this.id = id;
            this.name = name;
            this.company = company;
            this.depart = depart;
            this.company = company;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.posit = posit;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
