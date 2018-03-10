package ir.mahoorsoft.app.cityneed.view.activity_sms_box;


import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentErrorServer;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class ActivitySmsBox extends AppCompatActivity {
    ViewPager viewPager;
    Toolbar tlb;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_box);
        G.activity = this;
        G.context = this;
        pointers();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
    }

    private void pointers() {
        viewPager = (ViewPager) findViewById(R.id.vpSmsBox);
        tlb = (Toolbar) findViewById(R.id.tbSmsBox);
        tabLayout = (TabLayout) findViewById(R.id.tabLSmsBox);
    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.add(new FragmentSmsBoxIn(), "صندوق ورودی");
        adapterViewPager.add(new FragmentSmsBoxOut(), "صندوق خروجی");
        viewPager.setAdapter(adapterViewPager);

    }
}

