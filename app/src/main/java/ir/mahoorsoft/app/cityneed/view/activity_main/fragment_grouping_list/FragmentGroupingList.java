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

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentGroupingList extends Fragment {

    View view;
    static ViewPager viewPager;
    static  TabLayout tabLayout;
    public static AdapterViewPager adapterViewPager;

    Button btnHelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grouping_list, container, false);
        adapterViewPager = null;

        pointers();
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
        return view;
    }

    private void pointers() {
        viewPager = (ViewPager) view.findViewById(R.id.vpFragmentGroupingList);
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
        child.queryForGroupList(-1);
        adapterViewPager.add(child, "شاخه اصلی");
        viewPager.setAdapter(adapterViewPager);
    }

    public static void addPage(int id, String name) {
        int currentPage = FragmentGroupingList.viewPager.getCurrentItem();
        int count = FragmentGroupingList.adapterViewPager.getCount();
        if ((count - 1 > currentPage)) {
            removePages(currentPage, count);
            updatePageData(id);
        } else {
            FragmentChildGroupingList child = new FragmentChildGroupingList();
            adapterViewPager.add(child, "شاخه " + name);
            adapterViewPager.notifyDataSetChanged();
            View v2 = adapterViewPager.getItem(adapterViewPager.getCount() - 1).getView();
            ((FragmentChildGroupingList) adapterViewPager.getItem(adapterViewPager.getCount() - 1)).queryForGroupList(id);
        }
        viewPager.setCurrentItem(adapterViewPager.getCount() - 1);
    }

    private static void updatePageData(int id) {
        ((FragmentChildGroupingList) adapterViewPager.getItem(adapterViewPager.getCount() - 1)).queryForGroupList(id);

    }

    private static void removePages(int current, int count) {
        for (int i = 1; i < count - current - 1; i++) {
           // adapterViewPager.getItem(adapterViewPager.getCount() - 1).onDestroyView();
            adapterViewPager.remove();
            adapterViewPager.notifyDataSetChanged();
        }


    }

}
