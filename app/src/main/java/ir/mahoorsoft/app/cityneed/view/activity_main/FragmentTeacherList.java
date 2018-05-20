package ir.mahoorsoft.app.cityneed.view.activity_main;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.tables.teacher.Teacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTeacherList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 4/28/2018.
 */

public class FragmentTeacherList extends Fragment implements PresentTeacher.OnPresentTeacherListener, AdapterTeacherList.OnClickItemTeacherList, SwipeRefreshLayout.OnRefreshListener {

    View view;
    RecyclerView list;
    AdapterTeacherList adapter;
    ArrayList<StTeacher> source = new ArrayList<>();
    TextView txtEmpty;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_teacher_list, container, false);
            inite();
        }
        return view;
    }

    private void inite() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentTeacherList);
        sDown.setOnRefreshListener(this);
        list = (RecyclerView) view.findViewById(R.id.RVTeacherList);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmptyFragmentTeacherList);
        getData();
    }

    private void getData() {
        sDown.setRefreshing(true);
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getAllTeacher();
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
        if (teachers.get(0).empty == 1) {
            txtEmpty.setVisibility(View.VISIBLE);
            return;
        }
        source.clear();
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
        inite();
    }
}
