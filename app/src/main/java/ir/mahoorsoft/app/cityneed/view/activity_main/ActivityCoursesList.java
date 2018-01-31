package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivityCoursesList extends AppCompatActivity implements AdapterCourseList.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener {
    String id;
    boolean getAllCourse = false;
    AdapterCourseList adapter;
    RecyclerView list;
    ArrayList<StCourse> surce;
    DialogProgres dialogProgres = new DialogProgres(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        surce = new ArrayList<>();
        list = (RecyclerView) findViewById(R.id.RVList);
        adapter = new AdapterCourseList(this, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (getIntent().getExtras() == null)
            getAllCourse = true;
        else
            id = getIntent().getStringExtra("id");
        setSource(getAllCourse);


    }

    private void setSource(boolean getAllCourse) {
        dialogProgres.showProgresBar();
        if (getAllCourse) {
            PresentCourse presentCourse = new PresentCourse(this);
            presentCourse.getAllCourse();
        } else {
            PresentCourse presentCourse = new PresentCourse(this);
            presentCourse.getCourseByTeacherId(id);
        }

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
    public void onReceiveCourse(ArrayList<StCourse> course) {
        dialogProgres.closeProgresBar();
        list = (RecyclerView) findViewById(R.id.RVList);
        adapter = new AdapterCourseList(this, course, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void courseListItemClick(int position) {

    }
}