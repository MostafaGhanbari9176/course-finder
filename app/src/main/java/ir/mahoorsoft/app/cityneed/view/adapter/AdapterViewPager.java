package ir.mahoorsoft.app.cityneed.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by MAHNAZ on 10/9/2017.
 */

public class AdapterViewPager extends FragmentPagerAdapter {


    ArrayList<Fragment> fragments = new ArrayList<>();

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
