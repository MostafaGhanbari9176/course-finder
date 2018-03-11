package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentSmsCode;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class FragmentEmailConfirm extends Fragment implements View.OnClickListener, PresentSmsCode.OnPresentSmsCodeListener, PresentUser.OnPresentUserLitener, PresentTeacher.OnPresentTeacherListener {

    View view;
    boolean isUserChanged = true;

    LinearLayout llName;
    Button btnConfirmEmail;
    Button btnConfirmCode;
    Button btnResend;
    TextView txtEmail;
    TextView txtName;
    TextView txtSubject;
    TextView txtCode;
    DialogProgres dialogProgres;
    boolean isLogIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_email, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        pointers();
        setFont();
        startDialog();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtName.setTypeface(typeface);
        btnResend.setTypeface(typeface);
        btnConfirmCode.setTypeface(typeface);
        btnConfirmEmail.setTypeface(typeface);
        txtCode.setTypeface(typeface);
        txtEmail.setTypeface(typeface);
        txtSubject.setTypeface(typeface);

    }

    private void pointers() {
        // btnBack = (Button) findViewById(R.id.btnBackPhoneConfirm);

        txtCode = (TextView) view.findViewById(R.id.txtSmsCodeConfirmEmail);
        txtCode.setEnabled(false);
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectEmailConfirm);
        txtName = (TextView) view.findViewById(R.id.txtNameConfirmEmail);
        txtEmail = (TextView) view.findViewById(R.id.txtEmailConfirmٍEmail);
        btnConfirmEmail = (Button) view.findViewById(R.id.btnConfirmEmailConfirmEmail);
        btnConfirmCode = (Button) view.findViewById(R.id.btnConfirmCodeConfirmEmail);
        btnResend = (Button) view.findViewById(R.id.btnResendEmail);
        llName = (LinearLayout) view.findViewById(R.id.llNameEmailConfirm);
        txtEmail.setText("");
        txtCode.setText("");
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUserChanged) {
                    isUserChanged = false;
                    // txtSharayet.setTextKeepState();
                    txtName.setTextKeepState(CharCheck.faCheck(txtName.getText().toString()));

                } else
                    isUserChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnConfirmEmail.setOnClickListener(this);
        btnConfirmCode.setOnClickListener(this);
        btnResend.setOnClickListener(this);

    }

    private void checkeMAIL(String phone) {
        if (!isLogIn && txtName.getText().length() == 0) {
            showAlertDialog("خطا", "لطفا نام خود را صحیح وارد کنید", "", "قبول");
            return;
        }
        try {
            Long.parseLong(phone.trim());
            if (phone.trim().length() != 11) {
                showAlertDialog("خطا", "لطفا شماره همراه را صحیح وارد کنید", "", "قبول");
            } else {
                sendEmailForserver(phone);
            }
        } catch (Exception e) {
            showAlertDialog("خطا", "لطفا شماره همراه را صحیح وارد کنید", "", "قبول");
        }


    }

    private void checkCode() {
        if (!isLogIn && txtName.getText().length() == 0) {
            showAlertDialog("خطا", "لطفا نام خود را صحیح وارد کنید", "", "قبول");
            return;
        }
        try {
            Integer.parseInt(txtCode.getText().toString().trim());
            PresentUser presentUser = new PresentUser(this);
            if (!isLogIn)
                presentUser.logUp(txtEmail.getText().toString().trim(), txtName.getText().toString().trim(), Integer.parseInt(txtCode.getText().toString().trim()));
            else
                presentUser.logIn(txtEmail.getText().toString().trim(), Integer.parseInt(txtCode.getText().toString().trim()));
        } catch (Exception e) {
            showAlertDialog("خطا", "لطفا کد را صحیح وارد کنید", "", "قبول");
        }

    }

    private void sendEmailForserver(String phone) {
        dialogProgres.showProgresBar();
        PresentSmsCode p = new PresentSmsCode(this);
        p.createAndSaveSmsCode(phone);
    }


    @Override
    public void confirmSmsCode(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag)
            showAlertDialog("با ارز پوزش", "تعداد دفعات ارسال پیام به شما از حد مجاز گذشته لطفا تا فردا منتظر بمانید", "", "قبول");

        if (flag) {
            btnConfirmEmail.setText("تغیر شماره همراه");
            txtEmail.setEnabled(false);
            txtCode.setEnabled(true);
            btnConfirmCode.setEnabled(true);

        }

    }


    @Override
    public void sendMessageFScT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LogOut(boolean flag) {

    }

    @Override
    public void LogIn(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        if (res.code == 1) {
            Pref.saveStringValue(PrefKey.userName, res.name);
            Pref.saveStringValue(PrefKey.apiCode, res.apiCode);
            Pref.saveBollValue(PrefKey.IsLogin, true);
            Pref.saveStringValue(PrefKey.phone, txtEmail.getText().toString().trim());
            Pref.getIntegerValue(PrefKey.userTypeMode, 0);
            next();
        } else if (res.code == 3) {
            showAlertDialog("خطا", "شما قبلا ثبت نام نداشته اید.", "ثبت نام", "");
        } else if (res.code == 2) {
            Pref.saveStringValue(PrefKey.phone, txtEmail.getText().toString().trim());
            Pref.saveStringValue(PrefKey.userName, res.name);
            Pref.saveStringValue(PrefKey.apiCode, res.apiCode);
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.getTeacher(Pref.getStringValue(PrefKey.apiCode,""));
        }
    }

    @Override
    public void LogUp(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        if (res.code == 1) {
            Pref.saveStringValue(PrefKey.userName, txtName.getText().toString().trim());
            Pref.saveStringValue(PrefKey.apiCode, res.apiCode);
            Pref.saveBollValue(PrefKey.IsLogin, true);
            Pref.saveStringValue(PrefKey.phone, txtEmail.getText().toString().trim());
            Pref.getIntegerValue(PrefKey.userTypeMode, 0);
            next();
        } else if (res.code == 2) {
            showAlertDialog("خطا", "شما قبلا ثبت نام داشته اید.", "ورود", "");
        }

    }

    @Override
    public void onReceiveUser(ArrayList<StUser> students) {

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
        Pref.saveStringValue(PrefKey.landPhone, teacher.get(0).landPhone);
        Pref.saveStringValue(PrefKey.subject, teacher.get(0).subject);
        Pref.saveStringValue(PrefKey.lon, teacher.get(0).lg);
        Pref.saveStringValue(PrefKey.lat, teacher.get(0).lt);
        Pref.saveIntegerValue(PrefKey.madrak, teacher.get(0).m);
        Pref.saveIntegerValue(PrefKey.userTypeMode, teacher.get(0).type == 0 ? 1 : 2);
        next();
    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {

    }

    private void showAlertDialog(String title, String message, final String buttonTrue, final String btnFalse) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(G.context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton(buttonTrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (buttonTrue.equals("ثبت نام")) {
                    btnConfirmEmail.setText("تایید شماره همراه");
                    txtCode.setText("");
                    btnConfirmCode.setEnabled(false);
                    txtCode.setEnabled(false);
                    llName.setVisibility(View.VISIBLE);
                    isLogIn = false;
                    txtSubject.setText("ثبت نام");
                    dialog.cancel();
                } else if (buttonTrue.equals("ورود")) {
                    btnConfirmEmail.setText("تایید شماره همراه");
                    txtCode.setEnabled(false);
                    txtCode.setText("");
                    btnConfirmCode.setEnabled(false);
                    llName.setVisibility(View.GONE);
                    txtSubject.setText("ورود");
                    isLogIn = true;
                    dialog.cancel();
                }
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

    private void startDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("ورود یا ثبت نام");
        builder.setMessage("آیا قبلا ثبت نام داشته اید؟");
        //   final EditText editText = new EditText(this);
        // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //  editText.setLayoutParams(layoutParams);
        //  builder.setView(editText);
        builder.setPositiveButton("بله",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        llName.setVisibility(View.GONE);
                        txtSubject.setText("ورود");
                        isLogIn = true;
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("خیر",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isLogIn = false;
                        txtSubject.setText("ثبت نام");
                        dialog.cancel();
                    }
                });
        builder.setCancelable(false);
        builder.setCancelable(false);
        builder.show();
    }

    private void next() {
        Intent intent = new Intent(G.context, ActivityProfile.class);
        startActivity(intent);
        G.activity.finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnConfirmCodeConfirmPhone:
                checkCode();
                break;
            case R.id.btnConfirmPhoneConfirmPhone:
                if (btnConfirmEmail.getText().equals("تغیر شماره همراه")) {
                    btnConfirmEmail.setText("تایید شماره همراه");
                    txtEmail.setEnabled(true);
                    txtCode.setEnabled(false);
                    btnConfirmCode.setEnabled(false);
                } else
                    checkeMAIL(txtEmail.getText().toString());
                break;
            case R.id.btnResendEmail:
                sendEmailForserver(txtEmail.getText().toString().trim());
                break;
        }
    }
}
