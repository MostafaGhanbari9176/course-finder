package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_teacher_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTeacherList;

/**
 * Created by M-gh on 21-Jun-18.
 */

public class FragmentAllTeacher extends Fragment implements PresentTeacher.OnPresentTeacherListener, AdapterTeacherList.OnClickItemTeacherList, SwipeRefreshLayout.OnRefreshListener {

    View view;
    RecyclerView list;
    AdapterTeacherList adapter;
    ArrayList<StTeacher> source = new ArrayList<>();
    TextView txtEmpty;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_teacher_list, container, false);
        init();

        return view;
    }

    private void init() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentAllTeacherList);
        sDown.setOnRefreshListener(this);
        list = (RecyclerView) view.findViewById(R.id.RVFragmentAllTeacherList);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmptyFragmentAllTeacherList);
        if (source.size() == 0)
            getData();
        else
            setData();

    }

    private void getData() {
        sDown.setRefreshing(true);
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getAllTeacher();
    }

    private void setData() {
        adapter = new AdapterTeacherList(G.context, source, this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(G.context, 2, GridLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendMessageFTT(String message) {
        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> teachers) {
        sDown.setRefreshing(false);
        source.clear();

        if (teachers.get(0).empty == 1) {
            txtEmpty.setVisibility(View.VISIBLE);
            return;
        }
        source.addAll(teachers);
        adapter = new AdapterTeacherList(G.context, source, this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(G.context, 2, GridLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onReceiveCustomeTeacherListData(ArrayList<StCustomTeacherListHome> data) {

    }

    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> users) {

    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {

    }

    @Override
    public void teacherListItemClick(int position) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", -1);
        intent.putExtra("teacherId", source.get(position).ac);
        startActivity(intent);

    }


    @Override
    public void onRefresh() {
        getData();

    }
}
