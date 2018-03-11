package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

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
