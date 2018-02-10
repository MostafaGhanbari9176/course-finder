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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
    TextView txtLandPhone;
    TextView txtname;
    TextView txtSubject;
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
        txtSubject.setText(Pref.getStringValue(PrefKey.subject, ""));
        txtname.setText(Pref.getStringValue(PrefKey.userName, ""));
        txtPhone.setText(Pref.getStringValue(PrefKey.phone, ""));
        txtLandPhone.setText(Pref.getStringValue(PrefKey.landPhone, ""));

    }

    private void settingUpMap() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapProfileTeacher);
        if (supportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapProfileTeacher, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }

    private void pointers() {
        txtname = (TextView) view.findViewById(R.id.txtNameProfileTeacher);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneProfileTeacher);
        txtLandPhone = (TextView) view.findViewById(R.id.txtLandPhoneProfileTeacher);
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectProfileTeacher);
    }

    @Override
    public void onClick(View view) {


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

    }

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(Double.parseDouble(Pref.getStringValue(PrefKey.lat, "")), Double.parseDouble(Pref.getStringValue(PrefKey.lon, "")));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("مکان شما");

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                location).zoom(16).build();

        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
