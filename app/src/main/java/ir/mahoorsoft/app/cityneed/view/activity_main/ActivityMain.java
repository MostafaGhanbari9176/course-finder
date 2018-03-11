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
import android.widget.RadioButton;
import android.widget.TextView;


import ir.mahoorsoft.app.cityneed.G;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentCheckedServer;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentErrorServer;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentSelfCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search.FragmentSearch;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogTabaghe;
import ir.mahoorsoft.app.cityneed.view.purchase.ActivityPurchase;


public class ActivityMain extends AppCompatActivity implements View.OnClickListener, DialogTabaghe.OnTabagheItemClick, NavigationView.OnNavigationItemSelectedListener, PresentCheckedServer.OnPresentCheckServrer {

    boolean selfChecked = false;
    boolean publicChecked = true;

    public static Toolbar toolbar;
    TextView txtUserName;
    TextView txtProfileButton;
    View viewNavHeder;
    View viewMenu;
    LinearLayout llRadioGroup;
    RadioButton rbSelf;
    RadioButton rbOther;
    LinearLayout llNavHeder;
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout contentMain;
    public DialogProgres dialogProgres;
    boolean flag = true;
    private FragmentHome fhome = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        setContentView(R.layout.activity_main);
        init();
        checkedServer();

    }

    private void init() {

        pointers();
        profileCheck();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkedServer() {
        dialogProgres.showProgresBar();
        PresentCheckedServer presentCheckedServer = new PresentCheckedServer(this);
        presentCheckedServer.checkedServer();
    }


    private void pointers() {

        rbSelf = (RadioButton) findViewById(R.id.rbSelfMain);
        rbOther = (RadioButton) findViewById(R.id.rbOtherMain);
        llRadioGroup = (LinearLayout) findViewById(R.id.llRadioGroupMain);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewNavHeder = navigationView.inflateHeaderView(R.layout.nav_header_main);
        txtUserName = (TextView) viewNavHeder.findViewById(R.id.txtUserName_menu);
        txtProfileButton = (TextView) viewNavHeder.findViewById(R.id.txtProfileButton_menu);
        llNavHeder = (LinearLayout) viewNavHeder.findViewById(R.id.navHederMain);
        contentMain = (FrameLayout) findViewById(R.id.contentMain);
        llNavHeder.setOnClickListener(this);
        rbOther.setOnClickListener(this);
        rbSelf.setOnClickListener(this);
        // txtProfileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.navHederMain:
                acountCheck();
                break;
            case R.id.rbOtherMain:
                if (!publicChecked) {
                    publicChecked = true;
                    selfChecked = false;
                    showCourseMode();
                }

                break;
            case R.id.rbSelfMain:
                if (!selfChecked) {
                    selfChecked = true;
                    publicChecked = false;
                    showCourseMode();
                }
                break;

        }

    }


    private void showCourseMode() {
        if (rbSelf.isChecked()) {
            fhome = null;
            replaceContentWith(new FragmentSelfCourse());
        } else {
            fhome = new FragmentHome();
            replaceContentWith(fhome);
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
                toolbar.setVisibility(View.VISIBLE);
                fhome = new FragmentHome();
                replaceContentWith(fhome);
                flag = true;
                break;
            case R.id.secorityPass:
                startActivity(ActivityPurchase.class);
                break;
            case R.id.btnMap_Menu:
                fhome = null;
                toolbar.setVisibility(View.GONE);
                replaceContentWith(new FragmentMap());
                flag = false;
                break;
            case R.id.btnSearch_Menu:
                fhome = null;
                toolbar.setVisibility(View.GONE);
                replaceContentWith(new FragmentSearch());
                flag = false;
                break;
            case R.id.grouping:

                new DialogTabaghe(this, this).Show();
                flag = false;
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkPass(){
//        if(Pref.saveStringValue(PrefKey.secorityPass,""))
    }

    private void showPassDialog(){}

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!flag) {
            toolbar.setVisibility(View.VISIBLE);
            fhome = new FragmentHome();
            replaceContentWith(fhome);

        } else {

            super.onBackPressed();
        }
    }

    private void acountCheck() {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            startActivity(ActivityProfile.class);

        } else {
            startActivity(ActivityAcountConfirm.class);
        }
    }

    private void profileCheck() {

        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 0 || !(Pref.getBollValue(PrefKey.IsLogin, false))) {
            llRadioGroup.setVisibility(View.GONE);
        } else {
            rbOther.setEnabled(true);
        }
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {

            if (Pref.getStringValue(PrefKey.userName, "").length() != 0) {
                txtUserName.setText(Pref.getStringValue(PrefKey.userName, ""));
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

    @Override
    public void serverChecked(boolean online) {
        dialogProgres.closeProgresBar();
        if (online) {
            toolbar.setVisibility(View.VISIBLE);
            rbOther.setChecked(true);
            fhome = new FragmentHome();
            replaceContentWith(fhome);
        } else {
            fhome = null;
            toolbar.setVisibility(View.GONE);
            replaceContentWith(new FragmentErrorServer());
        }
    }


    @Override
    public void tabagheInf(String name, int id) {
        if (fhome != null) {
            fhome.queryForCourses(id);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            fhome = new FragmentHome();
            replaceContentWith(fhome);
            fhome.queryForCourses(id);
        }
    }
}
