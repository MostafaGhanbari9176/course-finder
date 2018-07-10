package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentSmsCode;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class FragmentEmailConfirm extends Fragment implements View.OnClickListener, PresentSmsCode.OnPresentSmsCodeListener, PresentUser.OnPresentUserLitener, PresentTeacher.OnPresentTeacherListener {

    View view;
    boolean isUserChanged = true;
    RelativeLayout llName;
    Button btnConfirmEmail;
    Button btnConfirmCode;
    Button btnResend;
    public TextView txtEmail;
    public TextView txtName;
    TextView txtCode;
    boolean isLogIn;
    public RadioButton rbLogIn;
    public RadioButton rbLogUp;
    RelativeLayout rlPbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_email, container, false);
        init();
        return view;
    }

    private void init() {
        pointers();
        txtEmail.setText(Pref.getStringValue(PrefKey.emailWhenConfirm, ""));
        txtName.setText(Pref.getStringValue(PrefKey.nameWhenConfirm, ""));
        rbLogIn.setChecked(Pref.getBollValue(PrefKey.cbxLogInisChecked, false));

    }

    private void pointers() {
        rlPbar = (RelativeLayout) view.findViewById(R.id.rlPbarFragmentConfirmEmail);
        txtCode = (TextView) view.findViewById(R.id.txtSmsCodeConfirmEmail);
        // txtCode.setEnabled(false);
        (rbLogIn = (RadioButton) view.findViewById(R.id.rbLogInWithEmail)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llName.setVisibility(View.GONE);
                    isLogIn = true;
                }
            }
        });
        (rbLogUp = (RadioButton) view.findViewById(R.id.rbLogUpWithEmail)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llName.setVisibility(View.VISIBLE);
                    isLogIn = false;
                }
            }
        });
        txtName = (TextView) view.findViewById(R.id.txtNameConfirmEmail);
        txtEmail = (TextView) view.findViewById(R.id.txtEmailConfirmٍEmail);
        btnConfirmEmail = (Button) view.findViewById(R.id.btnConfirmEmailConfirmEmail);
        btnConfirmCode = (Button) view.findViewById(R.id.btnConfirmCodeConfirmEmail);
        btnResend = (Button) view.findViewById(R.id.btnResendEmail);
        llName = (RelativeLayout) view.findViewById(R.id.RLNameEmailConfirm);
        txtEmail.setText("");
        txtCode.setText("");
        btnConfirmEmail.setOnClickListener(this);
        btnConfirmCode.setOnClickListener(this);
        btnResend.setOnClickListener(this);

    }

    private void checkeInData(String mail) {
        if (!isLogIn && txtName.getText().toString().trim().length() == 0) {
            txtName.setError("کامل کنید");
            txtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            txtEmail.setError("کامل کنید");
            txtEmail.requestFocus();
        } else if (!isEmailValid(mail)) {
            txtEmail.requestFocus();
            txtEmail.setError("صحیح وارد کنید");
        } else {
            Pref.saveStringValue(PrefKey.emailWhenConfirm, mail);
            Pref.saveStringValue(PrefKey.nameWhenConfirm, txtName.getText().toString().trim());
            Pref.saveBollValue(PrefKey.cbxLogInisChecked, isLogIn);
            sendEmailForServer(mail);
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void checkCode() {
        if (!isLogIn && txtName.getText().toString().trim().length() == 0) {
            txtName.setError("کامل کنید");
            txtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(txtEmail.getText().toString().trim())) {
            txtEmail.setError("کامل کنید");
            txtEmail.requestFocus();
            return;
        }
        if (!(isEmailValid(txtEmail.getText().toString().trim()))) {
            txtEmail.requestFocus();
            txtEmail.setError("صحیح وارد کنید");
            return;
        }
        try {
            Integer.parseInt(txtCode.getText().toString().trim());
            rlPbar.setVisibility(View.VISIBLE);
            PresentUser presentUser = new PresentUser(this);
            if (!isLogIn)
                presentUser.logUp(txtEmail.getText().toString().trim(), txtName.getText().toString().trim(), Integer.parseInt(txtCode.getText().toString().trim()));
            else
                presentUser.logIn(txtEmail.getText().toString().trim(), Integer.parseInt(txtCode.getText().toString().trim()));
        } catch (Exception e) {
            showAlertDialog("خطا", "لطفا کد را صحیح وارد کنید", "", "قبول");
        }

    }

    private void sendEmailForServer(String phone) {
        rlPbar.setVisibility(View.VISIBLE);
        PresentSmsCode p = new PresentSmsCode(this);
        p.createAndSaveSmsCode(phone);
    }

    @Override
    public void confirmSmsCode(boolean flag) {
        rlPbar.setVisibility(View.GONE);
/*        if (!flag)
            showAlertDialog("با ارز پوزش", "خطایی رخ داده لطفا دوباره تلاش کنید", "", "خب");

        if (flag) {*/
        Toast.makeText(G.context, "ایمیلی حاوی کد تایید به آدرس ایمیل وارد شده ارسال شد", Toast.LENGTH_SHORT).show();
        btnConfirmEmail.setText("تغیر اطلاعات وارد شده");
        txtEmail.setEnabled(false);
        txtName.setEnabled(false);
        txtCode.setEnabled(true);
        btnConfirmCode.setEnabled(true);
        txtCode.requestFocus();
        //}
    }

    @Override
    public void sendMessageFScT(String message) {
        rlPbar.setVisibility(View.GONE);
        Toast.makeText(G.context, "ایمیلی حاوی کد تایید به آدرس ایمیل وارد شده ارسال شد", Toast.LENGTH_SHORT).show();
        btnConfirmEmail.setText("تغیر اطلاعات وارد شده");
        txtEmail.setEnabled(false);
        txtName.setEnabled(false);
        txtCode.setEnabled(true);
        btnConfirmCode.setEnabled(true);
        // Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void sendMessageFUT(String message) {
        rlPbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LogOut(boolean flag) {

    }

    @Override
    public void LogIn(ResponseOfServer res) {
        rlPbar.setVisibility(View.GONE);
        if (res.code == 0)
            sendMessageFUT("کد وارد شده اشتباه است.");
        else if (res.code == 1) {
            Pref.saveStringValue(PrefKey.userName, res.name);
            Pref.saveStringValue(PrefKey.apiCode, res.apiCode);
            Pref.saveBollValue(PrefKey.IsLogin, true);
            Pref.saveStringValue(PrefKey.email, txtEmail.getText().toString().trim());
            Pref.getIntegerValue(PrefKey.userTypeMode, 0);
            next();
        } else if (res.code == 3) {
            showAlertDialog("خطا", "شما قبلا ثبت نام نداشته اید.", "ثبت نام", "");
        } else if (res.code == 2) {
            Pref.saveStringValue(PrefKey.email, txtEmail.getText().toString().trim());
            Pref.saveStringValue(PrefKey.userName, res.name);
            Pref.saveStringValue(PrefKey.apiCode, res.apiCode);
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.getTeacher(Pref.getStringValue(PrefKey.apiCode, ""));
        }
    }

    @Override
    public void LogUp(ResponseOfServer res) {
        rlPbar.setVisibility(View.GONE);
        if (res.code == 0)
            sendMessageFUT("کد وارد شده اشتباه است.");
        else if (res.code == 1) {
            Pref.saveStringValue(PrefKey.userName, txtName.getText().toString().trim());
            Pref.saveStringValue(PrefKey.apiCode, res.apiCode);
            Pref.saveBollValue(PrefKey.IsLogin, true);
            Pref.saveStringValue(PrefKey.email, txtEmail.getText().toString().trim());
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

        rlPbar.setVisibility(View.GONE);
        Pref.saveBollValue(PrefKey.IsLogin, true);
        Pref.saveStringValue(PrefKey.landPhone, teacher.get(0).landPhone);
        Pref.saveStringValue(PrefKey.subject, teacher.get(0).subject);
        Pref.saveStringValue(PrefKey.lon, teacher.get(0).lg);
        Pref.saveStringValue(PrefKey.lat, teacher.get(0).lt);
        Pref.saveStringValue(PrefKey.pictureId, teacher.get(0).pictureId);
        Pref.saveIntegerValue(PrefKey.madrak, teacher.get(0).m);
        Pref.saveStringValue(PrefKey.address, teacher.get(0).address);
        Pref.saveIntegerValue(PrefKey.userTypeMode, teacher.get(0).type == 0 ? 1 : 2);
        next();
    }

    @Override
    public void onReceiveCustomeTeacherListData(ArrayList<StCustomTeacherListHome> data) {

    }


    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> users) {

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
                btnConfirmEmail.setText("تایید ایمیل");
                txtEmail.setEnabled(true);
                txtName.setEnabled(true);
                // txtCode.setEnabled(false);
                txtCode.setText("");
                //  btnConfirmCode.setEnabled(false);

                if (buttonTrue.equals("ثبت نام")) {
                    rbLogUp.setChecked(true);
                    dialog.cancel();
                } else if (buttonTrue.equals("ورود")) {
                    rbLogIn.setChecked(true);
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

    private void next() {
        try {
            Intent intent = new Intent(G.context, ActivityProfile.class);
            startActivity(intent);
            G.activity.finish();
        } catch (Exception ignore) {
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnConfirmCodeConfirmEmail:
                checkCode();
                break;
            case R.id.btnConfirmEmailConfirmEmail:
                if (btnConfirmEmail.getText().equals("تغیر اطلاعات وارد شده")) {
                    btnConfirmEmail.setText("تایید ایمیل");
                    txtEmail.setEnabled(true);
                    txtName.setEnabled(true);
                    // txtCode.setEnabled(false);
                    // btnConfirmCode.setEnabled(false);
                } else
                    checkeInData(txtEmail.getText().toString().trim());
                break;
            case R.id.btnResendEmail:
                checkeInData(txtEmail.getText().toString().trim());
                break;
        }
    }


}
