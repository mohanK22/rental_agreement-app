package in.aartimkarande.rentalagreement.myrentalagreement.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.admin.AdminMainActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.AboutFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.ContactFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.CreateAgreementFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.HomeFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.LegalNoticeFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.LogoutFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.MyAccountFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.NotificationFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.OurServicesFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.PendingAgreementFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.RateAppFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.RecommendAppFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.SettingFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.SocialMediaFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.fragment.ViewAgreementFragment;
import in.aartimkarande.rentalagreement.myrentalagreement.other.CircleTransform;

public class UserMainActivity extends AppCompatActivity {

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_CREATE_AGREEMENT= "create_agreement";
    private static final String TAG_VIEW_AGREEMENT = "view_agreement";
    private static final String TAG_PENDING_AGREEMENT = "pending_agreement";
    private static final String TAG_MY_ACCOUNT = "my_account";
    private static final String TAG_ABOUT = "about";
    private static final String TAG_OUR_SERVICES = "our_services";
    private static final String TAG_CONTACT = "contact";
    private static final String TAG_RATE_APP = "rate_app";
    private static final String TAG_RECOMMEND_APP = "recommend_app";
    private static final String TAG_SOCIAL_MEDIA = "social_media";
    private static final String TAG_LEGAL_NOTICE = "legal_notice";
    private static final String TAG_LOGOUT = "logout";
    /*private static final String TAG_NOTIFICATION = "notification";
    private static final String TAG_SETTING = "settings"*/;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    public static String CURRENT_TAG = TAG_HOME;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    private ImageView imgProfile;
    private TextView txtName;
    private TextView txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        SharedPreferences sharedPreferences = UserMainActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(MyAppSharedPreferences.USERNAME,null);

        if(username == null){
            startActivity(new Intent(UserMainActivity.this, LoginActivity.class));
        }else if(username.equalsIgnoreCase("admin")){
            startActivity(new Intent(UserMainActivity.this, AdminMainActivity.class));
        }


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        /*fab =  findViewById(R.id.fab);*/

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName =  navHeader.findViewById(R.id.user_name);
        txtWebsite =  navHeader.findViewById(R.id.website);
        imgNavHeaderBg =  navHeader.findViewById(R.id.img_header_bg);
        imgProfile =  navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_items_usrs_activity_titles);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Send message activity will open", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    private void loadHomeFragment() {

        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            /*toggleFab();*/
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        /*toggleFab();*/

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();

    }

    private Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 1:
                // create agreement fragment
                CreateAgreementFragment createAgreementFragment = new CreateAgreementFragment();
                return createAgreementFragment;

            case 2:
                // view agreement fragment
                ViewAgreementFragment viewAgreementFragment = new ViewAgreementFragment();
                return viewAgreementFragment;

            case 3:
                // pending agreement fragment
                PendingAgreementFragment pendingAgreementFragment = new PendingAgreementFragment();
                return pendingAgreementFragment;

            case 4:
                // my account fragment
                MyAccountFragment myAccountFragment = new MyAccountFragment();
                return myAccountFragment;

            case 5:
                // notification fragament
                /*NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;*/
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;

            /*case 6:
                // setting fragment
                *//*SettingFragment settingFragment = new SettingFragment();
                return settingFragment;*//*
                OurServicesFragment ourServicesFragment = new OurServicesFragment();
                return ourServicesFragment;*/

            case 6:
                // about fragment
                /*AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;*/
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;

            case 7:
                // rate app fragment
                RateAppFragment rateAppFragment = new RateAppFragment();
                return rateAppFragment;

            case 8:
                // recommend app fragment
                RecommendAppFragment recommendAppFragment = new RecommendAppFragment();
                return recommendAppFragment;

            case 9:
                // contact fragment
                /*ContactFragment contactFragment = new ContactFragment();
                return contactFragment;*/
                SocialMediaFragment socialMediaFragment = new SocialMediaFragment();
                return socialMediaFragment;

            case 10:
                // social media
                /*SocialMediaFragment socialMediaFragment = new SocialMediaFragment();
                return socialMediaFragment;*/
                LegalNoticeFragment legalNoticeFragment = new LegalNoticeFragment();
                return legalNoticeFragment;

            case 11:
                // legal notice fragment
                /*LegalNoticeFragment legalNoticeFragment = new LegalNoticeFragment();
                return legalNoticeFragment;*/
                logout();

            //case 13:
                // logout fragment
                /*LogoutFragment logoutFragment = new LogoutFragment();
                return logoutFragment;*/
                /*logout();*/

            default:
                return new HomeFragment();
        }

    }

    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.contract);
        alertDialogBuilder.setTitle(R.string.logout_title);
        alertDialogBuilder.setMessage(R.string.logout_msg);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Getting out shared preference
                        SharedPreferences preferences = getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Putting the value false for loggedin
                        editor.putBoolean(MyAppSharedPreferences.LOGGEDIN, false);

                        //Putting blank value to the username
                        editor.putString(MyAppSharedPreferences.USERNAME, null);
                        editor.putString(MyAppSharedPreferences.USR_ID, null);

                        //saving the shared preference
                        editor.commit();

                        //Start login activity
                        Intent intentLogin = new Intent(UserMainActivity.this, LoginActivity.class);
                        startActivity(intentLogin);


                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(UserMainActivity.this, UserMainActivity.class));
                    }
                }
        );


        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        return;
    }

    /*private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }*/

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_create_agreement:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CREATE_AGREEMENT;
                        break;
                    case R.id.nav_view_agreement:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_VIEW_AGREEMENT;
                        break;
                    case R.id.nav_pending_agreement:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_PENDING_AGREEMENT;
                        break;
                    case R.id.nav_my_account:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_MY_ACCOUNT;
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ABOUT;
                        break;
                    /*case R.id.our_services:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_OUR_SERVICES;
                        break;*/
                    case R.id.nav_contact:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_CONTACT;
                        break;
                    case R.id.nav_rate_us:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_RATE_APP;
                        break;
                    case R.id.nav_recommend_app:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_RECOMMEND_APP;
                        break;
                    /*case R.id.nav_contact:
                        navItemIndex = 10;
                        CURRENT_TAG = TAG_CONTACT;
                        break;*/
                    case R.id.nav_social_media:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_SOCIAL_MEDIA;
                        break;
                    case R.id.nav_legal_notice:
                        navItemIndex = 10;
                        CURRENT_TAG = TAG_LEGAL_NOTICE;
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 11;
                        CURRENT_TAG = TAG_LOGOUT;
                        break;
                    /*case R.id.nav_notification:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;*/
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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }

        }

        if(navItemIndex == 0){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.confirm_exit);
            builder.setMessage(R.string.confirm_msg);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
            builder.setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

        /*super.onBackPressed();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        /*if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.user_menu_drawer, menu);
        }*/

        // when fragment is notifications, load the menu created for notifications
        /*if (navItemIndex == 5) {
            getMenuInflater().inflate(R.menu.notification, menu);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        /*if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }*/

        // user is in notifications fragment
        // and selected 'Clear All'



        return super.onOptionsItemSelected(item);
    }

    private void loadNavHeader() {

        SharedPreferences sharedPreferences = UserMainActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(MyAppSharedPreferences.USERNAME,null);

        if(username == null){
            startActivity(new Intent(UserMainActivity.this, LoginActivity.class));
        }

        // name, website
        txtName.setText(username);
        txtWebsite.setText(APIURLLists.WEBSITE);

        // loading header background image
        Glide.with(this).load(R.drawable.cover_img)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(FinalValues.navProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(5).setActionView(R.layout.notification_dot);


    }



}
