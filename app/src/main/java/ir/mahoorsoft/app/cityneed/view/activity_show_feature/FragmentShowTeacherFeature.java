package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 3/5/2018.
 */

public class FragmentShowTeacherFeature extends Fragment implements PresentTeacher.OnPresentTeacherListener, OnMapReadyCallback {
    View view;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    TextView txtSubject;
    TextView txtLandPhone;
    TextView txtPhone;
    ImageView img;
    DialogProgres dialogProgres;
    String teacherId = ActivityOptionalCourse.teacherId;
    Marker marker;
    StTeacher teacher;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_show_teacher_feture, container, false);
            inite();
        }
        return view;

    }

    private void inite() {
        dialogProgres = new DialogProgres(G.context);
        pointers();
        settingUpMap();
        setFont();
        getTeacherProfile();
    }

    private void settingUpMap() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapTeacherFeature);
        if (supportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapProfileTeacher, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }

    private void getTeacherProfile() {
        dialogProgres.showProgresBar();
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getTeacher(teacherId);
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtLandPhone.setTypeface(typeface);
        txtPhone.setTypeface(typeface);
        txtSubject.setTypeface(typeface);
    }

    private void pointers() {
        txtLandPhone = (TextView) view.findViewById(R.id.txtLandPhoneTeacherFeature);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneTeacherFeature);
        txtSubject = (TextView) view.findViewById(R.id.txtTeacherSubgectTeacherFeature);
        img = (ImageView) view.findViewById(R.id.imgTeacherFeature);

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
        dialogProgres.closeProgresBar();
        if (users.size() == 0) {
            sendMessageFTT("خطا");
            ActivityOptionalCourse.viewPager.setCurrentItem(0);
            return;
        }
        teacher = users.get(0);
        txtSubject.setText(users.get(0).subject);
        txtPhone.setText(users.get(0).phone);
        txtLandPhone.setText(users.get(0).landPhone);
        setImg(users.get(0).pictureId);
        setTeacherLocation();
    }

    @Override
    public void onReceiveCustomeTeacherListData(ArrayList<StCustomTeacherListHome> data) {

    }



    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> users) {

    }

    private void setTeacherLocation() {
        LatLng latLng = new LatLng(Double.parseDouble(teacher.lt), Double.parseDouble(teacher.lg));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.setPosition(latLng);
    }

    private void setImg(String pictureId) {
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/teacher/" + pictureId + ".png")
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .fitCenter()
                .error(R.drawable.university)
                .clone()
                .into(img);
    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(29.4892550, 60.8612360);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("مکان آموزشگاه");
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                location).zoom(16).build();
        marker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
