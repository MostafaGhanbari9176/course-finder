package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.activity_phone_confirm.fragment_get_phone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.activity_phone_confirm.ActivityPhoneConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.activity_phone_confirm.fragment_confirm_code.FragmentConfirmCode;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class FragmentGetPhone extends Fragment implements View.OnClickListener, PresentUser.OnPresentUserLitener {

    Context context = G.context;
    Button btnGet;
    Button btnBefore;
    TextView txt;
    View view;
    DialogProgres dialogProgres;
    String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_phone, container, false);
        init();

        return view;
    }

    private void init() {

        pointers();
        dialogProgres = new DialogProgres(G.activity);

    }

    private void pointers() {
        btnGet = (Button) view.findViewById(R.id.btnConfirmPhone);
        txt = (TextView) view.findViewById(R.id.txtGetPhone);

        btnGet.setOnClickListener(this);
        txt.setText("");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirmPhone:
                getPhone();
                break;
        }
    }

    private void getPhone() {
        if (txt.getText().length() == 11) {
            queryForPhone(phone = txt.getText().toString().trim());

        } else {
            showMassage("لطفا شماره تلفن خود را صحیح وارد کنید.");
        }

    }

    private void queryForPhone(String phone) {
//به جایه ذخیره در دیتابیس باید جهت دریافت کد ارسال شود
        PresentUser presentUser = new PresentUser(this);
        try {
            dialogProgres.showProgresBar();
            presentUser.savePhone(Long.parseLong(phone));
            Pref.saveStringValue(PrefKey.fakePhone,phone);
        } catch (Exception e) {
            dialogProgres.closeProgresBar();
            showMassage("لطفا شماره تلفن خود را صحیح وارد کنید.");
        }

    }

    private void showMassage(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMessageFUT(String message) {
        showMassage(message);
    }

    @Override
    public void confirm(boolean flag) {
        ActivityPhoneConfirm.replaceContentWith(new FragmentConfirmCode());
        dialogProgres.closeProgresBar();
    }

    @Override
    public void onReceiveUser(ArrayList<StUser> users) {

    }
}
