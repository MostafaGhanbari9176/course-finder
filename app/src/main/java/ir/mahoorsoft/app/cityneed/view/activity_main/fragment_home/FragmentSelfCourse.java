package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.ActivityStudentNameList;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseListTeacher;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 03-Feb-18.
 */

public class FragmentSelfCourse extends Fragment implements AdapterCourseListTeacher.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, PresentUpload.OnPresentUploadListener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    TextView txt;
    AdapterCourseListTeacher adapter;
    RecyclerView list;
    ArrayList<StCourse> surce = new ArrayList<>();
    DialogProgres dialogProgres;
    int position;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_list, container, false);
            init();
        }
        return view;
    }

    private void init() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentSelfCourse);
        sDown.setOnRefreshListener(this);
        ((Toolbar) view.findViewById(R.id.tlbList)).setVisibility(View.GONE);
        txt = (TextView) view.findViewById(R.id.txtEmptyCourseList);
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
        sDown.setRefreshing(true);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByTeacherId(Pref.getStringValue(PrefKey.apiCode, ""));
    }


    @Override
    public void sendMessageFCT(String message) {
        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        sDown.setRefreshing(false);
        if (course.get(0).empty == 1)
            txt.setVisibility(View.VISIBLE);
        else {
            txt.setVisibility(View.GONE);
            list = (RecyclerView) view.findViewById(R.id.RVList);
            surce.clear();
            surce.addAll(course);
            adapter = new AdapterCourseListTeacher(G.context, surce, this);
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
        Intent intent = new Intent(G.context, ActivityStudentNameList.class);
        intent.putExtra("id", surce.get(position).id);
        intent.putExtra("name", surce.get(position).CourseName);
        startActivity(intent);
    }

    @Override
    public void changeImage(int position) {
        this.position = position;
        selectImage();
    }

    private void selectImage() {
        Intent intent = new Intent(G.context, ActivityFiles.class);
        intent.putExtra("fileKind", "image");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        sDown.setRefreshing(false);
        if (data == null)
            return;
        if (requestCode == 2) {
            uploadImage(data.getStringExtra("path"));
        } else
            sendMessageFCT("خطا!!!");
    }

    private void uploadImage(String path) {
        dialogProgres = new DialogProgres(G.context, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("course", surce.get(position).id + ".png", path);
    }

    @Override
    public void messageFromUpload(String message) {
        dialogProgres.closeProgresBar();
        sendMessageFCT(message);
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {

        if (res.code == 1)
            messageFromUpload("بارگذاری شد");
        else
            messageFromUpload("خطا!!!");
    }

    @Override
    public void onResume() {
        if (ActivityStudentNameList.removeWaiting) {
            setSource();
            ActivityStudentNameList.removeWaiting = false;
        }
        super.onResume();
    }

    @Override
    public void onRefresh() {
        init();
    }
}
