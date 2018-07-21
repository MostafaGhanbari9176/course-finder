package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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
        } else {
            Toast.makeText(this, "خطا!!", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        init();
    }

    private void init() {
        pointer();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.add(new FragmentComment(), "نظرات و امتیازات");
        adapterViewPager.add(new FragmentTeacherCourse(), "دوره های آموزشگاه");
        adapterViewPager.add(new FragmentShowTeacherFeature(), "آموزشگاه");
        if (courseId != -1)
            adapterViewPager.add(new FragmentShowcourseFeature(), "مشخصات");
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(3);
        viewPager.setOffscreenPageLimit(5);

    }

    private void pointer() {
        viewPager = (ViewPager) findViewById(R.id.vpOptionalCourse);
        tlb = (Toolbar) findViewById(R.id.tlbOptionalCourse);
        tabLayout = (TabLayout) findViewById(R.id.tabsOptionalCourse);
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAfterTransition();
        }else
            this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        G.context = this;
        G.activity = this;
        super.onResume();
    }
}