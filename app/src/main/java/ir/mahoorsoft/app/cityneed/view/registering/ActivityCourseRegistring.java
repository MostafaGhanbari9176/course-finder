package ir.mahoorsoft.app.cityneed.view.registering;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import ir.mahoorsoft.app.cityneed.model.CheckedSTatuse;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogDayWeek;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogGrouping;

/**
 * Created by M-gh on 28-Jan-18.
 */

public class ActivityCourseRegistring extends AppCompatActivity implements View.OnClickListener, PresentCourse.OnPresentCourseLitener, PresentUpload.OnPresentUploadListener, DialogGrouping.OnTabagheItemClick, DialogDayWeek.ReturnDay {
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
    Toolbar tlb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery_course);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this, false);
        pointers();
        setFont();
        checkTxtInput();
    }

    private void pointers() {
        tlb = (Toolbar) findViewById(R.id.tlbCourseRegistery);
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("ثبت دوره جدید");
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
                if (btnSave.getText().toString().equals("ادامه"))
                    this.finish();
                else
                    checkData();
                break;
            case R.id.btnChooseTimeRegisteryCourse:
                showTimePicker();
                break;
            case R.id.btnChoseTabaghe:
                (new DialogGrouping(this, this)).Show();
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
                             sD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             btnStartDate.setText("تاریخ شروع دوره(" + sD + ")");
                         } else {
                             eD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             btnEndDate.setText("تاریخ پایان دوره (" + eD + ")");

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

        if (TextUtils.isEmpty(txtSubject.getText().toString())) {
            txtSubject.setError("لطفا تکمیل کنید");
            txtSubject.requestFocus();
        } else if (TextUtils.isEmpty(txtSharayet.getText().toString())) {
            txtSharayet.setError("لطفا تکمیل کنید");
            txtSharayet.requestFocus();
        } else if (TextUtils.isEmpty(txtMony.getText().toString())) {
            txtMony.setError("لطفا تکمیل کنید");
            txtMony.requestFocus();
        } else if (TextUtils.isEmpty(txtMaxRange.getText().toString())) {
            txtMaxRange.setError("لطفا تکمیل کنید");
            txtMaxRange.requestFocus();
        } else if (TextUtils.isEmpty(txtMinRange.getText().toString())) {
            txtMinRange.setError("لطفا تکمیل کنید");
            txtMinRange.requestFocus();
        } else if (TextUtils.isEmpty(txtCapacity.getText().toString())) {
            txtCapacity.setError("لطفا تکمیل کنید");
            txtCapacity.requestFocus();
        } else if (TextUtils.isEmpty(txtTozihat.getText().toString())) {
            txtTozihat.setError("لطفا تکمیل کنید");
            txtTozihat.requestFocus();
        } else {
            try {
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
                showDialog("تایید اطلاعات", "از صحت اطلاعات وارد شده مطمعن هستید", "بله", "بررسی");
            } catch (Exception e) {
                showDialog("خطا!", e.getMessage(), "", "قبول");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        (new CheckedSTatuse()).sendEmail(id + "");
        dialogProgres.closeProgresBar();
        btnSave.setText("ادامه");
        this.id = id;
        shoDialog();

    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {

    }


    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    private void uploadImage(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری", false);
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
        if (res.code == 1) {
            sendMessageFCT("بارگذاری شد");
        } else {
            sendMessageFCT("خطا در بارگذاری");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}

