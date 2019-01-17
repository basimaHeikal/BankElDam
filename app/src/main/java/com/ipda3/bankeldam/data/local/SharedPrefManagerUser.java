package com.ipda3.bankeldam.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.ipda3.bankeldam.data.model.register.Client;

import static com.ipda3.bankeldam.helper.Constants.SHARED_PREF_USER;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_API_TOKEN;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_BIRTH;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_BLOOD_TYPE;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_CITY_ID;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_EMAIL;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_LAST_DONATE;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_NAME;
import static com.ipda3.bankeldam.helper.Constants.SHARED_USER_PHONE;

public class SharedPrefManagerUser {

    //the constants
    private static final String SHARED_PREF_NAME = SHARED_PREF_USER;

    private static final String KEY_API_TOKEN = SHARED_USER_API_TOKEN;

    private static final String KEY_NAME = SHARED_USER_NAME;
    private static final String KEY_EMAIL = SHARED_USER_EMAIL;
    private static final String KEY_BIRTH_DATE = SHARED_USER_BIRTH;
    private static final String KEY_CITY_ID = SHARED_USER_CITY_ID;
    private static final String KEY_PHONE = SHARED_USER_PHONE;
    private static final String KEY_LAST_DONATION_DATE = SHARED_USER_LAST_DONATE;
    private static final String KEY_BLOOD_TYPE = SHARED_USER_BLOOD_TYPE;



    private static SharedPrefManagerUser mInstance;
    private static Context mCtx;

    private SharedPrefManagerUser(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerUser getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerUser(context);
        }
        return mInstance;
    }

    public void setKeyApiToken(String  orderSell) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_API_TOKEN, orderSell);
        editor.apply();
    }
    public String getKeyApiToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_API_TOKEN, null);
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_API_TOKEN, null) != null;
    }

    //this method will store the user data in shared preferences
    public void setUser(Client user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_BIRTH_DATE, user.getBirthDate());
        editor.putString(KEY_CITY_ID, user.getCityId());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_LAST_DONATION_DATE, user.getDonationLastDate());
        editor.putString(KEY_BLOOD_TYPE, user.getBloodType());

        editor.apply();
    }



    //this method will give the logged in user
    public Client getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Client(
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_BIRTH_DATE, null),
                sharedPreferences.getString(KEY_CITY_ID, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_LAST_DONATION_DATE, null),
                sharedPreferences.getString(KEY_BLOOD_TYPE, null)
        );
    }

    //this method will logout the user and direct him to the logo activity
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

