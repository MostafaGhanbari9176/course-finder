package ir.mahoorsoft.app.cityneed.view.activity_profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.bumptech.glide.signature.MediaStoreSignature;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;
import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentSubscribe;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.FragmentProfileAmozeshgah;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_karbar.FragmentProfileKarbar;
import ir.mahoorsoft.app.cityneed.view.activity_subscribe.ActivitySubscribe;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityProfile extends AppCompatActivity implements PresentUpload.OnPresentUploadListener, PresentTeacher.OnPresentTeacherListener, PresentSubscribe.OnPresentSubscribeListener {
    private Toolbar tlb;
    boolean isResponseForImage = false;
    FragmentProfileAmozeshgah teacher;
    FragmentProfileKarbar user;
    DialogProgres dialogProgres;
    ImageView imgAppBar;
    BottomNavigationView bottomNav;
    AppBarLayout appBarLayout;
    NestedScrollView scrollView;
    public static RatingBar ratingBar;
    ImageButton btnHelp;
    ImageButton btnHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        checkSubInventory();
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        pointers();
        checkUserType();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("?????????????? ???????????? ??????");
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkSubInventory() {
        if (Pref.getBollValue(PrefKey.isPaymentSuccess, false) && !(Pref.getBollValue(PrefKey.isPaymentSaved, false))) {
            (new PresentSubscribe(this)).saveUserBuy(Pref.getStringValue(PrefKey.refId, "NOK"));
        }
    }

    private void pointers() {
        btnHelp = (ImageButton) findViewById(R.id.imageButtonHelpTeacherProfile);
        btnHome = (ImageButton) findViewById(R.id.imageButtonHomeTeacherProfile);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runHelperForTeacher();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ratingBar = (RatingBar) findViewById(R.id.ratBarProfile);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarProfile);
        scrollView = (NestedScrollView) findViewById(R.id.nestedScrollview);
        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavProfileTeacher);
        G.disableShiftModeNavigation(bottomNav);
        imgAppBar = (ImageView) findViewById(R.id.app_bar_image);
        imgAppBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) != 0)
                    selectImage();
                return false;
            }
        });
        setImgUrl();
        tlb = (Toolbar) findViewById(R.id.tlbProfile);
        setPaletteSize();
    }

    private void selectImage() {
        Intent intent = new Intent(G.context, ActivityFiles.class);
        intent.putExtra("fileKind", "image");
        startActivityForResult(intent, 2);
    }

    private void setPaletteSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        G.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        appBarLayout.getLayoutParams().height = (int) (width / 1.5);
    }

    public void setImgUrl() {
        Glide.with(G.context)
                .load(ApiClient.serverAddress + "/city-need/v1/uploads/teacher/" + Pref.getStringValue(PrefKey.pictureId, "") + ".png")
                .fitCenter()
                .error(R.drawable.university)
                .into(imgAppBar);
    }

    @Override
    public void onBackPressed() {
        if (user != null && user.mapIsShow && teacher == null) {
            user.mapIsShow = false;
            replaceContentWith(user, R.id.contentProfileUser);
        } else {
            this.finish();
            super.onBackPressed();
        }
    }

    public static void replaceContentWith(Fragment fragment, int contentId) {
        G.activity.getSupportFragmentManager().beginTransaction().
                replace(contentId, fragment).commit();

    }

    @Override
    protected void onResume() {
        G.activity = this;
        G.context = this;
        super.onResume();
    }

    public void checkUserType() {
        switch (Pref.getIntegerValue(PrefKey.userTypeMode, 0)) {
            case 0:
                user = new FragmentProfileKarbar();
                replaceContentWith(user, R.id.contentProfileUser);
                scrollView.setVisibility(View.GONE);
                appBarLayout.setVisibility(View.GONE);
                bottomNav.setVisibility(View.GONE);
                break;
            case 1:
            case 2:
                teacher = new FragmentProfileAmozeshgah();
                teacher.object = this;
                replaceContentWith(teacher, R.id.contentProfileTeacher);
                teacher.setNavigationItemListener(bottomNav);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    teacher.uploadFile(data.getStringExtra("path"));
                    return;
                }
                if (requestCode == 2) {
                    uploadImage(data.getStringExtra("path"));
                    return;
                }
                if (data.getStringExtra("Gift").equals("Gift")) {
                    teacher.getUserSubscribeData();
                    return;
                }
                if (data.getBooleanExtra("buyResult", false)) {
                    Pref.removeValue(PrefKey.isPaymentSaved);
                    this.finish();
                    return;
                }
                if (!(data.getBooleanExtra("buyResult", false))) {
                    this.finish();
                    return;
                }
            }
        } catch (Exception ignored) {
            this.finish();
        }

        if (ActivitySubscribe.Ending)
            this.finish();
    }

    private void uploadImage(String path) {
        isResponseForImage = true;
        dialogProgres = new DialogProgres(this, "?????????? ????????????????");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("teacher", Pref.getStringValue(PrefKey.pictureId, "") + "wIzArD.png", path);

    }

    @Override
    public void messageFromUpload(String message) {
        sendMessageFTT(message);
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        if (res.code == 1 && !isResponseForImage) {
            dialogProgres.showProgresBar();
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.upMs();
        } else if (res.code == 1) {
            if (teacher != null)
                messageFromUpload("???????????????? ???? ?????????? ?????????? ?????? ???????? ?????????? ??????");
        } else if (res.code == 0)
            messageFromUpload("???????????? 5 ??????????????");

    }

    public void getFilesPath() {
        Intent intent = new Intent(G.context, ActivityFiles.class);
        intent.putExtra("fileKind", "pdf");
        startActivityForResult(intent, 1);
    }

    private void showAlertDialog(String title, String message, String buttonTrue, String btnFalse) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(G.context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton(buttonTrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    public void runHelperForTeacher() {

        SimpleTarget starter = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.registerCourseBottomNavTeacher))
                .setRadius(1f)
                .setTitle("???????? ???? ?????????? ???? ???? ???? ?????? ?????????? ?????? ?? ?????? ?????? ???????????? ??????????")
                .setDescription("???? ?????????? ???????? ?????? ???? ?????? ???? ?????????? ???????????? ???????? ?????? ???? ???????????? ???????? ?????? ?????? ???? ?????? ???????? ???? ???? ?????? ???? ???????? ???????????? ???? ???????????? ?????????????? ?????? ?????? ???????? ?? ???? ???? ?????????? ???? ???????? ?????? ???? ???????? ???????????? ?? ???????????? ?????????? ???????? ?????? ?????? ?????????? ???? ??????.")
                .build();

        SimpleTarget sabtenam = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.registerCourseBottomNavTeacher))
                .setRadius(100f)
                .setTitle("???????? ???????? ?????? ?????? ?????? ??????")
                .build();
        SimpleTarget add = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.addedCourseBottomNavTeacher))
                .setRadius(100f)
                .setTitle("?????????? ???????? ????????")
                .setDescription("???????? ???????????? ???? ???????? ???????? ???? ?????? ???????? ?????????? ????????")
                .build();
        SimpleTarget sms = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.messageBoxBottomNavTeacher))
                .setRadius(100f)
                .setTitle("?????????? ????????")
                .setDescription("???????? ?????? ???????????? ?? ?????????????? ???????? ??????")
                .build();

        SimpleTarget madrak = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.btnUpload))
                .setRadius(300f)
                .setTitle("???????? ???? ???????? ????????????")
                .setDescription("?????? ???????????????? ???????? ???? ?????? ???????? ?????????? ????????")
                .build();

        SimpleTarget subscribe = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.btnSubscribe))
                .setRadius(300f)
                .setTitle("???????? ????????????")
                .setDescription("?????? ???????? ???????????? ???? ?????? ???????? ?????????? ????????")
                .build();

        SimpleTarget helper = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.imageButtonHelpTeacherProfile))
                .setRadius(300f)
                .setTitle("????????????")
                .setDescription("???????????? ???????? ?????? ????????????")
                .build();

        SimpleTarget addList = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.addListBottomNavTeacher))
                .setRadius(100f)
                .setTitle("???????? ???????? ?????? ?????????? ?????? ???????? ??????")
                .setDescription("???????? ???????????? ???????? ???????????? ???? ???????? ?????????? ???? ?????? ???????? ???????? ?? ???? ???????? ???????? ???? ???????? ???????? ???????????? ???? ???????????? ??????")
                .build();
        SimpleTarget logout = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.logOutBottomNavTeacher))
                .setRadius(100f)
                .setTitle("????????")
                .setDescription("???????? ???? ???????? ????????????")
                .build();

        SimpleTarget img = new SimpleTarget.Builder(G.activity).setPoint(findViewById(R.id.app_bar_image))
                .setRadius(300f)
                .setTitle("?????????? ????????????????")
                .setDescription("???????? ?????????? ?????? ???????? ?? ?????? ??????????")
                .build();

        Spotlight.with(G.activity)
                .setOverlayColor(Color.argb(220, 100, 50, 70))
                .setDuration(500L)
                .setAnimation(new DecelerateInterpolator(4f))
                .setTargets(starter, sabtenam, add, sms, addList, logout, madrak, subscribe, img, helper)
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

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag) {
            sendMessageFTT("?????? ???? ?????? ??????????");
            teacher.flagMadrak = 0;
        } else {
            sendMessageFTT("???????????????? ????");
            teacher.flagMadrak = 1;
        }
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
    }

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {

    }

    @Override
    public void sendMessageFromSubscribe(String message) {

    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {

    }

    @Override
    public void onReceiveFlagFromSubscribe(boolean flag) {
        Pref.saveBollValue(PrefKey.isPaymentSaved, flag);
        if (flag) {
            Pref.removeValue(PrefKey.SubId);
            Pref.removeValue(PrefKey.isPaymentSuccess);
            Pref.removeValue(PrefKey.refId);
        }
    }
}


