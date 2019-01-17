package com.ipda3.bankeldam.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ipda3.bankeldam.ui.activities.AfterSplashActivity;

public class HelperMethod {
    private static HelperMethod mInstance;
    private static Context mCtx;

    private HelperMethod(Context context) {
        mCtx = context;
    }

    public static synchronized HelperMethod getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HelperMethod(context);
        }
        return mInstance;
    }

    public void loadSplashFragments(Context context,String currentTag){
        AfterSplashActivity.CURRENT_TAG = currentTag;
        ((AfterSplashActivity)context).loadFragment();
    }

    public void callToast(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
