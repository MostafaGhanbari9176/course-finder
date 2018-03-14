package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityCoursesListByTeacherId;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentMap extends Fragment implements OnMapReadyCallback, PresentTeacher.OnPresentTeacherListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    LatLng location;
    Marker selectedMarker = null;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    View view;
    DialogProgres dialogProgres;
    boolean dataSaved = false;
    boolean mapReady = false;
    ArrayList<StTeacher> source = new ArrayList<>();
    int selectedId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        if (supportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map1, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        getDataFromServer();
    }

    private void getDataFromServer() {
        dialogProgres.showProgresBar();
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getAllTeacher();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        try {
            if (Pref.getStringValue(PrefKey.lat, "").length() == 0)
                location = new LatLng(29.4892550, 60.8612360);
            else
                location = new LatLng(Double.parseDouble(Pref.getStringValue(PrefKey.lat, "")), Double.parseDouble(Pref.getStringValue(PrefKey.lon, "")));

        } catch (Exception e) {
            location = new LatLng(29.4892550, 60.8612360);
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                location).zoom(12).build();
        // mMap.setBuildingsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mapReady = true;
        if (dataSaved)
            setMarkers();
    }

    private void setMarkers() {
        for (int i = 0; i < source.size(); i++) {
            LatLng location = new LatLng(Double.parseDouble(source.get(i).lt), Double.parseDouble(source.get(i).lg));
            addMarker(location, source.get(i).subject);
        }

    }

    private void addMarker(LatLng location, String title) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("آموزشگاه : " + title);
        Marker m = mMap.addMarker(markerOptions);
//        m.showInfoWindow();
    }

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        //Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {
        dialogProgres.closeProgresBar();
        source.addAll(users);
        dataSaved = true;
        if (mapReady)
            setMarkers();
    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(mMap.getCameraPosition().zoom).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        try {

            if (Integer.parseInt(G.myTrim(marker.getId(), 'm')) != Integer.parseInt(G.myTrim(selectedMarker.getId(), 'm'))) {
                selectedId = Integer.parseInt(G.myTrim(marker.getId(), 'm'));
                selectedMarker = marker;
                selectedMarker.showInfoWindow();
                selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            } else
                showDialog(marker.getTitle(), "نمایش دوره های این آموزشگاه؟", "بله", "خیر");

        } catch (Exception e) {
            e.getMessage();
            if (selectedMarker == null) {
                selectedId = Integer.parseInt(G.myTrim(marker.getId(), 'm'));
                selectedMarker = marker;
                selectedMarker.showInfoWindow();
                selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            }
        }

        return true;
    }

    private void showDialog(String title, String message, String btntrue, String btnFalse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btntrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                showCourseList();
                dialog.cancel();
            }
        });
        builder.setNegativeButton(btnFalse, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void showCourseList() {
        Intent intent = new Intent(G.context, ActivityCoursesListByTeacherId.class);
        intent.putExtra("apiCode", source.get(selectedId).ac);
        startActivity(intent);

    }
}

