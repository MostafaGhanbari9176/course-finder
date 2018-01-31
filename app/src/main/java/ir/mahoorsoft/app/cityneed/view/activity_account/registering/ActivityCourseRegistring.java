package ir.mahoorsoft.app.cityneed.view.activity_account.registering;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;


import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityTabagheList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 28-Jan-18.
 */

public class ActivityCourseRegistring extends AppCompatActivity implements View.OnClickListener, PresentCourse.OnPresentCourseLitener, PresentUpload.OnPresentUploadListener {

    TextView txtSubject;
    TextView txtTozihat;
    TextView txtRange;
    TextView txtCapacity;
    TextView txtSharayet;
    TextView txtMony;
    Button btnSave;
    Button btnStartDate;
    Button btnEndDate;
    Button btnTime;
    Button btnTabaghe;
    TextView txtDay;
    String teacherId = Pref.getStringValue(PrefKey.phone, "");
    DialogProgres dialogProgres;
    CheckBox publik;
    CheckBox privat;
    String path;
    int id;
    int tabagheId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery_course);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        pointers();
    }

    private void pointers() {
        txtCapacity = (TextView) findViewById(R.id.txtCapacityRegisteryCourse);
        txtMony = (TextView) findViewById(R.id.txtMonyRegisteryCourse);
        txtRange = (TextView) findViewById(R.id.txtOldRangeRegisteryCourse);
        txtSubject = (TextView) findViewById(R.id.txtSubjectRegisteryCourse);
        txtTozihat = (TextView) findViewById(R.id.txtTozihatRegisteryCourse);
        txtSharayet = (TextView) findViewById(R.id.txtsharayetRegisteryCourse);

        txtDay = (TextView) findViewById(R.id.txtDayRegisteryCourse);
        btnEndDate = (Button) findViewById(R.id.btnEndDateRegisteryCourse);
        btnStartDate = (Button) findViewById(R.id.btnStartDateRegisteryCourse);
        btnSave = (Button) findViewById(R.id.btnSaveRegisteryRegisteryCourse);
        btnTime = (Button) findViewById(R.id.btnChooseTimeRegisteryCourse);
        btnTabaghe = (Button) findViewById(R.id.btnChoseTabaghe);


        publik = (CheckBox) findViewById(R.id.cbxPublicCurceRegisteryCourse);
        privat = (CheckBox) findViewById(R.id.cbxSingleCurceRegisteryCourse);


        btnTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnStartDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);
        btnTabaghe.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btnEndDateRegisteryCourse:
                showDatePicker(false);
                break;
            case R.id.btnStartDateRegisteryCourse:
                showDatePicker(true);
                break;
            case R.id.btnSaveRegisteryRegisteryCourse:
                checkData();
                break;
            case R.id.btnChooseTimeRegisteryCourse:
                showTimePicker();
                break;
            case R.id.btnChoseTabaghe:
                dialogProgres.showProgresBar();
                Intent intent = new Intent(this, ActivityTabagheList.class);
                startActivityForResult(intent, 1);
                break;

        }

    }

    private void showDatePicker(final boolean isStartDate) {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance
                (new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                         if (isStartDate)
                             btnStartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                         else
                             btnEndDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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

                        btnTime.setText(hourOfDay + ":" + minute);
                    }
                }, 2, 2, true);
        timePickerDialog.show();
    }

    private void checkData() {
        if (txtSharayet.getText() != null && txtSharayet.getText().toString().trim().length() != 0 &&
                txtTozihat.getText() != null && txtTozihat.getText().toString().trim().length() != 0 &&
                txtSubject.getText() != null && txtSubject.getText().toString().trim().length() != 0 &&
                txtRange.getText() != null && txtRange.getText().toString().trim().length() != 0 &&
                txtMony.getText() != null && txtMony.getText().toString().trim().length() != 0 &&
                txtDay.getText() != null && txtDay.getText().toString().trim().length() != 0 &&
                txtCapacity.getText() != null && txtCapacity.getText().toString().trim().length() != 0) {
            sendDataForServer();
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialogProgres.closeProgresBar();
        if (requestCode == 1) {
            tabagheId = data.getIntExtra("id", -2);
            btnTabaghe.setText(data.getStringExtra("name"));
        }
        else if(requestCode == 2){
            uploadImage(data.getStringExtra("path"));
        }
    }

    private void sendDataForServer() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.addCourse(teacherId,
                txtSubject.getText().toString().trim(),
                tabagheId, publik.isChecked() ? 0 : 1,
                Integer.parseInt(txtCapacity.getText().toString().trim()),
                Integer.parseInt(txtMony.getText().toString().trim()),
                txtSharayet.getText().toString().trim(),
                txtTozihat.getText().toString().trim(),
                btnStartDate.getText().toString().trim(),
                btnEndDate.getText().toString().trim(),
                txtDay.getText().toString().trim(),
                btnTime.getText().toString().trim(),
                Integer.parseInt(txtRange.getText().toString().trim()));

    }


    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {
        dialogProgres.closeProgresBar();
        this.id = id;
        shoDialog();
    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course) {

    }

    private void uploadImage(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("course",id+".png", path);
    }

    private void shoDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCourseRegistring.this);
        builder.setTitle("بارگذاری عکس");
        builder.setMessage("آیا مایل به بارگذاری عکسی مرتبط با موضوع دوره خود هستید.");
        builder.setPositiveButton("قبول", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                selectImage();
            }
        });
        builder.setNegativeButton("رد کردن", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ActivityCourseRegistring.this.finish();
            }
        });
        builder.show();


    }

    private void selectImage() {
        Intent intent = new Intent(this, ActivityFiles.class);
        intent.putExtra("isImage", true);
        startActivityForResult(intent, 2);
    }


    @Override
    public void messageFromUpload(String message) {
        sendMessageFCT(message);
        this.finish();
    }
}

