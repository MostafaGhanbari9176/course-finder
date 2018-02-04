package ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.zip.Adler32;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentSmsCode;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class ActivityPhoneConfirm extends AppCompatActivity implements PresentSmsCode.OnPresentSmsCodeListener, PresentUser.OnPresentUserLitener, PresentTeacher.OnPresentTeacherListener {

    int timer = 120;
    Toolbar tlb;
    Button btnConfirmPhone;
    Button btnConfirmCode;
    TextView txtPhone;
    TextView txtTimer;
    TextView txtCode;
    DialogProgres dialogProgres;
    boolean isResponseForCode = false;
    boolean isBtnConfirmPhone = true;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        setContentView(R.layout.activity_phone_confirm);

        dialogProgres = new DialogProgres(this);
        pointers();
        // setSupportActionBar(tlb);
    }

    private void pointers() {
        // btnBack = (Button) findViewById(R.id.btnBackPhoneConfirm);
        tlb = (Toolbar) findViewById(R.id.tlbPhoneConfirm);
        txtCode = (TextView) findViewById(R.id.txtSmsCodeConfirmPhone);
        txtTimer = (TextView) findViewById(R.id.txtTimerConfirmPhone);
        txtPhone = (TextView) findViewById(R.id.txtPhoneConfirmPhone);
        btnConfirmPhone = (Button) findViewById(R.id.btnConfirmPhoneConfirmPhone);
        btnConfirmCode = (Button) findViewById(R.id.btnConfirmCodeConfirmPhone);
        txtPhone.setText("");
        txtCode.setText("");
        btnConfirmPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBtnConfirmPhone) {
                    checkPhone(txtPhone.getText().toString());
                    timer = 120;
                } else {
                    timer = 0;
                    btnConfirmCode.setEnabled(false);
                    txtCode.setText("");
                    txtPhone.setEnabled(true);
                    btnConfirmPhone.setText("تایید شماره همراه");
                    isBtnConfirmPhone = true;
                }
            }
        });
        btnConfirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCode(txtPhone.getText().toString(), txtCode.getText().toString());

            }
        });

        txtTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageFScT("ok");
            }
        });
    }

    private void checkPhone(String phone) {
        phone.trim();
        try {
            Long.parseLong(phone);
            if (phone.length() != 11) {
                showAlertDialog("خطا", "لطفا شماره همراه را صحیح وارد کنید", "", "قبول");
            } else {
                sendPhoneForserver(phone);
            }
        } catch (Exception e) {
            showAlertDialog("خطا", "لطفا شماره همراه را صحیح وارد کنید", "", "قبول");
        }


    }

    private void checkCode(String phone, String code) {
        phone.trim();
        code.trim();
        try {
            Integer.parseInt(code);
            PresentSmsCode presentSms = new PresentSmsCode(this);
            presentSms.checkedSmsCode(phone, Integer.parseInt(code));
            isResponseForCode = true;
        } catch (Exception e) {
            showAlertDialog("خطا", "لطفا کد را صحیح وارد کنید", "", "قبول");
        }

    }

    private void sendPhoneForserver(String phone) {
        dialogProgres.showProgresBar();
        isResponseForCode = false;
        PresentSmsCode p = new PresentSmsCode(this);
        p.createAndSaveSmsCode(phone);
    }


    @Override
    public void confirmSmsCode(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag && !isResponseForCode)
            showAlertDialog("خطا", "لطفا چند لحظه بعد امتحان کنید", "", "قبول");

        if (flag) {
            btnConfirmPhone.setText("تغیر شماره همراه");
            txtPhone.setEnabled(false);
            btnConfirmCode.setEnabled(true);
            isBtnConfirmPhone = false;
            timer();
        }
        if (flag && isResponseForCode) {
            Pref.saveBollValue(PrefKey.IsLogin, flag);//***************************************************************
            Pref.saveStringValue(PrefKey.phone, txtPhone.getText().toString().trim());
            showDialogForName();
        } else if (!flag && isResponseForCode)
            showAlertDialog("خطا", "کده وارد شده صحیح نیست!!!", "", "قبول");

    }

    private void timer() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (timer >= 1) {
                    timer--;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateTimerView(timer);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void updateTimerView(final int i) {
        handler.post(new TimerTask() {
            @Override
            public void run() {
                txtTimer.setText(String.valueOf(i));
                if (i <= 1) {
                    // btnConfirmCode.setEnabled(false);
                    // btnConfirmPhone.setEnabled(true);
                    //  sendMessageFScT("لطفا از صحت شماره همراه مطمعن شوید");
                    txtTimer.setText("00");
                    // isResponseForCode = false;
                }
            }
        });
    }

    @Override
    public void sendMessageFScT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmSmsCodeAndExistUser(int code) {
        if (code == 2) {
            dialogProgres.showProgresBar();
            //***************************************************************
            Pref.saveStringValue(PrefKey.phone, txtPhone.getText().toString().trim());
            PresentUser presentUser = new PresentUser(this);
            presentUser.getUser(Pref.getStringValue(PrefKey.phone, ""));
        } else if (code == 3) {
            dialogProgres.showProgresBar();
            Pref.saveStringValue(PrefKey.phone, txtPhone.getText().toString().trim());
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.getTeacher();
        }
    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmUser(boolean flag) {

    }

    @Override
    public void onReceiveUser(ArrayList<StUser> users) {

        dialogProgres.closeProgresBar();
        Pref.saveBollValue(PrefKey.IsLogin, true);
        Pref.saveStringValue(PrefKey.userName, users.get(0).name);
        Pref.saveStringValue(PrefKey.userFamily, users.get(0).family);
        Pref.saveStringValue(PrefKey.location, users.get(0).location);
        Pref.saveIntegerValue(PrefKey.cityId, users.get(0).cityId);
        Pref.saveIntegerValue(PrefKey.userTypeMode, users.get(0).type);
        next();
    }

    @Override
    public void sendMessageFTT(String message) {
        sendMessageFUT(message);
    }

    @Override
    public void confirmTeacher(boolean flag) {

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> teacher) {

        dialogProgres.closeProgresBar();
        Pref.saveBollValue(PrefKey.IsLogin, true);
        Pref.saveIntegerValue(PrefKey.cityId, teacher.get(0).cityId);
        Pref.saveStringValue(PrefKey.location, teacher.get(0).location);
        Pref.saveStringValue(PrefKey.landPhone, teacher.get(0).landPhone);
        Pref.saveStringValue(PrefKey.subject, teacher.get(0).subject);
        Pref.saveStringValue(PrefKey.address, teacher.get(0).address);
        Pref.saveIntegerValue(PrefKey.madrak, teacher.get(0).madrak);
        Pref.saveIntegerValue(PrefKey.userTypeMode, teacher.get(0).type == 0 ? 1 : 2);
        next();
    }

    private void showAlertDialog(String title, String message, String buttonTrue, String btnFalse) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton(buttonTrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isResponseForCode = false;
                isBtnConfirmPhone = true;

                checkPhone(txtPhone.getText().toString());
                dialog.cancel();

            }
        });
        alertDialog.setNegativeButton(btnFalse, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void showDialogForName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("مرحله آخر");
        builder.setMessage("لطفا نام و نام خانوادگی خود را وارد کنید از آن برای ثبت نام شما در دوره انتخابی استفاده می شود.");
        final EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        builder.setView(editText);
        builder.setPositiveButton("قبول",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText().toString() != null && editText.getText().toString().trim().length() != 0) {
                            Pref.saveStringValue(PrefKey.userName, editText.getText().toString().trim());
                            dialog.cancel();
                            next();
                        }else{

                        }

                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    private void next() {
        Intent intent = new Intent(this, ActivityProfile.class);
        startActivity(intent);
        this.finish();
    }
}
