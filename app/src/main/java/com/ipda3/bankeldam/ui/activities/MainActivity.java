package com.ipda3.bankeldam.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.view.Menu;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.ui.fragments.AboutAppFragment;
import com.ipda3.bankeldam.ui.fragments.ContactUsFragment;
import com.ipda3.bankeldam.ui.fragments.FavoritesFragment;
import com.ipda3.bankeldam.ui.fragments.HomeFragment;
import com.ipda3.bankeldam.ui.fragments.HowToUseFragment;
import com.ipda3.bankeldam.ui.fragments.MyInformationFragment;
import com.ipda3.bankeldam.ui.fragments.NotificationsSettingsFragment;
import com.ipda3.bankeldam.ui.fragments.ReportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ipda3.bankeldam.helper.Constants.ABOUT_APP;
import static com.ipda3.bankeldam.helper.Constants.CONTACT_US;
import static com.ipda3.bankeldam.helper.Constants.FAVORITES;
import static com.ipda3.bankeldam.helper.Constants.HOME;
import static com.ipda3.bankeldam.helper.Constants.HOW_TO_USE;
import static com.ipda3.bankeldam.helper.Constants.LOGOUT;
import static com.ipda3.bankeldam.helper.Constants.MY_INFORMATION;
import static com.ipda3.bankeldam.helper.Constants.NOTIFICATIONS;
import static com.ipda3.bankeldam.helper.Constants.REPORT;

public class MainActivity extends AppCompatActivity {

    // index to identify current nav menu item
    public static int navItemIndex;

    // tags used to attach the fragments
    public static final String TAG_HOME = HOME;
    public static final String TAG_MY_INFORMATION = MY_INFORMATION;
    public static final String TAG_NOTIFICATIONS = NOTIFICATIONS;
    public static final String TAG_FAVORITES = FAVORITES;
    public static final String TAG_HOW_TO_USE = HOW_TO_USE;
    public static final String TAG_REPORT = REPORT;
    public static final String TAG_CONTACT_US = CONTACT_US;
    public static final String TAG_ABOUT_APP = ABOUT_APP;
    public static final String TAG_LOGOUT = LOGOUT;

    public static String CURRENT_TAG = TAG_HOME;


    HomeFragment homeFragment;
    MyInformationFragment myInformationFragment;
    NotificationsSettingsFragment notificationsSettingsFragment;
    FavoritesFragment favoritesFragment;
    HowToUseFragment howToUseFragment;
    ReportFragment reportFragment;
    ContactUsFragment contactUsFragment;
    AboutAppFragment aboutAppFragment;

    @BindView(R.id.MainToolBar)
    Toolbar MainToolBar;
    @BindView(R.id.MainFlContainer)
    FrameLayout MainFlContainer;
    @BindView(R.id.MainNavigation)
    NavigationView MainNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(MainToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            setCurrentTag(TAG_HOME, 0, 1);
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    public void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();
            return;
        }

        // update the main content by replacing fragments
        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.MainFlContainer, fragment, CURRENT_TAG);
        fragmentTransaction.commit();

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                myInformationFragment  = new MyInformationFragment();
                return myInformationFragment;
            case 2:
                notificationsSettingsFragment  = new NotificationsSettingsFragment();
                return notificationsSettingsFragment;

            case 3:
                favoritesFragment = new FavoritesFragment();
                return favoritesFragment;
            case 4:
                howToUseFragment = new HowToUseFragment();
                return howToUseFragment;

            case 5:
                reportFragment = new ReportFragment();
                return reportFragment;

            case 6:
                contactUsFragment = new ContactUsFragment();
                return contactUsFragment;

            case 7:
                aboutAppFragment = new AboutAppFragment();
                return aboutAppFragment;

//            case 8:
//                // logout
//                foodMenuFragment = new FoodMenuFragment();
//                return foodMenuFragment;


            default:
                return new HomeFragment();
        }
    }


    private void selectNavMenu() {
        MainNavigation.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        MainNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        setCurrentTag(TAG_HOME, 0, 0);
                        break;
                    case R.id.nav_my_information:
                        setCurrentTag(TAG_MY_INFORMATION, 1, 0);
                        break;
                    case R.id.nav_notifications:
                        setCurrentTag(TAG_NOTIFICATIONS, 2, 0);
                        break;
                    case R.id.nav_favorites:
                        setCurrentTag(TAG_FAVORITES, 3, 0);
                        break;
                    case R.id.nav_how_to_use:
                        setCurrentTag(TAG_HOW_TO_USE, 4, 0);
                        break;
                    case R.id.nav_report:
                        setCurrentTag(TAG_REPORT, 5, 0);
                        break;
                    case R.id.nav_contact_us:
                        setCurrentTag(TAG_CONTACT_US, 6, 0);
                        break;
                    case R.id.nav_about_app:
                        setCurrentTag(TAG_ABOUT_APP, 7, 0);
                        break;
//                    case R.id.nav_logout:
//                        setCurrentTag(TAG_FOOD_MENU, 8, 0);
//                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, MainToolBar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_nav);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex == 1 || navItemIndex == 2 || navItemIndex == 3 || navItemIndex == 4 || navItemIndex == 5 || navItemIndex == 6 || navItemIndex == 7) {
                setCurrentTag(TAG_HOME, 0, 1);
                return;
            }

            super.onBackPressed();
        }
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){

//            if (navItemIndex == 8) {
//                getMenuInflater().inflate(R.menu.order, menu);
//            } else if (navItemIndex == 11 || navItemIndex == 12) {
//                getMenuInflater().inflate(R.menu.user_auth, menu);
//            } else {
//                getMenuInflater().inflate(R.menu.main, menu);
//            }
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();


//            if (id == R.id.menu_cart) {
//                SharedPrefManager.getInstance(FoodOrderActivity.this).setKeyTotal("CART");
//                setCurrentTag(TAG_CART, 10, 1);
//            }
//
//
//            if (id == R.id.menu_back) {
//                onBackPressed();
//            }

            return super.onOptionsItemSelected(item);
        }



        public void setCurrentTag (String currentTag,int navIndex, int type){
            if (type == 0) {
                navItemIndex = navIndex;
                CURRENT_TAG = currentTag;
            } else if (type == 1) {
                navItemIndex = navIndex;
                CURRENT_TAG = currentTag;
                loadHomeFragment();
            }
        }

}

