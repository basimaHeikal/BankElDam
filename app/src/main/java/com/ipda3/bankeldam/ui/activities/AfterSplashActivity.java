package com.ipda3.bankeldam.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.ui.fragments.AfterSplashFragment;
import com.ipda3.bankeldam.ui.fragments.LoginFragment;
import com.ipda3.bankeldam.ui.fragments.SignFragment;

import butterknife.ButterKnife;

import static com.ipda3.bankeldam.helper.Constants.AFTER_SPLASH;
import static com.ipda3.bankeldam.helper.Constants.LOGIN;
import static com.ipda3.bankeldam.helper.Constants.SIGN;

public class AfterSplashActivity extends AppCompatActivity {


    public static final String TAG_AFTER_SPLASH = AFTER_SPLASH;
    public static final String TAG_LOGIN = LOGIN;
    public static final String TAG_SIGN = SIGN;

    public static String CURRENT_TAG = TAG_AFTER_SPLASH;


    AfterSplashFragment afterSplashFragment;
    LoginFragment loginFragment;
    SignFragment signFragment;




    private boolean shouldLoadHomeFragOnBackPress = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_splash);
        ButterKnife.bind(this);

        loadFragment();

    }

    public void loadFragment() {
        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.SplashFlContainer, fragment, CURRENT_TAG);
        fragmentTransaction.commit();
    }

    private Fragment getHomeFragment() {
        switch (CURRENT_TAG) {
            case TAG_AFTER_SPLASH:
                afterSplashFragment = new AfterSplashFragment();
                return afterSplashFragment;
            case TAG_LOGIN:
                loginFragment = new LoginFragment();
                return loginFragment;
            case TAG_SIGN:
                signFragment = new SignFragment();
                return signFragment;
            default:
                return new AfterSplashFragment();
        }
    }

    @Override
    public void onBackPressed() {

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {

            if (CURRENT_TAG == TAG_LOGIN) {
                setCurrentTag(TAG_AFTER_SPLASH);

                return;
            }
            if (CURRENT_TAG == TAG_SIGN) {
                setCurrentTag(TAG_LOGIN);
                return;
            }
        }

        super.onBackPressed();
    }

    private void setCurrentTag(String currentTag) {
        CURRENT_TAG = currentTag;
        loadFragment();
    }

}
