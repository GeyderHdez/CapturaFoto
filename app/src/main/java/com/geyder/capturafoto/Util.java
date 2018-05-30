package com.geyder.capturafoto;

import android.content.SharedPreferences;

public class Util {

    public static String getPrefs(SharedPreferences preferences, String key){
        return preferences.getString(key, "");
    }

    public static void RemovePrefs( SharedPreferences preferences, String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();;
    }
}
