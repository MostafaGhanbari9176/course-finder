package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.FragmentSmsBoxIn;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.FragmentSmsBoxOut;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentGroupingList extends Fragment implements FragmentChildGroupingList.EditePages {

    View view;
    static ViewPager viewPager;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;
    static int itemCounter = 0;
    public static ArrayList<GroupingListPages> pages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grouping_list, container, false);
        adapterViewPager = null;
        pages.clear();
        itemCounter = 0;
        pointers();
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
        return view;
    }

    private void pointers() {
        viewPager = (ViewPager) view.findViewById(R.id.vpFragmentGroupingList);
        viewPager.setOffscreenPageLimit(10);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLFragmentGroupingList);
    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getChildFragmentManager());
        FragmentChildGroupingList child = new FragmentChildGroupingList();
        child.queryForGroupList(itemCounter, "اصلی", -1);
        adapterViewPager.add(child, "شاخه اصلی");
        viewPager.setAdapter(adapterViewPager);
    }
    @Override
    public void addPage(int id, String name) {
        FragmentChildGroupingList child = new FragmentChildGroupingList();
        itemCounter++;
        child.queryForGroupList(itemCounter, name, id);
        adapterViewPager.add(child, "شاخه " + name);
        adapterViewPager.notifyDataSetChanged();
        viewPager.setCurrentItem(itemCounter);

    }

    @Override
    public void updatePage() {
        adapterViewPager = new AdapterViewPager(getChildFragmentManager());
        for (int i = 0; i < pages.size(); i++) {
            FragmentChildGroupingList child = new FragmentChildGroupingList();
            adapterViewPager.add(child, "شاخه " + pages.get(i).pageName);
            child.setSource(pages.get(i).page);
            itemCounter++;
        }
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(itemCounter);
    }


/*    static String getPageName(int position) {
        int id = pages.get(position).page.get(0).uperId;
        String subject = "";
        for (int i = 0; i < pages.get(position-1).page.size(); i++) {
            if(pages.get(position-1).page.get(i).id == id) {
                subject = pages.get(position - 1).page.get(i).subject;
                break;
            }
        }
        return subject;
    }*/


}
