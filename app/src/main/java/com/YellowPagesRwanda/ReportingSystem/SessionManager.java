package com.YellowPagesRwanda.ReportingSystem;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "AndroidLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    SessionManager(Context context){
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGEDIN,isLoggedIn);
        editor.commit();

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

}
