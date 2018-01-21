package ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimerTask;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentSmsCode;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class ActivityPhoneConfirm extends AppCompatActivity implements PresentSmsCode.OnPresentSmsCodeListener {

    Toolbar tlb;
    Button btnConfirmPhone;
    Button btnConfirmCode;
    TextView txtName;
    TextView txtFamily;
    TextView txtPhone;
    TextView txtTimer;
    TextView txtCode;
    DialogProgres dp;
    PresentSmsCode p;
    boolean isResponseForCode = false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        setContentView(R.layout.activity_phone_confirm);
        p = new PresentSmsCode(this);
        dp = new DialogProgres(this);
        pointers();
        // setSupportActionBar(tlb);
    }

    private void pointers() {
        // btnBack = (Button) findViewById(R.id.btnBackPhoneConfirm);
        tlb = (Toolbar) findViewById(R.id.tlbPhoneConfirm);
        txtCode = (TextView) findViewById(R.id.txtSmsCodeConfirmPhone);
        txtFamily = (TextView) findViewById(R.id.txtFamilyConfirmPhone);
        txtName = (TextView) findViewById(R.id.txtNameConfirmPhone);
        txtTimer = (TextView) findViewById(R.id.txtTimerConfirmPhone);
        txtPhone = (TextView) findViewById(R.id.txtPhoneConfirmPhone);
        btnConfirmPhone = (Button) findViewById(R.id.btnConfirmPhoneConfirmPhone);
        btnConfirmCode = (Button) findViewById(R.id.btnConfirmCodeConfirmPhone);
        txtPhone.setText("");
        txtName.setText("");
        txtFamily.setText("");
        txtCode.setText("");
        btnConfirmPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhone(txtPhone.getText().toString());

            }
        });
        btnConfirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCode(txtPhone.getText().toString(), txtCode.getText().toString());

            }
        });
    }

    private void checkPhone(String phone) {
        phone.trim();
        if (phone.length() != 11) {
            sendMessageFScT("لطفا شماره همراه را صحیح وارد کنید");
        } else {
            txtPhone.setEnabled(false);
            txtName.setEnabled(false);
            txtFamily.setEnabled(false);
            sendPhoneForserver(phone);
        }

    }

    private void checkCode(String phone, String code) {
        phone.trim();
        code.trim();
        isResponseForCode = true;
        p.checkedSmsCode(phone, Integer.parseInt(code));
    }

    private void sendPhoneForserver(String phone) {
        dp.showProgresBar();
        isResponseForCode = false;
        PresentSmsCode p = new PresentSmsCode(this);
        p.createAndSaveSmsCode(phone);
    }


    @Override
    public void confirmSmsCode(boolean flag) {
        dp.closeProgresBar();
        if (!flag)
            sendMessageFScT("خطا, لطفا چند لحظه بعد امتحان کنید");
        if (flag) {
            btnConfirmCode.setEnabled(true);
            btnConfirmPhone.setEnabled(false);
            timer();
        }
        if (flag && isResponseForCode) {
            Pref.saveBollValue(PrefKey.IsLogin, flag);
            Pref.saveStringValue(PrefKey.phone, txtPhone.getText().toString().trim());
            Pref.saveStringValue(PrefKey.userName, txtName.getText().toString().trim());
            Pref.saveStringValue(PrefKey.userFamily, txtFamily.getText().toString().trim());

            Intent intent = new Intent(this, ActivityProfile.class);
            startActivity(intent);
            this.finish();
        } else if (!flag && isResponseForCode)
            sendMessageFScT("خطا,کد صحیح نیست");

    }

    private void timer(){
        final int[] i = {120};
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (i[0] >=0){
                    i[0]--;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateTimerView(i);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void updateTimerView(final int[] i){
        handler.post(new TimerTask() {
            @Override
            public void run() {
                txtTimer.setText(String.valueOf(i[0]));
                if(i[0]<=0 && Pref.getBollValue(PrefKey.IsLogin,false)) {
                    btnConfirmCode.setEnabled(false);
                    btnConfirmPhone.setEnabled(true);
                    sendMessageFScT("لطفا از صحت شماره همراه مطمعن شوید");
                    txtTimer.setText("00");
                    isResponseForCode = false;
                }
            }
        });
    }
    @Override
    public void sendMessageFScT(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmSmsCodeAndExistUser(int code) {

    }
}
