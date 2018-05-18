package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_karbar;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
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
import ir.mahoorsoft.app.cityneed.view.acivity_launcher.FragmentErrorServer;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityTeacherCoursesList;
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
    String name;
    BottomNavigationView bottomNav;
    ImageView imgProfile;
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
        dialogProgres = new DialogProgres(G.context, "درحال بروزرسانی");
        pointers();
        setFont();
        txtPhone.setText(Pref.getStringValue(PrefKey.phone, ""));
        txtName.setText(Pref.getStringValue(PrefKey.userName, ""));

    }

    private void setFont() {
        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtName.setTypeface(typeface);
        txtPhone.setTypeface(typeface);
    }

    private void pointers() {
        imgProfile = (ImageView) view.findViewById(R.id.imgProfileUser);
        setUserImage();
        bottomNav = (BottomNavigationView) view.findViewById(R.id.bottomNavProfileUser);
        bottomNav.setBackgroundColor(ContextCompat.getColor(G.context, R.color.pink_tel));
        G.disableShiftModeNavigation(bottomNav);
        setNavigationItemListener();
        dialogProgres = new DialogProgres(G.context);
        txtName = (TextView) view.findViewById(R.id.txtUserNameProfile);
        txtPhone = (TextView) view.findViewById(R.id.txtUserIdProfile);
/*        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });*/


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
        presentUser.logOut(Pref.getStringValue(PrefKey.phone, ""));
    }

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(G.context, aClass);
        startActivity(intent);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        final EditText editText = new EditText(G.context);
        editText.setTypeface(typeface);
        builder.setView(editText);
        builder.setTitle("تغییر نام کاربری");
        builder.setMessage("از این نام برای ثبت نام شما در دوره های انتخابی استفاده می شود.");
        builder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText() != null) {
                    name = CharCheck.faCheck(editText.getText().toString().trim());
                    if (name.length() == 0)
                        showDialog();
                    else {
                        updateName(name);
                        dialog.cancel();
                    }
                }else
                    showDialog();
            }
        });

        builder.setNegativeButton("رد کردن", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
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


    private void updateName(String name) {
        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.updateUser(Pref.getStringValue(PrefKey.phone, ""), name);
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
                .setTitle("لیست")
                .setDescription("لیست دوره های ثبتنام شده")
                .build();
        SimpleTarget sms = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.messageBoxBottomNavUser))
                .setRadius(100f)
                .setTitle("صندوق پیام")
                .setDescription("پیام های ارسالی و دریافتی برای شما")
                .build();
        SimpleTarget trend = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.trendingUpBottomNavUser))
                .setRadius(100f)
                .setTitle("مدرس")
                .setDescription("ارتقا کاربری به مدرس")
                .build();
        SimpleTarget logout = new SimpleTarget.Builder(G.activity).setPoint(view.findViewById(R.id.logOutBottomNavUser))
                .setRadius(100f)
                .setTitle("خروج")
                .setDescription("خروج از حساب کاربری")
                .build();


        Spotlight.with(G.activity)
                .setOverlayColor(Color.argb(220, 100, 50, 70))
                .setDuration(500L)
                .setAnimation(new DecelerateInterpolator(4f))
                .setTargets(map, sabtenam, sms, trend, logout)
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



