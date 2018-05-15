package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.view.ActivityAboutUs;
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


public class ActivityMain extends AppCompatActivity implements DialogGrouping.OnTabagheItemClick {
    Toolbar toolbar;
    LinearLayout llRadioGroup;
    RadioButton rbSelf;
    RadioButton rbOther;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnMapMenu:
                fhome = null;
                replaceContentWith(new FragmentMap());
                return true;
            case R.id.btnSabtenamListMenu:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySabtenamList.class);
                else
                    Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnSmsBoxMenu:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySmsBox.class);
                else
                    Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnAboutUsMenu:
                starterActivity(ActivityAboutUs.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        pointers();
        profileCheck();
        setSupportActionBar(toolbar);
    }

    private void pointers() {

        navDown = (BottomNavigationView) findViewById(R.id.bottomNav_down_Home);
        navDown.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_tel));
        setNavigationItemListener();
        G.disableShiftModeNavigation(navDown);
        rbSelf = (RadioButton) findViewById(R.id.rbSelfMain);
        rbOther = (RadioButton) findViewById(R.id.rbOtherMain);
        llRadioGroup = (LinearLayout) findViewById(R.id.llRadioGroupMain);
        toolbar = (Toolbar) findViewById(R.id.tlbProfile);
        contentMain = (FrameLayout) findViewById(R.id.contentMain);
        rbOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    navDown.setSelectedItemId(R.id.homeNaveDownHome);
            }
        });
        rbSelf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    fhome = null;
                    replaceContentWith(new FragmentSelfCourse());
                }
            }
        });
    }

    private void setNavigationItemListener() {

        navDown.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeNaveDownHome:
                        if (fhome == null) {
                            rbOther.setChecked(true);
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
    public void onBackPressed() {
        if (fhome == null) {
            navDown.setSelectedItemId(R.id.homeNaveDownHome);
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

        if (fhome != null) {
            fhome.queryForCourses(id);
        } else {
            fhome = new FragmentHome();
            replaceContentWith(fhome);
            fhome.id = id;
        }
    }
}
