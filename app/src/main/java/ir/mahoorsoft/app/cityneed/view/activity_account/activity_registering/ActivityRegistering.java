package ir.mahoorsoft.app.cityneed.view.activity_account.activity_registering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import ir.mahoorsoft.app.cityneed.presenter.PresentCity;
import ir.mahoorsoft.app.cityneed.presenter.PresentOstan;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterChosePrvince;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;

/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityRegistering extends AppCompatActivity implements View.OnClickListener, DialogPrvince.OnDialogPrvinceListener{

    Button btnBack;
    Button bntSave;
    Button btnChosePrvince;
    TextView txtLocation;
    DialogPrvince dialogPrvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);
        pointers();
        btnChosePrvince.setText(Pref.getStringValue(PrefKey.location,"انتخاب شهر محل سکونت"));
        dialogPrvince = new DialogPrvince(this , this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void pointers() {

        btnBack = (Button) findViewById(R.id.btnBack_SumbitInformation);
        bntSave = (Button) findViewById(R.id.btnSaveRegistery);
        btnChosePrvince = (Button) findViewById(R.id.btnChosePrvince);
        txtLocation = (TextView) findViewById(R.id.txtsetLocation);

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
                saveInformation();
                break;

        }
    }

    private void saveInformation() {

    }///barresi

    @Override
    public void locationInformation() {
        btnChosePrvince.setText(Pref.getStringValue(PrefKey.location,""));
    }
}
