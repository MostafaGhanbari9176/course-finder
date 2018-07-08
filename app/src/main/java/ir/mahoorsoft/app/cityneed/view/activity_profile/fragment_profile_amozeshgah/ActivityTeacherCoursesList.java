package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.CheckedSTatuse;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseListTeacher;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogDayWeek;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivityTeacherCoursesList extends AppCompatActivity implements AdapterCourseListTeacher.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, PresentUpload.OnPresentUploadListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, DialogDayWeek.ReturnDay {
    int position;
    AdapterCourseListTeacher adapter;
    RecyclerView list;
    ArrayList<StCourse> surce;
    DialogProgres dialogProgres;
    SwipeRefreshLayout sDown;
    TextView txt;
    Toolbar tlb;
    TextView txtStartDate;
    TextView txtEndDate;
    TextView txtHours;
    TextView txtDays;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;

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
        sDown = (SwipeRefreshLayout) findViewById(R.id.SDFragmentSelfCourse);
        sDown.setOnRefreshListener(this);
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
        surce.clear();
        sDown.setRefreshing(true);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByTeacherId(Pref.getStringValue(PrefKey.apiCode, ""));
    }

    @Override
    public void sendMessageFCT(String message) {
        sDown.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {
        sDown.setRefreshing(false);
    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        sDown.setRefreshing(false);
        if (course.get(0).empty == 1) {
            txt.setVisibility(View.VISIBLE);
            return;
        }
        if (surce.size() == 0) {
            txt.setVisibility(View.GONE);
            surce.clear();
            surce.addAll(course);
            adapter = new AdapterCourseListTeacher(this, surce, this);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else
            showEditDialog(course.get(0));

    }


    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {
        sDown.setRefreshing(false);
    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {
        sDown.setRefreshing(false);
    }

    @Override
    public void courseListItemClick(int position) {
        showQuestionDialog(position);
    }

    private void showQuestionDialog(final int position) {

        final Dialog dialog = new Dialog(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_qustion_for_teacher_course, null, false);
        ((Button) view.findViewById(R.id.btnEditCourse)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCourseData(surce.get(position).id);
                dialog.cancel();
            }
        });

        ((Button) view.findViewById(R.id.btnShowStudents)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudents(position);
                dialog.cancel();
            }
        });

        ((Button) view.findViewById(R.id.btnUploadNewsVideo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseFile();
                dialog.cancel();
            }
        });

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void choseFile() {
        Intent intent = new Intent(this, ActivityFiles.class);
        intent.putExtra("fileKind", "video");
        startActivityForResult(intent, 1);
    }

    private void showStudents(int position) {

        Intent intent = new Intent(this, ActivityStudentNameList.class);
        intent.putExtra("id", surce.get(position).id);
        intent.putExtra("name", surce.get(position).CourseName);
        startActivity(intent);

    }

    private void getCourseData(int courseId) {
        sDown.setRefreshing(true);
        (new PresentCourse(this)).getCourseById(courseId);

    }

    private void showEditDialog(final StCourse course) {

        final Dialog dialog = new Dialog(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_edit_course_data, null, false);
        (txtStartDate = (Button) view.findViewById(R.id.txtStartDateDialogEdit)).setOnClickListener(this);
        (txtEndDate = (Button) view.findViewById(R.id.txtEndDateDialogEdit)).setOnClickListener(this);
        (txtHours = (Button) view.findViewById(R.id.txtStartHoursDialogEdit)).setOnClickListener(this);
        (txtDays = (Button) view.findViewById(R.id.txtDaysDialogEdit)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnSaveDataDialogEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataForServer(course.id, txtStartDate.getText().toString(), txtEndDate.getText().toString(), txtHours.getText().toString(), txtDays.getText().toString(), getRadioButtonData());
                dialog.cancel();
            }
        });
        ((Button) view.findViewById(R.id.btnCancelDialogEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1DialogEdit);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2DialogEdit);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3DialogEdit);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4DialogEdit);
        setUpRadioButtons(course.state);
        txtStartDate.setText(course.startDate);
        txtEndDate.setText(course.endDate);
        txtDays.setText(course.day);
        txtHours.setText(course.hours);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void sendDataForServer(int courseId, String startDate, String endDate, String hours, String days, int state) {

        sDown.setRefreshing(true);
        (new PresentCourse(this)).upDateCourse(courseId, startDate, endDate, hours, days, state);
    }

    private void setUpRadioButtons(int state) {

        switch (state) {
            case 1:
                radioButton1.setChecked(true);
                break;
            case 2:
                radioButton2.setChecked(true);
                break;
            case 3:
                radioButton3.setChecked(true);
                break;
            case 4:
                radioButton4.setChecked(true);
                break;
        }


    }

    private int getRadioButtonData() {

        if (radioButton1.isChecked())
            return 1;
        else if (radioButton2.isChecked())
            return 2;
        else if (radioButton3.isChecked())
            return 3;
        else
            return 4;

    }

    private void showDatePicker(final boolean isStartDate) {

        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance
                (new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                         if (isStartDate) {
                             String sD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             txtStartDate.setText(sD);
                         } else {
                             String eD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             txtEndDate.setText(eD);

                         }
                     }
                 }, now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay());

        datePickerDialog.setThemeDark(true);
        datePickerDialog.show(getFragmentManager(), "tpd");
    }

    private void showTimePicker() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtHours.setText(hourOfDay + ":" + minute);

                    }
                }, 2, 2, true);

        timePickerDialog.show();
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
        intent.putExtra("fileKind", "image");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        sDown.setRefreshing(false);
        if (data == null) {
            sendMessageFCT("خطا!!!");
            return;
        }
        if (requestCode == 2)
            uploadImage(data.getStringExtra("path"));
        if (requestCode == 1)
            uploadVideo(data.getStringExtra("path"));

    }

    private void uploadImage(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("course", surce.get(position).id + "wIzArD.png", path);
    }

    private void uploadVideo(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("newsVideo", surce.get(position).id + "wIzArD.mp4", path);
    }

    @Override
    public void messageFromUpload(String message) {
        dialogProgres.closeProgresBar();
        sendMessageFCT(message);
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {

        if (res.code == 1)
            messageFromUpload("بارگذاری شد بعداز تایید شدن قابل نمایش است");
        else if(res.code == 0)
            messageFromUpload("حداکثر 5 مگابایت");
    }

    @Override
    protected void onResume() {
        if (ActivityStudentNameList.removeWaiting) {
            getData();
            ActivityStudentNameList.removeWaiting = false;
        }
        super.onResume();
    }

    @Override
    public void onRefresh() {
        getData();
    }


    @Override
    public void days(String days) {
        txtDays.setText(days);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtDaysDialogEdit:
                (new DialogDayWeek(this, this)).Show();
                return;
            case R.id.txtStartDateDialogEdit:
                showDatePicker(true);
                return;
            case R.id.txtEndDateDialogEdit:
                showDatePicker(false);
                return;
            case R.id.txtStartHoursDialogEdit:
                showTimePicker();
                break;
        }
    }
}
