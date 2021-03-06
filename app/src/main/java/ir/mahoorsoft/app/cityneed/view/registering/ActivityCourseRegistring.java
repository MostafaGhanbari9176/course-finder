package ir.mahoorsoft.app.cityneed.view.registering;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
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
    TextView teacherName;
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
    String hours = "???????? ????????";
    String tabaghe = "";
    int id;
    int tabagheId;
    String days = "???????? ????????";
    Toolbar tlb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery_course);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this, false);
        pointers();


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
        getSupportActionBar().setTitle("?????? ???????? ????????");
        txtCapacity = (TextView) findViewById(R.id.txtCapacityRegisteryCourse);
        txtMony = (TextView) findViewById(R.id.txtMonyRegisteryCourse);
        teacherName = (TextView) findViewById(R.id.txtTeacherNameRegisteryCourse);
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
                if (btnSave.getText().toString().equals("??????????"))
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
                             btnStartDate.setText("?????????? ???????? ????????(" + sD + ")");
                         } else {
                             eD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             btnEndDate.setText("?????????? ?????????? ???????? (" + eD + ")");

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

                        btnTime.setText("???????? ???????? (" + hourOfDay + ":" + minute + ")");
                        hours = hourOfDay + ":" + minute;

                    }
                }, 2, 2, true);

        timePickerDialog.show();
    }

    private void checkData() {

        if (TextUtils.isEmpty(txtSubject.getText().toString())) {
            txtSubject.setError("???????? ?????????? ????????");
            txtSubject.requestFocus();
        } else if (TextUtils.isEmpty(teacherName.getText().toString())) {
            teacherName.setError("???????? ?????????? ????????");
            teacherName.requestFocus();
        } else if (TextUtils.isEmpty(txtSharayet.getText().toString())) {
            txtSharayet.setError("???????? ?????????? ????????");
            txtSharayet.requestFocus();
        } else if (TextUtils.isEmpty(txtMony.getText().toString())) {
            txtMony.setError("???????? ?????????? ????????");
            txtMony.requestFocus();
        } else if (TextUtils.isEmpty(txtMaxRange.getText().toString())) {
            txtMaxRange.setError("???????? ?????????? ????????");
            txtMaxRange.requestFocus();
        } else if (TextUtils.isEmpty(txtMinRange.getText().toString())) {
            txtMinRange.setError("???????? ?????????? ????????");
            txtMinRange.requestFocus();
        } else if (TextUtils.isEmpty(txtCapacity.getText().toString())) {
            txtCapacity.setError("???????? ?????????? ????????");
            txtCapacity.requestFocus();
        } else if (TextUtils.isEmpty(txtTozihat.getText().toString())) {
            txtTozihat.setError("???????? ?????????? ????????");
            txtTozihat.requestFocus();
        } else {
            try {
                if (tabaghe.length() == 0)
                    throw new Exception("???????? ???????? ???????? ???????? ???? ???????????? ????????");
                if (sD.length() == 0 || eD.length() == 0)
                    throw new Exception("???????? ?????????? ???????? ?? ?????????? ???????? ???? ???????????? ????????");
                if (eD.compareTo(sD) == -1)
                    throw new Exception("???????? ?????????? ???????? ?? ?????????? ???????? ???? ???????? ???????????? ????????");
                if (Integer.parseInt(txtMaxRange.getText().toString().trim()) <= Integer.parseInt(txtMinRange.getText().toString().trim()))
                    throw new Exception("?????? ?????? ???????? ?????? ????????");
                showDialog("?????????? ??????????????", "???? ?????? ?????????????? ???????? ?????? ?????????? ??????????", "??????", "??????????");
            } catch (Exception e) {
                showDialog("??????!", e.getMessage(), "", "????????");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialogProgres.closeProgresBar();
        if (data == null) {
            sendMessageFCT("??????!!!");
            return;
        }
        if (requestCode == 2 && data != null) {
            uploadImage(data.getStringExtra("path"));
        } else
            sendMessageFCT("??????!!!");
    }

    private void sendDataForServer() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.addCourse(txtSubject.getText().toString().trim(),
                teacherName.getText().toString().trim(),
                tabagheId,
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
        btnSave.setText("??????????");
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
        dialogProgres = new DialogProgres(this, "?????????? ????????????????", false);
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("course", id + ".png", path);
    }

    private void shoDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCourseRegistring.this);
        builder.setTitle("???????????????? ??????");
        builder.setMessage("?????? ???????? ???? ???????????????? ???????? ?????????? ???? ?????????? ???????? ?????? ??????????.");
        builder.setPositiveButton("????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                selectImage();
            }
        });
        builder.setNegativeButton("???? ????????", new DialogInterface.OnClickListener() {
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
        intent.putExtra("fileKind", "image");
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
            sendMessageFCT("???????????????? ????");
        } else {
            sendMessageFCT("?????? ???? ????????????????");
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

