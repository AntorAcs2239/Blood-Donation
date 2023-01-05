package com.example.blooddonation;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedClass {


    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public static final String ISLOGIN = "islogin";
    public static final String NAME = "fullname";
    public static final String PHONE = "phone";
    public static final String AREA = "area";
    public static final String DISTRICT = "district";
    public static final String LASTBLOODDONATION = "lastdonation";
    public static final String STATUS = "status";
    public static final String BLOODG = "bloodg";
    public static final String NUMOFDONATE = "numofdonation";

    SharedClass(Context context) {
        sharedPreferences = context.getSharedPreferences("userloginsession", Context.MODE_PRIVATE);
        this.context = context;
        editor = sharedPreferences.edit();
    }
    public void createloginsession(String name, String phone, String area, String district, String status, String lastdonate, String numofdonate, String bloodg) {
        editor.putBoolean(ISLOGIN, true);
        editor.putString(NAME, name);
        editor.putString(PHONE, phone);
        editor.putString(AREA, area);
        editor.putString(DISTRICT, district);
        editor.putString(STATUS, status);
        editor.putString(LASTBLOODDONATION, lastdonate);
        editor.putString(NUMOFDONATE, numofdonate);
        editor.putString(BLOODG, bloodg);
        editor.commit();
    }
    public HashMap<String, String> getuserdetails() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(NAME, sharedPreferences.getString(NAME, " "));
        map.put(PHONE,sharedPreferences.getString(PHONE," "));
        map.put(AREA,sharedPreferences.getString(AREA," "));
        map.put(DISTRICT,sharedPreferences.getString(DISTRICT," "));
        map.put(LASTBLOODDONATION,sharedPreferences.getString(LASTBLOODDONATION," "));
        map.put(STATUS,sharedPreferences.getString(STATUS," "));
        map.put(BLOODG,sharedPreferences.getString(BLOODG," "));
        map.put(NUMOFDONATE,sharedPreferences.getString(NUMOFDONATE," "));
        return map;
    }
    public boolean getuserauth() {
        if (sharedPreferences.getBoolean(ISLOGIN, false)) {
            return true;
        } else return false;
    }
//    public void logout()
//    {
//        editor.putBoolean(ISLOGIN, false);
//        editor.putString(FIRST,"first");
//        editor.commit();
//    }
}
