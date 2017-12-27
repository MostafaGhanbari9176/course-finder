package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm.ActivityPhoneConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search.FragmentSearch;


public class ActivityMain extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    Toolbar t;
    TextView txtUserName;
    TextView txtProfileButton;
    View viewMenu;
    LinearLayout llNavHeder;
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout contentMain;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        setContentView(R.layout.activity_main);

        replaceContentWith(new FragmentHome());

        pointers();
        profileCheck();
        setSupportActionBar(t);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void pointers() {
        t = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewMenu = navigationView.inflateHeaderView(R.layout.nav_header_main);
        txtUserName = (TextView) viewMenu.findViewById(R.id.txtUserName_menu);
        txtProfileButton = (TextView) viewMenu.findViewById(R.id.txtProfileButton_menu);
        llNavHeder = (LinearLayout) viewMenu.findViewById(R.id.navHederMain);
        contentMain = (FrameLayout) findViewById(R.id.contentMain);
        llNavHeder.setOnClickListener(this);
       // txtProfileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.navHederMain:
                acountCheck();
                break;

        }

    }

    public static void replaceContentWith(Fragment fragment) {

        G.activity.getSupportFragmentManager()
                .beginTransaction().replace(R.id.contentMain, fragment)
                .commit();
    }

    private void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.btnHome_Menu:
                replaceContentWith(new FragmentHome());
                flag = true;
                break;
            case R.id.btnMap_Menu:
                replaceContentWith(new FragmentMap());
                flag = false;
                break;
            case R.id.btnSearch_Menu:
                replaceContentWith(new FragmentSearch());
                flag = false;
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!flag) {

            replaceContentWith(new FragmentHome());

        } else {

            super.onBackPressed();
        }
    }

    private void acountCheck() {
        if (Pref.getStringValue(PrefKey.phone, "").length() != 0) {
            startActivity(ActivityProfile.class);

        } else {
            startActivity(ActivityPhoneConfirm.class);
        }
    }

    private void profileCheck() {

        if (Pref.getStringValue(PrefKey.phone, "").length() != 0) {
            if (Pref.getStringValue(PrefKey.userName, "").length() != 0 || Pref.getStringValue(PrefKey.userFamily, "").length() != 0) {
                txtUserName.setText(Pref.getStringValue(PrefKey.userName, "") + " " + Pref.getStringValue(PrefKey.userFamily, ""));
                txtProfileButton.setText("پروفایل");
            } else {
                txtUserName.setText(Pref.getStringValue(PrefKey.phone, ""));
                txtProfileButton.setText("پروفایل");
            }
        } else {
            txtUserName.setText("کاربر مهمان");
            txtProfileButton.setText("ثبت نام-ورود");
        }
    }

    @Override
    protected void onResume() {
        G.activity = this;
        G.context = this;
        profileCheck();
        super.onResume();
    }
}
