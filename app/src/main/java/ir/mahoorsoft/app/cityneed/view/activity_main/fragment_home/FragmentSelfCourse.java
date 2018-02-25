package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature.ActivityShowFeature;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseListTeacher;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 03-Feb-18.
 */

public class FragmentSelfCourse extends Fragment implements AdapterCourseListTeacher.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener {
    View view;
    String id;
    String modeShow = "";
    AdapterCourseListTeacher adapter;
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
        ((Toolbar) view.findViewById(R.id.tbList)).setVisibility(View.GONE);
        dialogProgres = new DialogProgres(G.context);
        surce = new ArrayList<>();
        list = (RecyclerView) view.findViewById(R.id.RVList);
        adapter = new AdapterCourseListTeacher(G.context, surce, this);
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
        presentCourse.getCourseByTeacherId(Pref.getStringValue(PrefKey.apiCode,""));
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
        list = (RecyclerView) view.findViewById(R.id.RVList);
        adapter = new AdapterCourseListTeacher(G.context, course, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StHomeListItems> items) {

    }

    @Override
    public void courseListItemClick(int id) {
        Intent intent = new Intent(G.context, ActivityShowFeature.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
