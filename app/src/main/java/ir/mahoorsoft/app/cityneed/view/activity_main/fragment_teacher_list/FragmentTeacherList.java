package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_teacher_list;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;


/**
 * Created by RCC1 on 4/28/2018.
 */

public class FragmentTeacherList extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_teacher_list, container, false);
        init();

        return view;
    }

    private void init() {
        pointer();
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
    }

    private void pointer() {
        viewPager = (ViewPager) view.findViewById(R.id.VPFragmentTeacherList);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutFragmentTeacherList);
    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getChildFragmentManager());
        adapterViewPager.add(new FragmentFavoriteTeacher(), "آموزشگاهای مورد علاقه");
        adapterViewPager.add(new FragmentAllTeacher(), "آموزشگاها");
        viewPager.setAdapter(adapterViewPager);
       // viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1);

    }


}