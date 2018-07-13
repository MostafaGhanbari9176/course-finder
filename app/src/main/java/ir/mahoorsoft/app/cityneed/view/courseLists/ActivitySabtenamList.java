package ir.mahoorsoft.app.cityneed.view.courseLists;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentNotify;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSabtenamList;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivitySabtenamList extends AppCompatActivity implements AdapterSabtenamList.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener, SwipeRefreshLayout.OnRefreshListener, PresentNotify.OnPresentNotifyListener {

    AdapterSabtenamList adapter;
    RecyclerView list;
    ArrayList<StCourse> surce = new ArrayList<>();
    SwipeRefreshLayout sDown;
    TextView txt;
    Toolbar tlb;
    boolean isUserChanged = true;
    int position;
    SwitchCompat sDate;
    SwitchCompat sWeak;

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
        sDown = (SwipeRefreshLayout) findViewById(R.id.SDFragmentSelfCourse);
        sDown.setOnRefreshListener(this);
        list = (RecyclerView) findViewById(R.id.RVList);
        list.setBackgroundColor(ContextCompat.getColor(this, R.color.light_two));
        adapter = new AdapterSabtenamList(this, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setSource();


    }

    private void setSource() {
        sDown.setRefreshing(true);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getUserCourse();

    }

    @Override
    public void sendMessageFCT(String message) {
        sDown.setRefreshing(false);
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
        sDown.setRefreshing(false);
        if (course.get(0).empty == 1)
            txt.setVisibility(View.VISIBLE);
        else {
            txt.setVisibility(View.GONE);
            surce.clear();
            surce.addAll(course);
            adapter = new AdapterSabtenamList(this, surce, this);
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
        intent.putExtra("teacherId", surce.get(position).idTeacher);
        startActivity(intent);
    }

    @Override
    public void courseDeletedClick(int position) {
        if (surce.get(position).isCanceled == 1 || surce.get(position).isDeleted == 1)
            queryForUpdateCanceledFlag(surce.get(position).sabtenamId);
        surce.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
    }

    @Override
    public void btnItemMenuPressed(int position) {
        this.position = position;
        getNotifySettingData();
        //showNotifySettingDialog();

    }

    private void getNotifySettingData() {
        sDown.setRefreshing(true);
        (new PresentNotify(this)).getNotifySettingData(surce.get(position).id);
    }

    private void showNotifySettingDialog(StNotifyData res) {
        try {
            Dialog dialog = new Dialog(this);
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.dialog_sabtenam_course_items, null);
            sDate = (SwitchCompat) view.findViewById(R.id.switchCompatStartDate);
            sWeak = (SwitchCompat) view.findViewById(R.id.switchCompatWeak);
            sDate.setChecked(res.startNotify == 1);
            sWeak.setChecked(res.weakNotify == 1);
            sDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    changeNotifySetting();
                }
            });
            sWeak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    changeNotifySetting();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(view);
            dialog.show();
        } catch (Exception ignore) {
        }
    }

    private void changeNotifySetting() {
        sDown.setRefreshing(true);
        (new PresentNotify(this)).saveNotifySetting(surce.get(position).id, sWeak.isChecked() ? 1 : 0, sDate.isChecked() ? 1 : 0);
    }

    private void queryForUpdateDeletedFlag(int id) {
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.updateDeletedFlag(id, 2);
    }

    private void queryForUpdateCanceledFlag(int id) {
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.updateCanceledFlag(id, 2, -1, "a", "a", "a");

    }

    @Override
    public void sendMessageFST(String message) {

    }

    @Override
    public void confirmSabtenam(boolean flag) {

    }

    @Override
    public void confirmDelete(boolean flag) {

    }

    @Override
    public void checkSabtenam(float ratBarValue) {

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        setSource();
    }

    @Override
    public void onReceiveData(ArrayList<StNotifyData> res) {
        sDown.setRefreshing(false);
        showNotifySettingDialog(res.get(0));
    }

    @Override
    public void onReceiveFlagFromNotify(boolean flag) {
        sDown.setRefreshing(false);
    }

    @Override
    public void messageFromNotify(String message) {
        sDown.setRefreshing(false);
    }
}