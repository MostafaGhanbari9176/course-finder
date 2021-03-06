package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
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
import java.util.TimerTask;

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
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class FragmentPhoneConfirm extends Fragment implements View.OnClickListener, PresentSmsCode.OnPresentSmsCodeListener, PresentUser.OnPresentUserLitener, PresentTeacher.OnPresentTeacherListener {
    View view;
    boolean isUserChanged = true;
    int timer = 120;
    LinearLayout llName;
    Button btnConfirmPhone;
    Button btnConfirmCode;
    TextView txtPhone;
    TextView txtName;
    TextView txtTimer;
    TextView txtSubject;
    TextView txtCode;
    DialogProgres dialogProgres;
    Handler handler = new Handler();
    boolean isLogIn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone_confirm, container, false);
        init();
        return view;
    }

    private void init(){
        dialogProgres = new DialogProgres(G.context);
        pointers();

       // startDialog();
    }

    private void pointers() {
        // btnBack = (Button) findViewById(R.id.btnBackPhoneConfirm);

        txtCode = (TextView) view.findViewById(R.id.txtSmsCodeConfirmPhone);
        txtCode.setEnabled(false);
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectPhoneConfirm);
        txtName = (TextView) view.findViewById(R.id.txtNamePhoneConfirm);
        txtTimer = (TextView) view.findViewById(R.id.txtTimerConfirmPhone);
        txtTimer.setVisibility(View.GONE);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneConfirmPhone);
        btnConfirmPhone = (Button) view.findViewById(R.id.btnConfirmPhoneConfirmPhone);
        btnConfirmCode = (Button) view.findViewById(R.id.btnConfirmCodeConfirmPhone);
        llName = (LinearLayout) view.findViewById(R.id.llNamePhoneConfirm);
        txtPhone.setText("");
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
        btnConfirmPhone.setOnClickListener(this);
        btnConfirmCode.setOnClickListener(this);
        txtTimer.setOnClickListener(this);

    }

    private void checkPhone(String phone) {
        if (!isLogIn && txtName.getText().length() == 0) {
            showAlertDialog("??????", "???????? ?????? ?????? ???? ???????? ???????? ????????", "", "????????");
            return;
        }
        try {
            Long.parseLong(phone.trim());
            if (phone.trim().length() != 11) {
                showAlertDialog("??????", "???????? ?????????? ?????????? ???? ???????? ???????? ????????", "", "????????");
            } else {
                sendPhoneForserver(phone);
            }
        } catch (Exception e) {
            showAlertDialog("??????", "???????? ?????????? ?????????? ???? ???????? ???????? ????????", "", "????????");
        }


    }

    private void checkCode() {
        if (!isLogIn && txtName.getText().length() == 0) {
            showAlertDialog("??????", "???????? ?????? ?????? ???? ???????? ???????? ????????", "", "????????");
            return;
        }
        try {
            Integer.parseInt(txtCode.getText().toString().trim());
            PresentUser presentUser = new PresentUser(this);
            if (!isLogIn)
                presentUser.logUp(txtPhone.getText().toString().trim(), txtName.getText().toString().trim(), Integer.parseInt(txtCode.getText().toString().trim()));
            else
                presentUser.logIn(txtPhone.getText().toString().trim(), Integer.parseInt(txtCode.getText().toString().trim()));
        } catch (Exception e) {
            showAlertDialog("??????", "???????? ???? ???? ???????? ???????? ????????", "", "????????");
        }

    }

    private void sendPhoneForserver(String phone) {
        dialogProgres.showProgresBar();
        PresentSmsCode p = new PresentSmsCode(this);
        p.createAndSaveSmsCode(phone);
    }


    @Override
    public void confirmSmsCode(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag)
            showAlertDialog("???? ?????? ????????", "?????????? ?????????? ?????????? ???????? ???? ?????? ???? ???? ???????? ?????????? ???????? ???? ???????? ?????????? ????????????", "", "????????");

        if (flag) {
            btnConfirmPhone.setText("???????? ?????????? ??????????");
            txtPhone.setEnabled(false);
            txtCode.setEnabled(true);
            btnConfirmCode.setEnabled(true);
            timer();
        }

    }

    private void timer() {
        txtTimer.setVisibility(View.VISIBLE);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (timer >= 1) {
                    timer--;
                    try {
                        Thread.sleep(100);
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
                txtTimer.setText("?????????? ???????? ??????????( " + String.valueOf(i) + " )");
                if (i <= 1) {
                    txtTimer.setText("?????????? ???????? ??????????");
                }
            }
        });
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
            Pref.saveStringValue(PrefKey.email, txtPhone.getText().toString().trim());
            Pref.getIntegerValue(PrefKey.userTypeMode, 0);
            next();
        } else if (res.code == 3) {
            showAlertDialog("??????", "?????? ???????? ?????? ?????? ???????????? ??????.", "?????? ??????", "");
        } else if (res.code == 2) {
            Pref.saveStringValue(PrefKey.email, txtPhone.getText().toString().trim());
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
            Pref.saveStringValue(PrefKey.email, txtPhone.getText().toString().trim());
            Pref.getIntegerValue(PrefKey.userTypeMode, 0);
            next();
        } else if (res.code == 2) {
            showAlertDialog("??????", "?????? ???????? ?????? ?????? ?????????? ??????.", "????????", "");
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
                if (buttonTrue.equals("?????? ??????")) {
                    btnConfirmPhone.setText("?????????? ?????????? ??????????");
                    txtCode.setText("");
                    btnConfirmCode.setEnabled(false);
                    txtCode.setEnabled(false);
                    llName.setVisibility(View.VISIBLE);
                    isLogIn = false;
                    txtSubject.setText("?????? ??????");
                    dialog.cancel();
                } else if (buttonTrue.equals("????????")) {
                    btnConfirmPhone.setText("?????????? ?????????? ??????????");
                    txtCode.setEnabled(false);
                    txtCode.setText("");
                    btnConfirmCode.setEnabled(false);
                    llName.setVisibility(View.GONE);
                    txtSubject.setText("????????");
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
        builder.setTitle("???????? ???? ?????? ??????");
        builder.setMessage("?????? ???????? ?????? ?????? ?????????? ????????");
        //   final EditText editText = new EditText(this);
        // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //  editText.setLayoutParams(layoutParams);
        //  builder.setView(editText);
        builder.setPositiveButton("??????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        llName.setVisibility(View.GONE);
                        txtSubject.setText("????????");
                        isLogIn = true;
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("??????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isLogIn = false;
                        txtSubject.setText("?????? ??????");
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
                if (btnConfirmPhone.getText().equals("???????? ?????????? ??????????")) {
                    btnConfirmPhone.setText("?????????? ?????????? ??????????");
                    txtPhone.setEnabled(true);
                    txtCode.setEnabled(false);
                    btnConfirmCode.setEnabled(false);
                } else
                    checkPhone(txtPhone.getText().toString());
                break;
            case R.id.txtTimerConfirmPhone:
                if (txtTimer.getText().equals("?????????? ???????? ????"))
                    sendPhoneForserver(txtPhone.getText().toString().trim());
                break;
        }
    }
}
