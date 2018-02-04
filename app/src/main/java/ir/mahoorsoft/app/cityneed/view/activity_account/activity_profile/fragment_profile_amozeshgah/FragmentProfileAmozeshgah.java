package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_amozeshgah;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.view.activity_account.registering.ActivityCourseRegistring;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityCoursesList;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;

/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileAmozeshgah extends Fragment implements DialogPrvince.OnDialogPrvinceListener, View.OnClickListener, PresentTeacher.OnPresentTeacherListener, OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    TextView txtPhone;
    TextView txtLocation;
    TextView txtSubject;
    TextView txtAddress;
    LinearLayout btnEdit;
    LinearLayout btnSave;
    LinearLayout btnAddCourse;
    LinearLayout btnListSabtenam;
    LinearLayout btnListCourse;
    DialogPrvince dialogPrvince;
    DialogProgres dialogProgres;
    int cityId = Pref.getIntegerValue(PrefKey.cityId, -1);
    int madrak = Pref.getIntegerValue(PrefKey.madrak, 0);
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_teacher, container, false);
        init();
        return view;
    }

    private void init() {
        settingUpMap();
        pointers();
        dialogProgres = new DialogProgres(G.context);
        dialogPrvince = new DialogPrvince(G.context, this);
        txtAddress.setText(Pref.getStringValue(PrefKey.address, ""));
        txtSubject.setText(Pref.getStringValue(PrefKey.subject, ""));
        txtLocation.setText(Pref.getStringValue(PrefKey.location, ""));
        txtPhone.setText(Pref.getStringValue(PrefKey.landPhone, ""));
        btnSave.setVisibility(View.GONE);
    }

    private void settingUpMap(){
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.contentMapProfileTeacher);
        if(supportMapFragment == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.contentMapProfileTeacher , supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }

    private void pointers() {
        txtAddress = (TextView) view.findViewById(R.id.txtAddressProfileTeacher);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneProfileTeacher);
        (txtLocation = (TextView) view.findViewById(R.id.txtLocationProfileTeacher)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dialogPrvince.showDialog();
                return false;
            }
        });
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectProfileTeacher);

        btnEdit = (LinearLayout) view.findViewById(R.id.btnEditProfileTeacher);
        btnAddCourse = (LinearLayout) view.findViewById(R.id.btnAddCuorseProfileTeacher);
        btnSave = (LinearLayout) view.findViewById(R.id.btnSaveProfileTeacher);
        btnListCourse = (LinearLayout) view.findViewById(R.id.btnListCuorseProfileTeacher);
        btnListSabtenam = (LinearLayout) view.findViewById(R.id.btnSabtenamListProfileTeacher);
        btnAddCourse.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnListCourse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnEditProfileTeacher:
                editProfile();
                break;
            case R.id.btnAddCuorseProfileTeacher:
                addCourse();
                break;
            case R.id.btnSaveProfileTeacher:
                checkValidInf();
                break;
            case R.id.btnListCuorseProfileTeacher:
                showCourse();
                break;
            case R.id.btnSabtenamListProfileTeacher:
                showCourse();
                break;
        }
    }

    private void showCourse() {
        Intent intent = new Intent(G.context, ActivityCoursesList.class);
        intent.putExtra("id", Pref.getStringValue(PrefKey.phone, ""));
        startActivity(intent);
    }

    private void editProfile() {

        btnSave.setVisibility(View.VISIBLE);
        Toast.makeText(G.context, "لطفا اطلاعات جدید را وارد کنید.", Toast.LENGTH_SHORT).show();
        txtSubject.setEnabled(true);
        txtAddress.setEnabled(true);
        txtPhone.setEnabled(true);
        txtLocation.setEnabled(true);
    }

    private void addCourse() {
        Intent intent = new Intent(G.context, ActivityCourseRegistring.class);
        startActivity(intent);


    }

    private void checkValidInf() {
        if ((txtAddress.getText() != null && txtAddress.getText().toString().trim().length() != 0) &&
                (txtSubject.getText() != null && txtSubject.getText().toString().trim().length() != 0) &&
                (txtPhone.getText() != null && txtPhone.getText().toString().trim().length() != 0) &&
                (txtLocation.getText() != null && txtLocation.getText().toString().trim().length() != 0)) {
            dialogProgres.showProgresBar();
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.updateTeacher(txtPhone.getText().toString().trim(), txtSubject.getText().toString().trim(), txtAddress.getText().toString().trim(), cityId, madrak);
        } else {
            Toast.makeText(G.context, "لطفا اطلاعات را کامل وارد کنید...", Toast.LENGTH_SHORT).show();
        }
    }


    public void startActivitys(Class aClass, boolean flag) {

        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
        if (flag) {
            getActivity().finish();
        }
    }

    @Override
    public void locationInformation(String location, int cityId) {
        this.cityId = cityId;
        txtLocation.setText(location);
    }

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag) {
            sendMessageFTT("خطا");
            G.activity.finish();
        } else {
            Pref.saveIntegerValue(PrefKey.cityId, cityId);
            Pref.saveStringValue(PrefKey.location, txtLocation.getText().toString().trim());
            Pref.saveStringValue(PrefKey.landPhone, txtPhone.getText().toString().trim());
            Pref.saveStringValue(PrefKey.subject, txtSubject.getText().toString().trim());
            Pref.saveStringValue(PrefKey.address, txtAddress.getText().toString().trim());
            Pref.saveIntegerValue(PrefKey.madrak, madrak);
            btnSave.setVisibility(View.GONE);
            txtAddress.setEnabled(false);
            txtSubject.setEnabled(false);
            txtPhone.setEnabled(false);
            txtLocation.setEnabled(false);
        }
    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
