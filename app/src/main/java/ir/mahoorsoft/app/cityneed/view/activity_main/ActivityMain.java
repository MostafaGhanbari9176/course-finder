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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentCheckedServer;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm.ActivityPhoneConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentErrorServer;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentSelfCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search.FragmentSearch;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogTabaghe;


public class ActivityMain extends AppCompatActivity implements View.OnClickListener, DialogTabaghe.OnTabagheItemClick, NavigationView.OnNavigationItemSelectedListener, PresentCheckedServer.OnPresentCheckServrer {
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
                showCourseMode();
                break;
            case R.id.rbSelfMain:
                showCourseMode();
                break;

        }

    }

    private void showCourseMode() {
        if (rbSelf.isChecked())
            replaceContentWith(new FragmentSelfCourse());
        else
            replaceContentWith(new FragmentHome());
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
            case R.id.tabaghe:
//                Intent intent = new Intent(this, ActivityTabagheList.class);
//                startActivity(intent);
                new DialogTabaghe(this,this).Show();
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
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            startActivity(ActivityProfile.class);

        } else {
            startActivity(ActivityPhoneConfirm.class);
        }
    }

    private void profileCheck() {

        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 0 || !(Pref.getBollValue(PrefKey.IsLogin, false))) {
            llRadioGroup.setVisibility(View.GONE);
        } else {
            rbOther.setEnabled(true);
        }
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {

            if (Pref.getStringValue(PrefKey.userName, "").length() != 0 ) {
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
            replaceContentWith(new FragmentHome());
        } else {

            toolbar.setVisibility(View.GONE);
            replaceContentWith(new FragmentErrorServer());
        }
    }


    @Override
    public void tabagheInf(String name, int id) {
        Toast.makeText(this, id+" ,,, "+name, Toast.LENGTH_SHORT).show();
    }
}
