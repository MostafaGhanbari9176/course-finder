package ir.mahoorsoft.app.cityneed.view.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Stack;

/**
 * Created by MAHNAZ on 10/9/2017.
 */

public class AdapterViewPager extends FragmentPagerAdapter {


    public Stack<Fragment> fragments = new Stack<>();
    public Stack<String> titles = new Stack<>();


    public AdapterViewPager(FragmentManager fm) {
        super(fm);

    }

    public void add(Fragment fragment, String title) {
        fragments.push(fragment);
        titles.push(title);

    }

    public void remove() {
        fragments.pop();
        titles.pop();

    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
