package com.firstexample.emarkova.session11;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class DataStorage {
    public static final String STORAGE = "mystorage";
    public static final String KEY = "key";
    private Context context;
    public DataStorage(Context context){
        this.context = context;
    }

    public void setData(String value){
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY);
        editor.putString(KEY, value);
        editor.commit();
    }
    public String getData(){
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return preferences.getString(KEY,null);
    }
}
