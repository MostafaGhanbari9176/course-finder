package ir.mahoorsoft.app.cityneed.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;

import java.util.ArrayList;

/**
 * Created by MAHNAZ on 10/9/2017.
 */

public class AdapterViewPager extends FragmentPagerAdapter {


    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private Context context;

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
        this.context = context;
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
}
