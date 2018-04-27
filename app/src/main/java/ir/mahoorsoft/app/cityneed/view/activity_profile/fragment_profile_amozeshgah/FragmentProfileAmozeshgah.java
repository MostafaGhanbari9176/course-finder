package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.acivity_launcher.FragmentErrorServer;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityTeacherCoursesList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityCourseRegistring;

/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileAmozeshgah extends Fragment implements OnMapReadyCallback, PresentUser.OnPresentUserLitener, View.OnClickListener, PresentTeacher.OnPresentTeacherListener {
    ImageView imgProfile;
    TextView txtUpload;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    TextView txtPhone;
    TextView txtLandPhone;
    TextView txtSubscribe_up;
    TextView txtSubscribe_down;
    TextView txtname;
    TextView txtSubject;
    DialogProgres dialogProgres;
    View view;
    BottomNavigationView bottomNav;
    LinearLayout btnUpload;
    LinearLayout btnSubscribe;
    RatingBar ratingBar;

    public int flagMadrak = 0;
    boolean haveASubscribe = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_teacher, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        settingUpMap();
        pointers();
        checkMadrak();
        setImageSize();
        setImgUrl();
        if (Pref.getBollValue(PrefKey.profileTeacherPage, true))
            showDialogForHelper();
        txtSubject.setText(Pref.getStringValue(PrefKey.subject, ""));
        txtname.setText(Pref.getStringValue(PrefKey.userName, ""));
        txtPhone.setText(Pref.getStringValue(PrefKey.phone, ""));
        txtLandPhone.setText(Pref.getStringValue(PrefKey.landPhone, ""));

    }

    private void showDialogForHelper() {

        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("راهنما");
        builder.setMessage("آیا مایل به مشاهده راهنما هستید؟");
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNeutralButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                runHelperForTeacher();
            }
        });
        builder.show();
    }

    private void runHelperForTeacher() {

        SimpleTarget starter = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.registerCourseBottomNavTeacher))
                .setRadius(1f)
                .setTitle("تبریک و تشکر از اینکه به ما در جهت توسعه کسب و کار خود اعتماد کردید")
                .setDescription("در ابتدا باید شما از سمت ما تایید اعتبار شوید سپس می توانید دوره های خود را ثبت کنید پس از ثیت هر دوره محصلین می توانند درخواست ثبتنام دهند و پس از تایید از جانب شما از طریق برنامه و مراجعه حضوری محصل ثبتنام نهایی می شود.")
                .build();

        SimpleTarget sabtenam = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.registerCourseBottomNavTeacher))
                .setRadius(100f)
                .setTitle("لیست")
                .setDescription("لیست دوره های ثبتنام شده")
                .build();
        SimpleTarget add = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.addedCourseBottomNavTeacher))
                .setRadius(100f)
                .setTitle("دوره")
                .setDescription("اضافه کردن دوره")
                .build();
        SimpleTarget sms = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.messageBoxBottomNavTeacher))
                .setRadius(100f)
                .setTitle("صندوق پیام")
                .setDescription("پیام های ارسالی و دریافتی برای شما")
                .build();
        SimpleTarget madrak = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.btnUpload))
                .setRadius(300f)
                .setTitle("مدرک یا مجوز آموزشی")
                .setDescription("جهت بارگزاری مدرک از این قسمت اقدام کنید")
                .build();
        SimpleTarget addList = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.addListBottomNavTeacher))
                .setRadius(100f)
                .setTitle("لیست دوره")
                .setDescription("لیست دوره های اضافه شده توسط شما")
                .build();
        SimpleTarget logout = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.logOutBottomNavTeacher))
                .setRadius(100f)
                .setTitle("خروج")
                .setDescription("خروج از حساب کاربری")
                .build();
        SimpleTarget img = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.imgProfileTeacher))
                .setRadius(300f)
                .setTitle("تصویر آموزشگاه")
                .setDescription("برای تغییر لمس کرده و نگه دارید")
                .build();


        Spotlight.with(G.activity)
                .setOverlayColor(ContextCompat.getColor(G.context, R.color.blue_ios))
                .setDuration(500L)
                .setAnimation(new DecelerateInterpolator(4f))
                .setTargets(starter, sabtenam, add, sms, madrak, addList, logout, img)
                .setClosedOnTouchedOutside(true)
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                    @Override
                    public void onStarted() {

                    }
                })
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                    @Override
                    public void onEnded() {
                        Pref.saveBollValue(PrefKey.profileTeacherPage, false);
                    }
                })
                .start();
    }

    private void checkMadrak() {
        dialogProgres.showProgresBar();
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
        ratingBar = (RatingBar) view.findViewById(R.id.ratBarTeacher);
        btnUpload = (LinearLayout) view.findViewById(R.id.btnUpload);
        btnSubscribe = (LinearLayout) view.findViewById(R.id.btnSubscribe);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfileTeacher);
        imgProfile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) != 0)
                    selectImage();
                return false;
            }
        });
        txtUpload = (TextView) view.findViewById(R.id.txtUpload);
        txtSubscribe_up = (TextView) view.findViewById(R.id.txtSubscribt_up);
        txtSubscribe_down = (TextView) view.findViewById(R.id.txtSubscribe_down);
        bottomNav = (BottomNavigationView) view.findViewById(R.id.bottomNavProfileTeacher);
        bottomNav.setBackgroundColor(ContextCompat.getColor(G.context, R.color.pink_tel));
        G.disableShiftModeNavigation(bottomNav);
        setNavigationItemListener();
        txtname = (TextView) view.findViewById(R.id.txtNameProfileTeacher);
        txtPhone = (TextView) view.findViewById(R.id.txtPhoneProfileTeacher);
        txtLandPhone = (TextView) view.findViewById(R.id.txtLandPhoneProfileTeacher);
        txtSubject = (TextView) view.findViewById(R.id.txtSubjectProfileTeacher);

        btnUpload.setOnClickListener(this);
        btnSubscribe.setOnClickListener(this);
    }

    private void setImageSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        G.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        imgProfile.getLayoutParams().height = (int) (width / 1.5);
    }

    private void selectImage() {
        Intent intent = new Intent(G.context, ActivityFiles.class);
        intent.putExtra("isImage", true);
        startActivityForResult(intent, 2);
    }

    private void setNavigationItemListener() {

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
        presentUser.logOut(Pref.getStringValue(PrefKey.phone, ""));
    }

    private void addCourse() {
        if (flagMadrak == 0)
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "برای ثبت دوره مدرک شما باید تایید شده باشد", "بارگذاری مدرک", "متوجه شدم", "");
        else if (flagMadrak == 1)
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "مدرک شما در انتظار تایید است,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        else if (!haveASubscribe)
            showDialogForMadrakState("اشتراک", "شما هیچ اشتراک فعالی ندارید لطفا نسخه جدید برنامه را نصب کنید.", "", "متوجه شدم", "");
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
                uploalMadrak();
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

    private void uploalMadrak() {
        if (txtUpload.getText().equals("بارگذاری مدرک آموزشی")) {
            Intent intent = new Intent(G.context, ActivityFiles.class);
            intent.putExtra("isImage", false);
            startActivityForResult(intent, 1);
        } else if (txtUpload.getText().equals("مدرک شما در انتظار تایید است")) {
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "مدرک شما در انتظار تایید است,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        } else if (txtUpload.getText().equals("مدرک شما تایید شده")) {
            //sendMessageFTT(txtUpload.getText().toString());
        }
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

    public  void setImgUrl() {
        Glide.with(G.context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/teacher/" + Pref.getStringValue(PrefKey.pictureId, "") + ".png")
                .fitCenter()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(R.drawable.university)
                .into(imgProfile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpload:
                uploalMadrak();
                break;

            case R.id.btnSubscribe:
                break;
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
        Pref.removeValue(PrefKey.phone);
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
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag) {
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
        dialogProgres.closeProgresBar();
        ratingBar.setRating(res.code);
        if ((new String(Base64.decode(Base64.decode(res.bus, Base64.DEFAULT), Base64.DEFAULT))).equals("YoEkS"))
            haveASubscribe = true;
        else
            haveASubscribe = false;

        if ((new String(Base64.decode(Base64.decode(res.ms, Base64.DEFAULT), Base64.DEFAULT))).equals("error")) {
            ActivityProfile.replaceContentWith(new FragmentErrorServer());
        } else if ((new String(Base64.decode(Base64.decode(res.ms, Base64.DEFAULT), Base64.DEFAULT))).equals("notbar")) {
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
}
