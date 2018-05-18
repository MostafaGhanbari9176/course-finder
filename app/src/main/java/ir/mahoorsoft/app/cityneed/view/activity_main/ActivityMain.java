package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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


public class ActivityMain extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout llRadioGroup;
    RadioButton rbSelf;
    RadioButton rbOther;
    FrameLayout contentMain;
    public DialogProgres dialogProgres;
    BottomNavigationView navDown;
    HashMap<String, Fragment> fSaver = new HashMap<>();
    Stack<String> keySaver = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        setContentView(R.layout.activity_main);
        init();
        rbOther.setChecked(true);
        replaceContentWith("fHome", new FragmentHome());
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
                replaceContentWith("fMap", new FragmentMap());
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
            case R.id.closeApp:
                this.finish();
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
                if (isChecked) {
                    replaceContentWith("fSelfCourse", new FragmentSelfCourse());
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
                        replaceContentWith("fHome", new FragmentHome());
                        return true;

                    case R.id.searchNanDownHome:
                        replaceContentWith("fSearch", new FragmentSearch());
                        return true;

                    case R.id.groupingNavDownHome:
                        replaceContentWith("fGroupingList", new FragmentGroupingList());
                        return true;

                    case R.id.teacherListNavDownHome:
                        replaceContentWith("fTeacherList", new FragmentTeacherList());
                        return true;

                    case R.id.profileNavDownHome:
                        acountCheck();
                        return true;
                }
                return false;
            }

        });

    }

    public void replaceContentWith(String key, Fragment value) {
        boolean isAvailable = false;
        for (Map.Entry m : fSaver.entrySet()) {
            if (m.getKey() == key) {
                isAvailable = true;
                break;
            }
        }
        try {
            if (!isAvailable) {
                fSaver.put(key, value);
            }
        } catch (Exception e) {
            keySaver.clear();
            fSaver.clear();
        }
        G.activity.getSupportFragmentManager()
                .beginTransaction().replace(R.id.contentMain, fSaver.get(key))
                .commit();
        setNavBottomColor(key);
        addKeyForBack(key);
        switch (key) {
            case "fHome":
                rbOther.setChecked(true);
                break;
            case "fSelfCourse":
                rbSelf.setChecked(true);
                break;
        }
    }

    private void setNavBottomColor(String key) {
        switch (key) {
            case "fHome":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_tel));
                break;

            case "fMap":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                break;

            case "fSearch":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                break;

            case "fGroupingList":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                break;

            case "fSelfCourse":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                break;

            case "fTeacherList":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                break;
        }
    }

    private void addKeyForBack(String key) {
        if (keySaver.size() >= 4) {
            keySaver.remove(0);
        }
        keySaver.add(key);
    }

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (keySaver.size() > 1) {
            keySaver.pop();
            replaceContentWith(keySaver.pop(), null);
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
}
