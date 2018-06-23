package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentSubscribe;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_subscribe.ActivitySubscribe;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityTeacherCoursesList;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityCourseRegistring;

/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileAmozeshgah extends Fragment implements OnMapReadyCallback, PresentUser.OnPresentUserLitener, View.OnClickListener, PresentTeacher.OnPresentTeacherListener, PresentUpload.OnPresentUploadListener, PresentSubscribe.OnPresentSubscribeListener {

    TextView txtUpload;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    TextView txtPhone;
    TextView txtLandPhone;
    TextView txtSubscribe_up;
    TextView txtSubscribe_down;
    TextView txtname;
    TextView txtDescription;
    TextView txtAddress;
    TextView txtSubject;
    RelativeLayout RLPbar;
    View view;
    LinearLayout btnUpload;
    LinearLayout btnSubscribe;
    public Object object;
    public int flagMadrak = 0;
    boolean haveASubscribe = false;
    boolean subError = false;
    ProgressBar pbarSubscribeData;
    public static StBuy subscribeData;

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
        checkMadrak();
        getUserSubscribeData();
        if (Pref.getBollValue(PrefKey.profileTeacherPage, true))
            showDialogForHelper();
        txtSubject.setText(Pref.getStringValue(PrefKey.subject, ""));
        txtname.setText(Pref.getStringValue(PrefKey.userName, ""));
        txtPhone.setText(Pref.getStringValue(PrefKey.email, ""));
        txtLandPhone.setText(Pref.getStringValue(PrefKey.landPhone, ""));
        txtAddress.setText(Pref.getStringValue(PrefKey.address, ""));

    }

    private void getUserSubscribeData() {

        (new PresentSubscribe(this)).getUserBuy();
    }

    private void showDialogForHelper() {

        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("راهنما");
        builder.setMessage("آیا مایل به مشاهده راهنما هستید؟");
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Pref.saveBollValue(PrefKey.profileTeacherPage, false);
                dialog.cancel();
            }
        });
        builder.setNeutralButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((ActivityProfile) object).runHelperForTeacher();
            }
        });
        builder.show();
    }


    private void checkMadrak() {
        RLPbar.setVisibility(View.VISIBLE);
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getMadrakStateAndRat();
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

        btnUpload = (LinearLayout) view.findViewById(R.id.btnUpload);
        RLPbar = (RelativeLayout) view.findViewById(R.id.rlPbarFragmentProfileTeacher);
        btnSubscribe = (LinearLayout) view.findViewById(R.id.btnSubscribe);
        txtUpload = (TextView) view.findViewById(R.id.txtUpload);
        txtSubscribe_up = (TextView) view.findViewById(R.id.txtSubscribt_up);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionProfileTeacher);
        txtAddress = (TextView) view.findViewById(R.id.txtAddressProfileTeacher);
        txtSubscribe_down = (TextView) view.findViewById(R.id.txtSubscribe_down);
        pbarSubscribeData = (ProgressBar) view.findViewById(R.id.pbarUserSubscribeData);
        txtname = (TextView) view.findViewById(R.id.txtNameProfileTeacher);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneProfileTeacher);
        txtLandPhone = (TextView) view.findViewById(R.id.txtLandPhoneProfileTeacher);
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectProfileTeacher);

        btnUpload.setOnClickListener(this);
        btnSubscribe.setOnClickListener(this);
    }

    public void setNavigationItemListener(BottomNavigationView bottomNav) {

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.messageBoxBottomNavTeacher:
                        starterActivity(ActivitySmsBox.class);
                        return true;

                    case R.id.addListBottomNavTeacher:
                        starterActivity(ActivityTeacherCoursesList.class);
                        return true;

                    case R.id.registerCourseBottomNavTeacher:
                        starterActivity(ActivitySabtenamList.class);
                        return true;

                    case R.id.addedCourseBottomNavTeacher:
                        addCourse();
                        return true;

                    case R.id.logOutBottomNavTeacher:
                        showAlertDialog("خروج از حساب", "آیا می خواهید از حساب کاربری خود خارج شوید", "بله", "خیر");
                        return true;
                }

                return false;
            }
        });
    }

    private void showAlertDialog(String title, String message, String buttonTrue, String btnFalse) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(G.context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton(buttonTrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                queryForLogOut();
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

    private void queryForLogOut() {
        RLPbar.setVisibility(View.VISIBLE);
        PresentUser presentUser = new PresentUser(this);
        presentUser.logOut(Pref.getStringValue(PrefKey.email, ""));
    }

    private void addCourse() {
        if (flagMadrak == 0)
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "برای ثبت دوره مدرک شما باید تایید شده باشد", "بارگذاری مدرک", "متوجه شدم", "");
        else if (flagMadrak == 1)
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "مدرک شما در انتظار تایید است,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        else if (!haveASubscribe)
            showDialogForMadrakState("اشتراک", "شما هیچ اشتراک فعالی ندارید.", "", "متوجه شدم", "");
        else
            starterActivity(ActivityCourseRegistring.class);
    }

    private void showDialogForMadrakState(String title, String message, String buttonTrue, String btnFalse, String btnNeutral) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(G.context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton(buttonTrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadMadrak();
                dialog.cancel();

            }
        });
        alertDialog.setNegativeButton(btnFalse, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.setNeutralButton(btnNeutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callUs();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void callUs() {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:05431132499"));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(G.context, "خطا!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadMadrak() {
        if (txtUpload.getText().equals("بارگذاری مدرک آموزشی")) {
            ((ActivityProfile) object).getFilesPath();
        } else if (txtUpload.getText().equals("مدرک شما در انتظار تایید است")) {
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "مدرک شما در انتظار تایید است,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        } else if (txtUpload.getText().equals("مدرک شما تایید شده")) {
            sendMessageFTT(txtUpload.getText().toString());
        }
    }

    public void uploadFile(String path) {
        //isResponseForImage = false;
        RLPbar.setVisibility(View.VISIBLE);
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("madrak", Pref.getStringValue(PrefKey.email, "") + ".pdf", path);

    }

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpload:
                uploadMadrak();
                break;

            case R.id.btnSubscribe:
                if (pbarSubscribeData.getVisibility() == View.GONE)
                    subData();
                break;
        }
    }

    private void subData() {
        if (flagMadrak == 2 && !subError) {
            Intent intent = new Intent(G.context, ActivitySubscribe.class);
            intent.putExtra("haveASubscribe", !(txtSubscribe_up.getText().toString().equals("خرید اشتراک")));
            startActivityForResult(intent, 5);
        } else if (flagMadrak == 1) {
            showDialogForMadrakState("خطا", "ابتدا باید مدرک شما تایید شود,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        } else if (flagMadrak == 0) {
            Toast.makeText(G.context, "ابتدا مدرک خود را بارگذاری کنید.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendMessageFUT(String message) {
        RLPbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LogOut(boolean flag) {
        RLPbar.setVisibility(View.GONE);
        Pref.removeValue(PrefKey.email);
        Pref.removeValue(PrefKey.apiCode);
        Pref.removeValue(PrefKey.userName);
        Pref.removeValue(PrefKey.location);
        Pref.removeValue(PrefKey.cityId);
        Pref.removeValue(PrefKey.subject);
        Pref.removeValue(PrefKey.address);
        Pref.removeValue(PrefKey.userTypeMode);
        Pref.removeValue(PrefKey.landPhone);
        Pref.removeValue(PrefKey.madrak);
        Pref.removeValue(PrefKey.lat);
        Pref.removeValue(PrefKey.lon);
        Pref.removeValue(PrefKey.IsLogin);
        G.activity.finish();
    }

    @Override
    public void LogIn(ResponseOfServer res) {

    }

    @Override
    public void LogUp(ResponseOfServer res) {

    }

    @Override
    public void onReceiveUser(ArrayList<StUser> students) {

    }

    @Override
    public void sendMessageFTT(String message) {
        RLPbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        RLPbar.setVisibility(View.GONE);
        if (flag) {
            sendMessageFTT("بارگذاری شد");
            txtUpload.setText("مدرک شما در انتظار تایید است");
            flagMadrak = 1;
        } else
            showAlertDialog("خطا", "خطا در بارگذاری لطفا بعدا امتحان کنید.", "", "قبول");
    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> users) {

    }

    @Override
    public void onReceiveCustomeTeacherListData(ArrayList<StCustomTeacherListHome> data) {

    }

    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> users) {

    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {
        RLPbar.setVisibility(View.GONE);
        ActivityProfile.ratingBar.setRating(res.code);
        if ((new String(Base64.decode(Base64.decode(res.ms, Base64.DEFAULT), Base64.DEFAULT))).equals("notbar")) {
            txtUpload.setText("بارگذاری مدرک آموزشی");
            flagMadrak = 0;
        } else if ((new String(Base64.decode(Base64.decode(res.ms, Base64.DEFAULT), Base64.DEFAULT))).equals("yesbarnotok")) {
            txtUpload.setText("مدرک شما در انتظار تایید است");
            flagMadrak = 1;
        } else if ((new String(Base64.decode(Base64.decode(res.ms, Base64.DEFAULT), Base64.DEFAULT))).equals("barok")) {
            txtUpload.setText("مدرک شما تایید شده");
            flagMadrak = 2;
        }
    }

    @Override
    public void messageFromUpload(String message) {
        RLPbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {
        RLPbar.setVisibility(View.GONE);

        if (res.code == 1) {
            RLPbar.setVisibility(View.VISIBLE);
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.upMs();
        } else {
            showAlertDialog("خطا", "خطا در بارگذاری لطفا بعدا امتحان کنید.", "", "قبول");
        }
    }

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {
        RLPbar.setVisibility(View.GONE);
    }

    @Override
    public void sendMessageFromSubscribe(String message) {
        pbarSubscribeData.setVisibility(View.GONE);
        subError = true;
        txtSubscribe_up.setText(message);
    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {
        pbarSubscribeData.setVisibility(View.GONE);
        if (data.get(0).empty == 1) {
            txtSubscribe_up.setText("خرید اشتراک");
            txtSubscribe_down.setText("برای ثبت دوره ابتدا اشتراک خود را فعال کنید");
        } else {
            subscribeData = data.get(0);
            txtSubscribe_up.setText("اشتراک " + data.get(0).subjectSubscribe);
            if (data.get(0).vaziat == 1) {
                txtSubscribe_down.setText(data.get(0).remainingCourses + " دوره باقی مانده");
                haveASubscribe = true;
            } else
                txtSubscribe_down.setText("اشتراک شما به پایان رسیده");
        }
    }

    @Override
    public void onReceiveFlagFromSubscribe(boolean flag) {
        pbarSubscribeData.setVisibility(View.GONE);
    }

}
