package com.ipda3.bankeldam.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import static com.ipda3.bankeldam.helper.Constants.SHARED_KEY_DIALOG;
import static com.ipda3.bankeldam.helper.Constants.SHARED_PREF;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = SHARED_PREF;
    private static final String KEY_DIALOG = SHARED_KEY_DIALOG;

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }



    public void setKEY_DIALOG(String restaurantId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DIALOG, restaurantId);
        editor.apply();
    }
    public String getKEY_DIALOG() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DIALOG, null);

    }

}