package ir.mahoorsoft.app.cityneed.view.activity_account.registering;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityTabagheList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogDayWeek;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogTabaghe;

/**
 * Created by M-gh on 28-Jan-18.
 */

public class ActivityCourseRegistring extends AppCompatActivity implements View.OnClickListener, PresentCourse.OnPresentCourseLitener, PresentUpload.OnPresentUploadListener, DialogTabaghe.OnTabagheItemClick, DialogDayWeek.ReturnDay {
    /*    RadioButton rb1;
        RadioButton rb2;
        RadioButton rb3;
        RadioButton rb4;
        RadioButton rb5;
        RadioButton rb6;
        RadioButton rb7;*/
    TextView txtSubject;
    boolean isUserChanged = true;
    TextView txtTozihat;
    TextView txtMinRange;
    TextView txtMaxRange;
    TextView txtCapacity;
    TextView txtSharayet;
    TextView txtMony;
    Button btnSave;
    Button btnStartDate;
    Button btnEndDate;
    Button btnDays;
    Button btnTime;
    Button btnTabaghe;
    DialogProgres dialogProgres;
    CheckBox cbxPublic;
    CheckBox cbxPrivate;
    String sD = "";
    String eD = "";
    String hours = "";
    String tabaghe = "";
    int id;
    int tabagheId;
    String days = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery_course);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        pointers();
        setFont();
        checkTxtInput();
    }

    private void pointers() {
        txtCapacity = (TextView) findViewById(R.id.txtCapacityRegisteryCourse);
        txtMony = (TextView) findViewById(R.id.txtMonyRegisteryCourse);
        txtMinRange = (TextView) findViewById(R.id.txtMinRange);
        txtMaxRange = (TextView) findViewById(R.id.txtMaxRange);
        txtSubject = (TextView) findViewById(R.id.txtSubjectRegisteryCourse);
        txtTozihat = (TextView) findViewById(R.id.txtTozihatRegisteryCourse);
        txtSharayet = (TextView) findViewById(R.id.txtsharayetRegisteryCourse);

        btnEndDate = (Button) findViewById(R.id.btnEndDateRegisteryCourse);
        btnDays = (Button) findViewById(R.id.btnDaysRejistery);
        btnStartDate = (Button) findViewById(R.id.btnStartDateRegisteryCourse);
        btnSave = (Button) findViewById(R.id.btnSaveRegisteryRegisteryCourse);
        btnTime = (Button) findViewById(R.id.btnChooseTimeRegisteryCourse);
        btnTabaghe = (Button) findViewById(R.id.btnChoseTabaghe);

/*        rb1 = (RadioButton) findViewById(R.id.rbSaturday);
        rb2 = (RadioButton) findViewById(R.id.rbSunday);
        rb3 = (RadioButton) findViewById(R.id.rbMonday);
        rb4 = (RadioButton) findViewById(R.id.rb3);
        rb5 = (RadioButton) findViewById(R.id.rb4);
        rb6 = (RadioButton) findViewById(R.id.rb5);
        rb7 = (RadioButton) findViewById(R.id.rbFriday);
        rb1.setChecked(true);*/

        cbxPublic = (CheckBox) findViewById(R.id.cbxPublicCurceRegisteryCourse);
        cbxPrivate = (CheckBox) findViewById(R.id.cbxSingleCurceRegisteryCourse);
        cbxPublic.setChecked(true);

        btnTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnStartDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);
        btnTabaghe.setOnClickListener(this);
        cbxPrivate.setOnClickListener(this);
        cbxPublic.setOnClickListener(this);
        btnDays.setOnClickListener(this);

    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(this.getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtMaxRange.setTypeface(typeface);
        txtMinRange.setTypeface(typeface);
        txtSharayet.setTypeface(typeface);
        txtCapacity.setTypeface(typeface);
        txtTozihat.setTypeface(typeface);
        txtMony.setTypeface(typeface);
        txtSubject.setTypeface(typeface);
    }

    private void checkTxtInput() {
        txtSharayet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUserChanged) {
                    isUserChanged = false;
                    // txtSharayet.setTextKeepState();
                    txtSharayet.setTextKeepState(CharCheck.faCheck(txtSharayet.getText().toString()));

                } else
                    isUserChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUserChanged) {
                    isUserChanged = false;
                    // txtSharayet.setTextKeepState();
                    txtSubject.setTextKeepState(CharCheck.faCheck(txtSubject.getText().toString()));

                } else
                    isUserChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtTozihat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUserChanged) {
                    isUserChanged = false;
                    // txtSharayet.setTextKeepState();
                    txtTozihat.setTextKeepState(CharCheck.faCheck(txtTozihat.getText().toString()));

                } else
                    isUserChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbxPublicCurceRegisteryCourse:
                if (cbxPublic.isChecked())
                    cbxPrivate.setChecked(false);
                else
                    cbxPrivate.setChecked(true);
                break;

            case R.id.cbxSingleCurceRegisteryCourse:
                if (cbxPrivate.isChecked())
                    cbxPublic.setChecked(false);
                else
                    cbxPublic.setChecked(true);
                break;
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
                (new DialogTabaghe(this, this)).Show();
                break;
            case R.id.btnDaysRejistery:
                (new DialogDayWeek(this, this)).Show();
                break;

        }

    }

    private void showDatePicker(final boolean isStartDate) {

        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance
                (new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                         if (isStartDate) {
                             btnStartDate.setText("تاریخ شروع دوره(" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ")");
                             sD = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                         } else {
                             eD = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                             btnEndDate.setText("تاریخ پایان دوره (" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ")");

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

                        btnTime.setText("ساعت شروع (" + hourOfDay + ":" + minute + ")");
                        hours = hourOfDay + ":" + minute;

                    }
                }, 2, 2, true);
        timePickerDialog.show();
    }

    private void checkData() {
        try {
            Long.parseLong(txtMony.getText().toString().trim());
            Integer.parseInt(txtCapacity.getText().toString().trim());
            Integer.parseInt(txtMinRange.getText().toString().trim());
            Integer.parseInt(txtMaxRange.getText().toString().trim());
            if (tabaghe.length() == 0)
                throw new Exception("لطفا دسته بندی دوره را انتخاب کنید");
            if (sD.length() == 0 || eD.length() == 0)
                throw new Exception("لطفا تاریخ شروع و پایان دوره را انتخاب کنید");
            if (eD.compareTo(sD) == -1)
                throw new Exception("لطفا تاریخ شروع و پایان دوره را صحیح انتخاب کنید");
            if (hours.length() == 0)
                throw new Exception("لطفا ساعت شروع دوره را انتخاب کنید");
            if (Integer.parseInt(txtMaxRange.getText().toString().trim()) < Integer.parseInt(txtMinRange.getText().toString().trim()))
                throw new Exception("رنج سنی صحیح نمی باشد");
            if (txtSharayet.getText() != null && txtSharayet.getText().toString().trim().length() != 0 &&
                    txtTozihat.getText() != null && txtTozihat.getText().toString().trim().length() != 0 &&
                    txtSubject.getText() != null && txtSubject.getText().toString().trim().length() != 0 &&
                    txtMinRange.getText() != null && txtMinRange.getText().toString().trim().length() != 0 &&
                    txtMony.getText() != null && txtMony.getText().toString().trim().length() != 0 &&
                    txtMaxRange.getText() != null && txtMaxRange.getText().toString().trim().length() != 0 &&
                    txtCapacity.getText() != null && txtCapacity.getText().toString().trim().length() != 0 &&
                    days.length() != 0) {
                showDialog("تایید اطلاعات", "از صحت اطلاعات وارد شده مطمعن هستید", "بله", "بررسی");
            } else {
                throw new Exception("لطفا اطلاعات را صحیح وارد کنید");
            }
        } catch (Exception e) {
            showDialog("خطا!", e.getMessage(), "", "قبول");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialogProgres.closeProgresBar();
        if (requestCode == 2) {
            uploadImage(data.getStringExtra("path"));
        }
    }

    private void sendDataForServer() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.addCourse(txtSubject.getText().toString().trim(), tabagheId,
                cbxPublic.isChecked() == true ? 0 : 1,
                Integer.parseInt(txtCapacity.getText().toString().trim()),
                Integer.parseInt(txtMony.getText().toString().trim()),
                txtSharayet.getText().toString().trim(),
                txtTozihat.getText().toString().trim(),
                sD,
                eD,
                days,
                hours,
                Integer.parseInt(txtMinRange.getText().toString().trim()),
                Integer.parseInt(txtMaxRange.getText().toString().trim())
        );
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
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {

    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StHomeListItems> items) {

    }

    private void uploadImage(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("course", id + ".png", path);
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

    @Override
    public void flagFromUpload(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        if (res.code == 0) {
            sendMessageFCT("خطا در بارگذاری لطفا بعدا امتحان کنید.");
        } else if (res.code == 1) {

        } else if (res.code == 2) {
            sendMessageFCT("حجم فایل باید بین یک تا پنج مگابایت باشد");
        }
        this.finish();
    }

    private void showDialog(String title, String message, String btntrue, String btnFalse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btntrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendDataForServer();
                dialog.cancel();
            }
        });
        builder.setNegativeButton(btnFalse, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

/*    private String getDay() {
*//*        String day = "";
        if (rb1.isChecked())
            day = "شنبه";
        else if (rb2.isChecked())
            day = "یکشنبه";
        else if (rb3.isChecked())
            day = "دوشنبه";
        else if (rb4.isChecked())
            day = "سه شنبه";
        else if (rb5.isChecked())
            day = "چهار شنبه";
        else if (rb6.isChecked())
            day = "پنجشنبه";
        else if (rb7.isChecked())
            day = "جمعه";
        return day;*//*
    }*/

    @Override
    public void tabagheInf(String name, int id) {
        tabagheId = id;
        tabaghe = name;
        btnTabaghe.setText(name);
    }

    @Override
    public void days(String days) {
        this.days = days;
        btnDays.setText(days);
        sendMessageFCT(days);
    }
}
