package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_karbar;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import java.util.ArrayList;
import java.util.Random;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityTeacherRegistering;


/**
 * Created by MAHNAZ on 10/23/2017.
 */

public class FragmentProfileKarbar extends Fragment implements PresentUser.OnPresentUserLitener {
    public boolean mapIsShow = false;
    TextView txtName;
    TextView txtPhone;
    View view;
    DialogProgres dialogProgres;
    Typeface typeface;
    BottomNavigationView bottomNav;
    ImageView imgProfile;
    ImageButton btnHome;
    ImageButton btnHelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_karbar, container, false);
        init();
        if (Pref.getBollValue(PrefKey.profileUserPage, true))
            showDialogForHelper();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context, false);
        pointers();
        setFont();
        txtPhone.setText(Pref.getStringValue(PrefKey.email, ""));
        txtName.setText(Pref.getStringValue(PrefKey.userName, ""));

    }

    private void setFont() {
        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtName.setTypeface(typeface);
        txtPhone.setTypeface(typeface);
    }

    private void pointers() {
        btnHome = (ImageButton) view.findViewById(R.id.imgButtonHomeProfileKarbar);
        btnHelp = (ImageButton) view.findViewById(R.id.imageButtonHelpUserProfile);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.activity.finish();
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runHelperForUser();
            }
        });
        imgProfile = (ImageView) view.findViewById(R.id.imgProfileUser);
        setUserImage();
        bottomNav = (BottomNavigationView) view.findViewById(R.id.bottomNavProfileUser);
        G.disableShiftModeNavigation(bottomNav);
        setNavigationItemListener();
        txtName = (TextView) view.findViewById(R.id.txtUserNameProfile);
        txtPhone = (TextView) view.findViewById(R.id.txtUserIdProfile);

    }

    private void setUserImage() {
        int img;
        if (((new Random()).nextInt(10)) % 2 == 0)
            img = R.drawable.user1;
        else
            img = R.drawable.user2;
        Glide.with(G.context)
                .load(img)
                .fitCenter()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(imgProfile);
    }

    private void setNavigationItemListener() {

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.messageBoxBottomNavUser:
                        starterActivity(ActivitySmsBox.class);
                        return true;

                    case R.id.mapBottomNavUser:
                        if (!mapIsShow) {
                            ActivityProfile.replaceContentWith(new FragmentMap(), R.id.contentProfileUser);
                            mapIsShow = true;
                        } else {
                            mapIsShow = false;
                            ActivityProfile.replaceContentWith(new FragmentProfileKarbar(), R.id.contentProfileUser);
                        }
                        return true;

                    case R.id.registerCourseBottomNavUser:
                        starterActivity(ActivitySabtenamList.class);
                        return true;

                    case R.id.trendingUpBottomNavUser:
                        starterActivity(ActivityTeacherRegistering.class);
                        G.activity.finish();
                        return true;

                    case R.id.logOutBottomNavUser:
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
        presentUser.logOut(Pref.getStringValue(PrefKey.email, ""));
    }

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
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

    private void showDialogForHelper() {

        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setTitle("راهنما");
        builder.setMessage("آیا مایل به مشاهده راهنما هستید؟");
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Pref.saveBollValue(PrefKey.profileUserPage, false);
                dialog.cancel();
            }
        });
        builder.setNeutralButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                runHelperForUser();
            }
        });
        builder.show();
    }

    private void runHelperForUser() {
        SimpleTarget map = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.mapBottomNavUser))
                .setRadius(100f)
                .setTitle("نقشه")
                .setDescription("نقشه حاوی موقعیت مکانی آموزشگاهای ثبت شده می باشد")
                .build();
        SimpleTarget sabtenam = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.registerCourseBottomNavUser))
                .setRadius(100f)
                .setTitle("لیست دوره های ثبت نام شده")
                .setDescription("برای مشاهده دوره هایی که درخواست ثبت نام داده اید به این قسمت مراجعه کنید")
                .build();
        SimpleTarget sms = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.messageBoxBottomNavUser))
                .setRadius(100f)
                .setTitle("صندوق پیام")
                .setDescription("پیام های ارسالی و دریافتی برای شما")
                .build();
        SimpleTarget trend = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.trendingUpBottomNavUser))
                .setRadius(100f)
                .setTitle("ارتقا کاربری به مدرس")
                .setDescription("برای اینکه بتوانید دوره ثبت کنید باید کاربری خودرا به مدرس یا آموزشگاه ارتقا داده")
                .build();
        SimpleTarget logout = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.logOutBottomNavUser))
                .setRadius(100f)
                .setTitle("خروج")
                .setDescription("خروج از حساب کاربری")
                .build();
        SimpleTarget helper = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.imageButtonHelpUserProfile))
                .setRadius(100f)
                .setTitle("راهنما")
                .setDescription("برای مشاهده مجدد این راهنما")
                .build();


        Spotlight.with(G.activity)
                .setOverlayColor(Color.argb(220, 100, 50, 70))
                .setDuration(500L)
                .setAnimation(new DecelerateInterpolator(4f))
                .setTargets(map, sabtenam, sms, trend, logout, helper)
                .setClosedOnTouchedOutside(true)
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                    @Override
                    public void onStarted() {
                    }
                })
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                    @Override
                    public void onEnded() {
                        Pref.saveBollValue(PrefKey.profileUserPage, false);
                    }
                })
                .start();
    }

}



