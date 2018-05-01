package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityShowMoreCourse;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentGroupingList extends Fragment implements AdapterGroupingList.OnClickItemGroupingList, PresentGrouping.OnPresentTabagheListener {


    View view;
    RecyclerView list;
    ProgressBar bar;
    ArrayList<StGrouping> source = new ArrayList<>();
    AdapterGroupingList adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grouping_list, container, false);
        pointers();
        queryForGroupData();
        return view;
    }

    private void pointers() {
        list = (RecyclerView) view.findViewById(R.id.RVFragmentGroupingList);
        bar = (ProgressBar) view.findViewById(R.id.pbarFragmentGroupingList);
    }

    public void queryForGroupData() {
        PresentGrouping presentGrouping = new PresentGrouping(this);
        presentGrouping.getTabaghe(-1);
    }

    @Override
    public void groupingListItemClick(int position) {
        Intent intent = new Intent(G.context, ActivityShowMoreCourse.class);
        intent.putExtra("groupId", source.get(position).id);
        intent.putExtra("groupName", source.get(position).subject);
        startActivity(intent);
    }

    @Override
    public void onResiveTabaghe(ArrayList<StGrouping> data) {
        bar.setVisibility(View.GONE);
        source.addAll(data);
        adapter = new AdapterGroupingList(G.context, source, this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(G.context, 2, GridLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void sendMessageFTabagheT(String message) {
        bar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }
}
