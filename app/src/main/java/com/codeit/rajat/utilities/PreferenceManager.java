package com.codeit.rajat.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.codeit.rajat.vidbuddy.R;

/**
 * Created by rajat on 4/11/17.
 */

public class PreferenceManager {

    private Context context;
    private SharedPreferences preferences;

    public PreferenceManager(Context context){
        this.context = context;
        getSharedPreferences();
    }

    private void getSharedPreferences(){
        preferences = context.getSharedPreferences(context.getString(R.string.my_pref),Context.MODE_PRIVATE);
    }

    public void writePreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.my_pref_key),"INIT_OK");
        editor.commit();
    }

    public boolean checkPreferences(){
        boolean stat = false;
        if(preferences.getString(context.getString(R.string.my_pref_key),"null").equals("null")){
            stat = false;
        }else {
            stat = true;
        }
        return stat;
    }

    public void clearPreferences(){
        preferences.edit().clear().commit();
    }
}
