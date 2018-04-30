package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentGroupingList extends Fragment implements FragmentChildGroupingList.ManagePages {


    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;
    Button btnHelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grouping_list, container, false);
        pointers();
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
        return view;
    }

    private void pointers() {
        viewPager = (ViewPager) view.findViewById(R.id.vpFragmentGroupingList);
        viewPager.setSaveFromParentEnabled(false);
        viewPager.setOffscreenPageLimit(10);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLFragmentGroupingList);
        btnHelp = (Button) view.findViewById(R.id.btnHelpMe);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });
    }

    private void showData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("view = " + adapterViewPager.getItem(viewPager.getCurrentItem()).getView());
        builder.setMessage("position = " + viewPager.getCurrentItem());
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getChildFragmentManager());
        FragmentChildGroupingList child = new FragmentChildGroupingList();
        adapterViewPager.add(child, "شاخه اصلی");
        viewPager.setAdapter(adapterViewPager);
       // adapterViewPager.notifyDataSetChanged();
        child.managePages = this;
        child.queryForGroupList(-1);
    }

    private void updatePageData(int id) {
        ((FragmentChildGroupingList) adapterViewPager.getItem(adapterViewPager.getCount() - 1)).queryForGroupList(id);

    }

    private void removePages(int current, int count) {
       // pointers();
        for (int i = 1; i < count - current; i++) {
            adapterViewPager.remove();
        }
        ArrayList<ArrayList<StGrouping>> groups = new ArrayList<>();
        Stack<Fragment> fragments = adapterViewPager.fragments;
        for (int i = 0; i < fragments.size(); i++) {
            groups.add(((FragmentChildGroupingList) (fragments.get(i))).source);
        }
        adapterViewPager = new AdapterViewPager(getChildFragmentManager());
        for (int i = 0; i < fragments.size(); i++) {
            FragmentChildGroupingList child = new FragmentChildGroupingList();
            adapterViewPager.add(child, "شاخه اصلی");
          //  adapterViewPager.notifyDataSetChanged();
            child.managePages = this;
            child.setSource(groups.get(i));
        }
        viewPager.setAdapter(adapterViewPager);
      //  adapterViewPager.notifyDataSetChanged();
    }


    @Override
    public void addPage(int id, String name) {
        int currentPage = viewPager.getCurrentItem();
        int count = adapterViewPager.getCount();
        if ((count - 1 > currentPage))
            removePages(currentPage, count);
        FragmentChildGroupingList child = new FragmentChildGroupingList();
        adapterViewPager.add(child, "شاخه " + name);
        adapterViewPager.notifyDataSetChanged();
        child.managePages = this;
        ((FragmentChildGroupingList) adapterViewPager.getItem(adapterViewPager.getCount() - 1)).queryForGroupList(id);
        viewPager.setCurrentItem(adapterViewPager.getCount() - 1);
    }
}
