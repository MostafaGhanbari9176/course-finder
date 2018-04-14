package ir.mahoorsoft.app.cityneed.view.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by MAHNAZ on 10/9/2017.
 */

public class AdapterViewPager extends FragmentPagerAdapter {


    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();


    public AdapterViewPager(FragmentManager fm) {
        super(fm);

    }

    public void add(Fragment fragment, String title) {
        fragments.add(fragment);
        this.title.add(title);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
