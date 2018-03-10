package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by MAHNAZ on 10/15/2017.
 */

public class ActivityOptionalCourse extends AppCompatActivity {

    public static int courseId;
    public static String teacherId;
    public static ViewPager viewPager;
    Toolbar tlb;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional_course);
        G.context = this;
        G.activity = this;
        if (getIntent().getExtras() != null) {
            courseId = getIntent().getIntExtra("id", -1);
            teacherId = getIntent().getStringExtra("teacherId");
        }
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
        adapterViewPager.add(new FragmentShowcourseFeature(), "مشخصات");
        adapterViewPager.add(new FragmentShowTeacherFeature(), "آموزشگاه");
        adapterViewPager.add(new FragmentComment(), "نظرات و امتیازات");
        viewPager.setAdapter(adapterViewPager);

    }

    private void pointer() {
        viewPager = (ViewPager) findViewById(R.id.vpOptionalCourse);
        tlb = (Toolbar) findViewById(R.id.tlbOptionalCourse);
        tabLayout = (TabLayout) findViewById(R.id.tabsOptionalCourse);
    }

}