package com.example.notesapp.core;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceWrapper {
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public PreferenceWrapper(Context context){
        preferences = context.getSharedPreferences(
                "com.example.notesapp.PREFERENCE_FILE_KEY" , Context.MODE_PRIVATE
        );
        editor = preferences.edit();
    }

    public void putStringOnPreference(String key, String stringToSave){
        editor.putString(key, stringToSave);
        editor.apply();
    }

    public String getStringFromPreference(String key, String defaultValue){
        return preferences.getString(key, defaultValue);
    }
}
