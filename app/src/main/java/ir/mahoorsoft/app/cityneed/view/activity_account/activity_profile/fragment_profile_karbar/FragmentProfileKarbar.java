package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_karbar;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_registering.ActivityRegistering;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;


/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileKarbar extends Fragment implements View.OnClickListener, DialogPrvince.OnDialogPrvinceListener, PresentUser.OnPresentUserLitener {

    LinearLayout btnEdit;
    LinearLayout btnAdd;
    LinearLayout btnSave;
    TextView txtName;
    TextView txtFamilyName;
    TextView txtPhone;
    TextView txtLocation;
    View view;
    DialogPrvince dialogPrvince;
    DialogProgres dialogProgres;
    int cityId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_karbar, container, false);
        init();
        return view;
    }

    private void init() {

        dialogPrvince = new DialogPrvince(G.context, this);
        pointers();
        txtPhone.setText(Pref.getStringValue(PrefKey.phone, ""));
        txtName.setText(Pref.getStringValue(PrefKey.userName, ""));
        txtFamilyName.setText(Pref.getStringValue(PrefKey.userFamily, ""));
        txtLocation.setText(Pref.getStringValue(PrefKey.location, ""));
        btnSave.setVisibility(View.GONE);

    }

    private void pointers() {

        dialogProgres = new DialogProgres(G.context);
        btnEdit = (LinearLayout) view.findViewById(R.id.btnEditProfile_Karbar);
        btnAdd = (LinearLayout) view.findViewById(R.id.btnAddCurse_Karbar);
        btnSave = (LinearLayout) view.findViewById(R.id.btnSave_Karbar);

        txtName = (TextView) view.findViewById(R.id.txtNAme_karbar);
        txtFamilyName = (TextView) view.findViewById(R.id.txtFamilyName_Karbar);
        (txtPhone = (TextView) view.findViewById(R.id.txtPhone_Karbar)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updatePhone();
                return false;
            }
        });
        (txtLocation = (TextView) view.findViewById(R.id.txtLocation_Karbar)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dialogPrvince.showDialog();
                return false;
            }
        });

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnEditProfile_Karbar:
                editProfile();
                break;
            case R.id.btnAddCurse_Karbar:
                starterActivity(ActivityRegistering.class);
                break;
            case R.id.btnSave_Karbar:
                checkValidInf();
                break;
        }
    }

    private void editProfile() {

        btnSave.setVisibility(View.VISIBLE);
        Toast.makeText(G.context, "لطفا اطلاعات جدید را وارد کنید.", Toast.LENGTH_SHORT).show();
        txtName.setEnabled(true);
        txtFamilyName.setEnabled(true);
        txtPhone.setEnabled(true);
        txtLocation.setEnabled(true);
    }

    private void starterActivity(Class aClass) {

        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void locationInformation(String location, int cityId) {
        txtLocation.setText(location);
        this.cityId = cityId;
    }

    private void updateUser(String phone, String name, String family) {
        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.updateUser(phone, name, family, 1, 0, cityId, -1);
    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmUser(boolean flag) {
        dialogProgres.closeProgresBar();
        if(!flag){
            sendMessageFUT("خطا");
        }
        else {
            btnSave.setVisibility(View.GONE);
            txtName.setEnabled(false);
            txtFamilyName.setEnabled(false);
            txtPhone.setEnabled(false);
            txtLocation.setEnabled(false);

            Pref.saveStringValue(PrefKey.location, txtLocation.getText().toString());
            Pref.saveIntegerValue(PrefKey.cityId, cityId);
            Pref.saveStringValue(PrefKey.userName, txtName.getText().toString().trim());
            Pref.saveStringValue(PrefKey.userFamily, txtFamilyName.getText().toString().trim());
            Toast.makeText(G.context, "اطلاعات جدید ذخیره شد.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReceiveUser(ArrayList<StUser> users) {

    }

    private void updatePhone() {
        txtPhone.setEnabled(false);
    }

    private void checkValidInf() {
        if ((txtName.getText() != null && txtName.getText().length() != 0) &&
                (txtFamilyName.getText() != null && txtFamilyName.getText().length() != 0)&&
                (txtLocation.getText() != null && txtLocation.getText().length() != 0)) {
            updateUser(Pref.getStringValue(PrefKey.phone, ""), txtName.getText().toString().trim(), txtFamilyName.getText().toString().trim());
        } else {
            Toast.makeText(G.context, "لطفا اطلاعات را کامل وارد کنید...", Toast.LENGTH_SHORT).show();
        }
    }
}



