package ir.mahoorsoft.app.cityneed.view.activity_account.registering;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
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
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.UploadRes;
import ir.mahoorsoft.app.cityneed.model.uploadFile.ProgressRequestBody;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
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

public class ActivityTeacherRegistering extends AppCompatActivity implements View.OnClickListener, DialogPrvince.OnDialogPrvinceListener, PresentTeacher.OnPresentTeacherListener, PresentUpload.OnPresentUploadListener {

    Button btnBack;
    Button btnSave;
    Button btnUploadImag;
    Button btnLocation;
    DialogPrvince dialogPrvince;
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
        dialogPrvince = new DialogPrvince(this, this);
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


        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnUploadImag.setOnClickListener(this);
    }

    private void starterActivitry(Class aClass) {

        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();

    }

    private void reqForData() {

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
                startActivity(intent);
                break;

            case R.id.btnSaveRegistery:
                getData();
                break;

            case R.id.btnUploadImg:
                choseFile();
                break;

        }
    }

    private void getData() {

        if (txtPhone.getText().toString().trim().length() != 11 &&
                txtSubject.getText().toString().trim().length() != 0 &&
                //txtAddress.getText().toString().trim().length() != 0 &&
                txtTozihat.getText().toString().trim().length() != 0 &&
                btnLocation.getText().length() != 0) {
            if (cbxPrivate.isChecked() || cbxPublic.isChecked()) {
                dialogProgres.showProgresBar();
                PresentTeacher presentTeacher = new PresentTeacher(this);
                presentTeacher.addTeacher(txtSubject.getText().toString().trim(), "", txtSubject.getText().toString().trim(), txtTozihat.getText().toString().trim(), cbxPrivate.isChecked() ? 1 : 0, cityId);
            } else {
                Toast.makeText(this, "لطفا نوع آموزش خود را مشخص کنید", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "لطفا اطلاعات را کامل وصحیح وارد کنید...", Toast.LENGTH_SHORT).show();
        }
    }///barresi

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

        } catch (Exception ex) {
            Toast.makeText(ActivityTeacherRegistering.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void locationInformation(String location, int cityId) {
        btnLocation.setText(location);
        this.cityId = cityId;
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

    private void uploadFile(String path){
        dialogProgres = new DialogProgres(this,"درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("teacher", Pref.getStringValue(PrefKey.phone, "") + ".png", path);

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {

    }
    @Override
    public void messageFromUpload(String message) {
        //dialogProgres.closeProgresBar();
        sendMessageFTT(message);
    }
}
