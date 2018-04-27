package ir.mahoorsoft.app.cityneed.view.courseLists;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.ActivityStudentNameList;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseListTeacher;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivityTeacherCoursesList extends AppCompatActivity implements AdapterCourseListTeacher.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, PresentUpload.OnPresentUploadListener {
    int position;
    AdapterCourseListTeacher adapter;
    RecyclerView list;
    ArrayList<StCourse> surce;
    DialogProgres dialogProgres;
    TextView txt;
    Toolbar tlb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        txt = (TextView) findViewById(R.id.txtEmptyCourseList);
        tlb = (Toolbar) findViewById(R.id.tlbList);
        setSupportActionBar(tlb);
        getSupportActionBar().setTitle("دوره های ثبت شده توسط شما");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialogProgres = new DialogProgres(this);
        surce = new ArrayList<>();
        list = (RecyclerView) findViewById(R.id.RVList);
        adapter = new AdapterCourseListTeacher(this, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getData();
    }

    private void getData() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByTeacherId(Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            surce.clear();
            surce.addAll(course);
            adapter = new AdapterCourseListTeacher(this, surce, this);
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
        Intent intent = new Intent(this, ActivityStudentNameList.class);
        intent.putExtra("id", surce.get(position).id);
        intent.putExtra("name", surce.get(position).CourseName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void changeImage(int position) {
        this.position = position;
        selectImage();
    }

    private void selectImage() {
        Intent intent = new Intent(this, ActivityFiles.class);
        intent.putExtra("isImage", true);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        dialogProgres.closeProgresBar();
        if (data == null) {
            sendMessageFCT("خطا!!!");
            return;
        }
        if (requestCode == 2 && data != null) {
            uploadImage(data.getStringExtra("path"));
        } else
            sendMessageFCT("خطا!!!");
    }

    private void uploadImage(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("course", surce.get(position).id + ".png", path);
    }

    @Override
    public void messageFromUpload(String message) {
        sendMessageFCT(message);
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {

        if (res.code == 1)
            sendMessageFCT("بارگذاری شد");
        else
            sendMessageFCT("خطا!!!");
    }

    @Override
    protected void onResume() {
        if (ActivityStudentNameList.removeWaiting){
            getData();
            ActivityStudentNameList.removeWaiting = false;
        }
        super.onResume();
    }
}
