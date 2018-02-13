package ir.mahoorsoft.app.cityneed.view.activity_account.registering;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.MapsActivity;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.UploadRes;
import ir.mahoorsoft.app.cityneed.model.uploadFile.ProgressRequestBody;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityTeacherRegistering extends AppCompatActivity implements View.OnClickListener, PresentTeacher.OnPresentTeacherListener, PresentUpload.OnPresentUploadListener {
    boolean locationIsSet = false;
    boolean isUserChanged = true;
    Button btnBack;
    Button btnSave;
    Button btnUploadImag;
    Button btnLocation;
    TextView txtPhone;
    TextView txtSubject;
    //TextView txtAddress;
    TextView txtTozihat;
    CheckBox cbxPublic;
    CheckBox cbxPrivate;

    DialogProgres dialogProgres;
    int cityId;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);
        pointers();
        dialogProgres = new DialogProgres(this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void pointers() {
        cbxPrivate = (CheckBox) findViewById(R.id.cbxSingleCurce);
        cbxPublic = (CheckBox) findViewById(R.id.cbxPublicCurce);
        txtTozihat = (TextView) findViewById(R.id.txtTozihat);
        txtPhone = (TextView) findViewById(R.id.txtPhoneRegistery);
        //txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtSubject = (TextView) findViewById(R.id.txtSubject);
        btnBack = (Button) findViewById(R.id.btnBackRegistery);
        btnUploadImag = (Button) findViewById(R.id.btnUploadImg);
        btnSave = (Button) findViewById(R.id.btnSaveRegistery);
        btnLocation = (Button) findViewById(R.id.btnLocation);

        inPutTVCheck();

        btnSave.setOnClickListener(this);
        cbxPrivate.setOnClickListener(this);
        cbxPublic.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnUploadImag.setOnClickListener(this);
    }

    private void inPutTVCheck() {
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

    private void starterActivitry(Class aClass) {

        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();

    }

    public static void showMessage(String message) {

        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBackRegistery:
                starterActivitry(ActivityProfile.class);
                break;

            case R.id.btnLocation:

                Intent intent = new Intent(this, MapsActivity.class);
                startActivityForResult(intent, 2);
                break;

            case R.id.btnSaveRegistery:
                getData();
                break;

            case R.id.btnUploadImg:
                choseFile();
                break;

            case R.id.cbxPublicCurce:
                if (cbxPublic.isChecked())
                    cbxPrivate.setChecked(false);
                else
                    cbxPrivate.setChecked(true);
                break;

            case R.id.cbxSingleCurce:
                if (cbxPrivate.isChecked())
                    cbxPublic.setChecked(false);
                else
                    cbxPublic.setChecked(true);
                break;

        }
    }

    private void getData() {


        try {
            if (Pref.getStringValue(PrefKey.lat, "").length() == 0)
                throw new Exception("لطفا موقعیت مکانی خود را تعیین کنید.");
            Long.parseLong(txtPhone.getText().toString().trim());
            if (txtPhone.getText().toString().trim().length() != 11)
                throw new Exception("لطفا شماره تماس خود را صحیح وارد کنید.");

            if (txtSubject.getText().toString().trim().length() != 0 &&
                    //txtAddress.getText().toString().trim().length() != 0 &&
                    txtTozihat.getText().toString().trim().length() != 0 &&
                    btnLocation.getText().length() != 0) {
                if (cbxPrivate.isChecked() || cbxPublic.isChecked()) {
                    showDialog("تایید اطلاعات", "از صحت اطلاعات وارد شده مطمعن هستید؟", "بله", "بررسی");
                } else {
                    showDialog("خطا", "لطفا نوع آموزش خود را مشخص کنید", "", "قبول");

                }
            } else {
                showDialog("خطا", "لطفا اطلاعات را کامل وصحیح وارد کنید...", "", "قبول");

            }
        } catch (Exception e) {
            showDialog("خطا", e.getMessage(), "", "قبول");
        }
    }///barresi

    private void sendDataForServer() {
        dialogProgres.showProgresBar();
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.addTeacher(txtPhone.getText().toString().trim(), txtSubject.getText().toString().trim(), txtTozihat.getText().toString().trim(), cbxPrivate.isChecked() ? 1 : 0, Pref.getStringValue(PrefKey.lat, ""), Pref.getStringValue(PrefKey.lon, ""));

    }

    private void choseFile() {
        Intent intent = new Intent(this, ActivityFiles.class);
        intent.putExtra("isImage", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK) {
                uploadFile(data.getStringExtra("path"));
            }
            if (requestCode == 2 && resultCode == RESULT_OK) {
                locationIsSet = true;
            }

        } catch (Exception ex) {
            Toast.makeText(ActivityTeacherRegistering.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag) {
            Pref.saveIntegerValue(PrefKey.cityId, cityId);
            Pref.saveStringValue(PrefKey.location, btnLocation.getText().toString().trim());
            Pref.saveStringValue(PrefKey.landPhone, txtPhone.getText().toString().trim());
            Pref.saveStringValue(PrefKey.subject, txtSubject.getText().toString().trim());
            //  Pref.saveStringValue(PrefKey.address, txtAddress.getText().toString().trim());
            Pref.saveIntegerValue(PrefKey.madrak, 0);
            Pref.saveIntegerValue(PrefKey.userTypeMode, cbxPublic.isChecked() ? 1 : 2);
            starterActivitry(ActivityProfile.class);
        } else
            sendMessageFTT("خطا");
    }

    private void uploadFile(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("teacher", Pref.getStringValue(PrefKey.phone, "") + ".png", path);

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {

    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {

    }

    @Override
    public void messageFromUpload(String message) {
        //dialogProgres.closeProgresBar();
        sendMessageFTT(message);
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {

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


}
