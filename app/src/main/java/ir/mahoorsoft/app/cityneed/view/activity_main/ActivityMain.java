package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.view.ActivityAboutUs;
import ir.mahoorsoft.app.cityneed.view.RandomColor;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list.FragmentGroupingList;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentSelfCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search.FragmentSearch;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogGrouping;


public class ActivityMain extends AppCompatActivity implements View.OnClickListener, DialogGrouping.OnTabagheItemClick, NavigationView.OnNavigationItemSelectedListener {

    boolean selfChecked = false;
    boolean publicChecked = true;
    Toolbar toolbar;
    TextView txtUserName;
    TextView txtProfileButton;
    View viewNavHeder;
    LinearLayout llRadioGroup;
    RadioButton rbSelf;
    RadioButton rbOther;
    LinearLayout llNavHeder;
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout contentMain;
    public DialogProgres dialogProgres;
    private FragmentHome fhome = null;
    BottomNavigationView navDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        setContentView(R.layout.activity_main);
        init();
        rbOther.setChecked(true);
        fhome = new FragmentHome();
        replaceContentWith(fhome);
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

    private void pointers() {

        navDown = (BottomNavigationView) findViewById(R.id.bottomNav_down_Home);
        navDown.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_tel));
        setNavigationItemListener();
        disableShiftModeNavigation(navDown);
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

    }

    private void setNavigationItemListener() {

        navDown.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeNaveDownHome:
                        if (fhome == null) {
                            navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_tel));
                            fhome = new FragmentHome();
                            replaceContentWith(fhome);
                        }
                        return true;

                    case R.id.searchNanDownHome:
                        fhome = null;
                        navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                        replaceContentWith(new FragmentSearch());
                        return true;

                    case R.id.groupingNavDownHome:
                        fhome = null;
                        navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                        replaceContentWith(new FragmentGroupingList());
                        return true;

                    case R.id.teacherListNavDownHome:
                        fhome = null;
                        navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                        replaceContentWith(new FragmentTeacherList());
                        return true;

                    case R.id.profileNavDownHome:
                        acountCheck();
                        return true;
                }
                return false;
            }

        });

    }

    private void disableShiftModeNavigation(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
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

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.btnHomeMenu:
                if (fhome == null) {
                    fhome = new FragmentHome();
                    replaceContentWith(fhome);
                }
                break;
            case R.id.btnMapMenu:
                fhome = null;
                replaceContentWith(new FragmentMap());
                break;
            case R.id.btnSearchMenu:
                fhome = null;
                replaceContentWith(new FragmentSearch());
                break;
            case R.id.btnSabtenamListMenu:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySabtenamList.class);
                else
                    Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSmsBoxMenu:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySmsBox.class);
                else
                    Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnGroupingMenu:
                new DialogGrouping(this, this).Show();
                break;
            case R.id.btnAboutUsMenu:
                starterActivity(ActivityAboutUs.class);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkPass() {
//        if(Pref.saveStringValue(PrefKey.secorityPass,""))
    }

    private void showPassDialog() {
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fhome == null) {
            fhome = new FragmentHome();
            replaceContentWith(fhome);
            rbOther.setChecked(true);
            publicChecked = true;
            selfChecked = false;
        } else {

            super.onBackPressed();
        }
    }

    private void acountCheck() {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            starterActivity(ActivityProfile.class);

        } else {
            starterActivity(ActivityAcountConfirm.class);
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
    public void tabagheInf(String name, int id) {
        //   Toast.makeText(this, name + "     " + id, Toast.LENGTH_SHORT).show();
        if (fhome != null) {
            fhome.queryForCourses(id);
        } else {
            fhome = new FragmentHome();
            replaceContentWith(fhome);
            fhome.id = id;
        }
    }
}
