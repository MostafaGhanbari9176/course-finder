package ir.mahoorsoft.app.cityneed.view.activity_account.activity_registering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;

/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityRegistering extends AppCompatActivity implements View.OnClickListener, DialogPrvince.OnDialogPrvinceListener {

    Button btnBack;
    Button bntSave;
    Button btnChosePrvince;
    DialogPrvince dialogPrvince;
    TextView txtPhone;
    TextView txtSubject;
    TextView txtAddress;
    TextView txtTozihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);
        pointers();
        btnChosePrvince.setText(Pref.getStringValue(PrefKey.location, "انتخاب شهر محل سکونت"));
        dialogPrvince = new DialogPrvince(this, this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void pointers() {
        txtTozihat = (TextView) findViewById(R.id.txtTozihat);
        txtPhone = (TextView) findViewById(R.id.txtPhoneRegistery);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtSubject = (TextView) findViewById(R.id.txtSubject);
        btnBack = (Button) findViewById(R.id.btnBack_SumbitInformation);
        bntSave = (Button) findViewById(R.id.btnSaveRegistery);
        btnChosePrvince = (Button) findViewById(R.id.btnChosePrvince);
        bntSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnChosePrvince.setOnClickListener(this);
    }

    private void starterActivitry(Class aClass) {

        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();

    }

    private void reqForData() {
        dialogPrvince.showDialog();
    }

    public static void showMessage(String message) {

        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBack_SumbitInformation:
                starterActivitry(ActivityProfile.class);
                break;

            case R.id.btnChosePrvince:
                reqForData();
                break;

            case R.id.btnSaveRegistery:
                confirmData();
                break;

        }
    }

    private void confirmData() {

        if (txtPhone.getText().toString().trim().length() != 11 &&
                txtSubject.getText().toString().trim().length()!=0 &&
                txtAddress.getText().toString().trim().length()!=0&&
                btnChosePrvince.getText().length()!=0) {



        } else {
            Toast.makeText(this, "لطفا اطلاعات را کامل وصحیح وارد کنید...", Toast.LENGTH_SHORT).show();
        }
    }///barresi


    @Override
    public void locationInformation(String location, int cityId) {

    }
}
