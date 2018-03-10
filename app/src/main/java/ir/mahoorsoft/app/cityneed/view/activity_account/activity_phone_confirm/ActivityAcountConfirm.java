package ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TimerTask;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentSmsCode;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.FragmentComment;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.FragmentShowTeacherFeature;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.FragmentShowcourseFeature;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class ActivityAcountConfirm extends AppCompatActivity {
    Toolbar tlb;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        setContentView(R.layout.activity_acount_confirm);
        init();
    }

    private void init() {
        pointer();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();

    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.add(new FragmentEmailConfirm(), "ثبت نام یا ورود با ایمیل");
        adapterViewPager.add(new FragmentPhoneConfirm(), "ثبت نام یا ورود با شماره همراه");
        viewPager.setAdapter(adapterViewPager);

    }

    private void pointer() {
        viewPager = (ViewPager) findViewById(R.id.vpAcountConfirm);
        tlb = (Toolbar) findViewById(R.id.tlbAcountConfirm);
        tabLayout = (TabLayout) findViewById(R.id.tabsAcountConfirm);
    }

}
