package ir.mahoorsoft.app.cityneed.view.activity_account.registering;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;


import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityTabagheList;

/**
 * Created by M-gh on 28-Jan-18.
 */

public class ActivityCourseRegistring extends AppCompatActivity implements View.OnClickListener {

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
    Button btnUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery_course);
        G.activity = this;
        G.context = this;
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
        btnUpload = (Button) findViewById(R.id.btnUploadImgRegisteryCourse);

        btnUpload.setOnClickListener(this);
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
            case R.id.btnUploadImgRegisteryCourse:
                break;
            case R.id.btnChoseTabaghe:
                Intent intent = new Intent(this, ActivityTabagheList.class);
                startActivityForResult(intent,1);
                break;

        }

    }

    private void showDatePicker(final boolean isStartDate) {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance
                (new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                         if(isStartDate)
                             btnStartDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                         else
                             btnEndDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
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

    private void checkData(){
        if(txtSharayet.getText() != null && txtSharayet.getText().toString().trim().length() !=0 &&
                txtTozihat.getText() != null && txtTozihat.getText().toString().trim().length() !=0 &&
                txtSubject.getText() != null && txtSubject.getText().toString().trim().length() !=0 &&
                txtRange.getText() != null && txtRange.getText().toString().trim().length() !=0 &&
                txtMony.getText() != null && txtMony.getText().toString().trim().length() !=0 &&
                txtDay.getText() != null && txtDay.getText().toString().trim().length() !=0 &&
                txtCapacity.getText() != null && txtCapacity.getText().toString().trim().length() !=0){
            sendDataForServer();
        }
        else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnTabaghe.setText(data.getIntExtra("id",-2)+"");
    }

    private void sendDataForServer(){


    }


}

