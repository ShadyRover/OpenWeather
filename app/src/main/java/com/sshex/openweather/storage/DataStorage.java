package com.sshex.openweather.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataStorage {

    private static final String CITY = "DataStorage.City";
    private static final String UNITS = "DataStorage.UNITS";


    private static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean saveCity(String city,Context context){
        return getPreferences(context).edit().putString(CITY, city).commit();
    }

    public static String getCity(Context context){
        return getPreferences(context).getString(CITY, "");
    }
    public static boolean saveUnits(String metrics,Context context){
        return getPreferences(context).edit().putString(UNITS, metrics).commit();
    }

    public static String getUnits(Context context){
        return getPreferences(context).getString(UNITS, "metric");
    }

}
