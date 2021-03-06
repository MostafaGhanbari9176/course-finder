package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import ir.mahoorsoft.app.cityneed.model.CheckedSTatuse;
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
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityCourseRegistring;

/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileAmozeshgah extends Fragment implements OnMapReadyCallback, PresentUser.OnPresentUserLitener, View.OnClickListener, PresentTeacher.OnPresentTeacherListener, PresentUpload.OnPresentUploadListener, PresentSubscribe.OnPresentSubscribeListener {

    RelativeLayout pBar;
    LinearLayout btnBox;
    LinearLayout pBarBox;
    TextView txtUpload;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    TextView txtPhone;
    TextView txtLandPhone;
    TextView txtSubscribe_up;
    TextView txtSubscribe_down;
    TextView txtname;
    TextView txtAddress;
    TextView txtSubject;
    DialogProgres dialogProgres;
    View view;
    LinearLayout btnUpload;
    LinearLayout btnSubscribe;
    public Object object;
    public int flagMadrak = 0;
    boolean haveASubscribe = false;
    boolean subError = false;
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
        if (Pref.getBollValue(PrefKey.profileTeacherPage, true))
            showDialogForHelper();
        txtSubject.setText(Pref.getStringValue(PrefKey.subject, ""));
        txtname.setText(Pref.getStringValue(PrefKey.userName, ""));
        txtPhone.setText(Pref.getStringValue(PrefKey.email, ""));
        txtLandPhone.setText(Pref.getStringValue(PrefKey.landPhone, ""));
        txtAddress.setText(Pref.getStringValue(PrefKey.address, ""));

    }

    public void getUserSubscribeData() {
        setVisiblePbar();
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
        setVisiblePbar();
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
        pBarBox = (LinearLayout) view.findViewById(R.id.LLPbarProfileTeacher);
        btnBox = (LinearLayout) view.findViewById(R.id.LLBtnBoxProfile);
        pBar = (RelativeLayout) view.findViewById(R.id.RLPbarProfileTeacher);
        btnUpload = (LinearLayout) view.findViewById(R.id.btnUpload);
        dialogProgres = new DialogProgres(G.context, false);
        btnSubscribe = (LinearLayout) view.findViewById(R.id.btnSubscribe);
        txtUpload = (TextView) view.findViewById(R.id.txtUpload);
        txtSubscribe_up = (TextView) view.findViewById(R.id.txtSubscribt_up);
        txtAddress = (TextView) view.findViewById(R.id.txtAddressProfileTeacher);
        txtSubscribe_down = (TextView) view.findViewById(R.id.txtSubscribe_down);
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
        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.logOut(Pref.getStringValue(PrefKey.apiCode, ""));
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
            Toast.makeText(G.context, txtUpload.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadFile(String path) {
        //isResponseForImage = false;
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("madrak", Pref.getStringValue(PrefKey.email, "") + ".madrak.pdf", path);

    }

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(Double.parseDouble(Pref.getStringValue(PrefKey.lat, "29.4892550")), Double.parseDouble(Pref.getStringValue(PrefKey.lon, "60.8612360")));

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
            showDialogForMadrakState("توجه", "ابتدا باید مدرک شما تایید شود,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        } else if (flagMadrak == 0) {
            Toast.makeText(G.context, "ابتدا مدرک خود را بارگذاری کنید.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LogOut(boolean flag) {
        dialogProgres.closeProgresBar();
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
        pBar.setVisibility(View.GONE);
        getUserSubscribeData();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag) {
            Toast.makeText(G.context, "بارگذاری شد", Toast.LENGTH_SHORT).show();
            txtUpload.setText("مدرک شما در انتظار تایید است");
            (new CheckedSTatuse()).sendEmail(Pref.getStringValue(PrefKey.email, "madrak"));
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
        pBar.setVisibility(View.GONE);
        getUserSubscribeData();
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
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        if (res.code == 1) {
            dialogProgres.showProgresBar();
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.upMs();
        } else {
            showAlertDialog("خطا", "خطا در بارگذاری لطفا بعدا امتحان کنید.", "", "قبول");
        }
    }

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {
        dialogProgres.closeProgresBar();
    }

    @Override
    public void sendMessageFromSubscribe(String message) {
        pBar.setVisibility(View.GONE);
        subError = true;
        txtSubscribe_up.setText(message);
    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {
        pBar.setVisibility(View.GONE);
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
        pBar.setVisibility(View.GONE);
    }

    private void setVisiblePbar() {
        pBar.setVisibility(View.VISIBLE);
        int h = btnBox.getHeight();
        if (h != 0)
            pBarBox.getLayoutParams().height = h;

    }

}
