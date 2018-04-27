package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 03-Feb-18.
 */

public class FragmentTeacherCourse extends Fragment implements AdapterCourseList.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener {
    View view;
    TextView txt;
    AdapterCourseList adapter;
    RecyclerView list;
    ArrayList<StCourse> surce;
    DialogProgres dialogProgres;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list, container, false);
        init();
        return view;
    }

    private void init() {
        ((Toolbar) view.findViewById(R.id.tlbList)).setVisibility(View.GONE);
        txt = (TextView) view.findViewById(R.id.txtEmptyCourseList);
        dialogProgres = new DialogProgres(G.context);
        surce = new ArrayList<>();
        list = (RecyclerView) view.findViewById(R.id.RVList);
        adapter = new AdapterCourseList(G.context, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setSource();
    }

    private void setSource() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByTeacherId(ActivityOptionalCourse.teacherId);
    }


    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        dialogProgres.closeProgresBar();
        if (course.get(0).empty == 1)
            txt.setVisibility(View.VISIBLE);
        else {
            txt.setVisibility(View.GONE);
            list = (RecyclerView) view.findViewById(R.id.RVList);
            surce.clear();
            surce.addAll(course);
            adapter = new AdapterCourseList(G.context, surce, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                    , LinearLayoutManager.VERTICAL, false);
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void courseListItemClick(int position) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", surce.get(position).id);
        intent.putExtra("teacherId", ActivityOptionalCourse.teacherId);
        startActivity(intent);
    }


}
