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
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
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

public class FragmentFavoriteTeacher extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PresentTeacher.OnPresentTeacherListener, AdapterTeacherList.OnClickItemTeacherList {

    View view;
    RecyclerView list;
    AdapterTeacherList adapter;
    ArrayList<StTeacher> source = new ArrayList<>();
    TextView txtEmpty;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_teacher_list, container, false);
        init();
        return view;
    }

    private void init() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentFavoriteTeacherList);
        sDown.setOnRefreshListener(this);
        list = (RecyclerView) view.findViewById(R.id.RVFavoriteTeacherList);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmptyFavoriteTeacherList);
        getData();

    }

    private void getData() {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            sDown.setRefreshing(true);
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.getFavoriteTeachers();
        } else {
            {
                sDown.setRefreshing(false);
                txtEmpty.setText("برای مشاهده لیست علاقه مندی ابتدا وارد حساب کاربری خود شوید");
                txtEmpty.setVisibility(View.VISIBLE);
            }
        }
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
        txtEmpty.setVisibility(View.GONE);
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
        ((ActivityMain) G.activity).helpSwipeProgress.setVisibility(View.GONE);
    }
}
