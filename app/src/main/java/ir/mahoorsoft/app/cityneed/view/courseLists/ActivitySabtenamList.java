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

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivitySabtenamList extends AppCompatActivity implements AdapterCourseList.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener {

    AdapterCourseList adapter;
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
        getSupportActionBar().setTitle("دوره های ثبت نام شده توسط شما");
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
        adapter = new AdapterCourseList(this, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setSource();


    }

    private void setSource() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getUserCourse();

    }

    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {
//        if (id == 1) {
//            surce.remove(clickedPosition);
//            adapter.notifyItemRemoved(clickedPosition);
//            adapter.notifyItemRangeChanged(clickedPosition, adapter.getItemCount());
//        }
    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        dialogProgres.closeProgresBar();
        if (course.get(0).empty == 1)
            txt.setVisibility(View.VISIBLE);
        else {
            txt.setVisibility(View.GONE);
            surce = course;
            adapter = new AdapterCourseList(this, surce, this);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StHomeListItems> items) {

    }

    @Override
    public void courseListItemClick(int id) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void courseDeletedClick(int position) {
        if (surce.get(position).isDeleted == 1)
            queryForUpdateDeletedFlag(surce.get(position).id);
        if (surce.get(position).isCanceled == 1)
            queryForUpdateCanceledFlag(surce.get(position).sabtenamId);
        surce.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
    }

    private void queryForUpdateDeletedFlag(int id) {
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.updateDeletedFlag(id, 2);
    }

    private void queryForUpdateCanceledFlag(int id) {
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.updateCanceledFlag(id, 2);

    }

    @Override
    public void sendMessageFST(String message) {

    }

    @Override
    public void confirmSabtenam(boolean flag) {

    }

    @Override
    public void checkSabtenam(float ratBarValue) {

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}