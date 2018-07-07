package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentFavorite;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;

/**
 * Created by RCC1 on 3/5/2018.
 */

public class FragmentShowTeacherFeature extends Fragment implements PresentTeacher.OnPresentTeacherListener, OnMapReadyCallback, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, PresentFavorite.OnPresentFavoriteListener {
    View view;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    TextView txtSubject;
    TextView txtAddress;
    TextView txtDescription;
    TextView txtLandPhone;
    TextView txtPhone;
    ImageView img;
    SwipeRefreshLayout sDown;
    String teacherId = ActivityOptionalCourse.teacherId;
    Marker marker;
    StTeacher teacher;
    ImageView imgFavorite;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_show_teacher_feture, container, false);
            init();
        }
        return view;

    }

    private void init() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDShowTeacherFuture);
        sDown.setOnRefreshListener(this);
        pointers();
        settingUpMap();
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
        sDown.setRefreshing(true);
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getTeacher(teacherId);
    }

    private void pointers() {
        txtLandPhone = (TextView) view.findViewById(R.id.txtLandPhoneTeacherFeature);
        txtAddress = (TextView) view.findViewById(R.id.txtAddressTeacherFeature);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionTeacherFeature);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneTeacherFeature);
        txtSubject = (TextView) view.findViewById(R.id.txtTeacherSubgectTeacherFeature);
        img = (ImageView) view.findViewById(R.id.imgTeacherFeature);
        imgFavorite = (ImageView) view.findViewById(R.id.imgFavorite);
        imgFavorite.setOnClickListener(this);
        setPaletteSize();

    }

    private void setPaletteSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        G.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        img.getLayoutParams().height = (int) (width / 1.5);
    }

    @Override
    public void sendMessageFTT(String message) {
        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {
        sDown.setRefreshing(false);
        if (users.size() == 0) {
            sendMessageFTT("خطا");
            ActivityOptionalCourse.viewPager.setCurrentItem(0);
            return;
        }
        teacher = users.get(0);
        txtSubject.setText(users.get(0).subject);
        txtPhone.setText(users.get(0).phone);
        txtLandPhone.setText(users.get(0).landPhone);
        txtDescription.setText(users.get(0).tozihat);
        txtAddress.setText(users.get(0).address);
        setImg(users.get(0).pictureId);
        setFavoriteImage();
        setTeacherLocation();
    }

    @Override
    public void onReceiveCustomeTeacherListData(ArrayList<StCustomTeacherListHome> data) {

    }


    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> users) {

    }

    private void setTeacherLocation() {
        try {
            if (mMap != null) {
                LatLng latLng = new LatLng(Double.parseDouble(teacher.lt), Double.parseDouble(teacher.lg));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(
                        latLng).zoom(16).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                marker.setPosition(latLng);
            }
        } catch (Exception ignored) {
        }
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

    private void setFavoriteImage() {
        if (teacher.favorite == 1)
            imgFavorite.setImageResource(R.drawable.icon_favorite_red);
        else
            imgFavorite.setImageResource(R.drawable.icon_favorite_silver);
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

    @Override
    public void onRefresh() {
        getTeacherProfile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFavorite:
                if (Pref.getBollValue(PrefKey.IsLogin, false)) {
                    if (teacher.favorite == 0)
                        queryForSaveFavorite();
                    else
                        queryForRemoveFavorite();
                } else
                    messageFromFavorite("ابتدا وارد حساب کاربری خود شوید");
                break;
        }
    }

    private void queryForSaveFavorite() {
        messageFromFavorite("درحال ثبت در لیست علاقه مندی ...");
        (new PresentFavorite(this)).saveFavorite(teacherId);
    }

    private void queryForRemoveFavorite() {
        messageFromFavorite("درحال حذف از لیست علاقه مندی ...");
        (new PresentFavorite(this)).removeFavorite(teacherId);
    }

    @Override
    public void messageFromFavorite(String message) {
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void flagFromFavorite(boolean flag) {
        if (flag && teacher.favorite == 0) {
            sendMessageFTT("آموزشگاه به لیست علاقه مندی ها افزوده شد.");
            teacher.favorite = 1;
            setFavoriteImage();
        } else if (flag && teacher.favorite == 1) {
            sendMessageFTT("آموزشگاه از لیست علاقه مندی ها حذف شد.");
            teacher.favorite = 0;
            setFavoriteImage();
        } else
            sendMessageFTT("خطا ,لطفا دوباره سیع کنید ...");
    }
}
