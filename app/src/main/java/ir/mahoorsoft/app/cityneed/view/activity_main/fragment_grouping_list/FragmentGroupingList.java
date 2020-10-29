package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityShowMoreCourse;

/**
 * Created by M-gh on 08-Apr-18.
 */

public class FragmentGroupingList extends Fragment implements AdapterGroupingList.OnClickItemGroupingList, PresentGrouping.OnPresentTabagheListener, SwipeRefreshLayout.OnRefreshListener {


    View view;
    RecyclerView list;
    ArrayList<StGrouping> source = new ArrayList<>();
    AdapterGroupingList adapter;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_grouping_list, container, false);
            pointers();
            queryForGroupData();

        }
        return view;
    }

    private void pointers() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentGroupingList);
        sDown.setOnRefreshListener(this);
        list = (RecyclerView) view.findViewById(R.id.RVFragmentGroupingList);

    }

    public void queryForGroupData() {
        sDown.setRefreshing(true);
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

        sDown.setRefreshing(false);
        source.clear();
        source.addAll(data);
        adapter = new AdapterGroupingList(G.context, source, this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(G.context, 2, GridLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void sendMessageFTabagheT(String message) {

        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        queryForGroupData();

    }
}
