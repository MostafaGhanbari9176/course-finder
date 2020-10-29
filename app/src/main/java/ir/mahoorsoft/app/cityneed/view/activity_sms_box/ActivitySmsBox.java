package ir.mahoorsoft.app.cityneed.view.activity_sms_box;


import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
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
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        adapterViewPager.add(new FragmentSmsBoxOut(), "صندوق خروجی");
        adapterViewPager.add(new FragmentSmsBoxIn(), "صندوق ورودی");
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}

