package ir.mahoorsoft.app.cityneed.view.activity_profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

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
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.FragmentProfileAmozeshgah;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_karbar.FragmentProfileKarbar;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityProfile extends AppCompatActivity implements PresentUpload.OnPresentUploadListener, PresentTeacher.OnPresentTeacherListener {
    private Toolbar tlb;
    boolean isResponseForImage = false;
    static FragmentProfileAmozeshgah teacher;
    static FragmentProfileKarbar user;
    DialogProgres dialogProgres;
    ImageView imgAppBar;
    static BottomNavigationView bottomNav;
    static AppBarLayout appBarLayout;
    static NestedScrollView scrollView;
    public static RatingBar ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        pointers();
        checkUserType();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void pointers() {
        ratingBar = (RatingBar) findViewById(R.id.ratBarProfile);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarProfile);
        scrollView = (NestedScrollView) findViewById(R.id.nestedScrollview);
        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavProfileTeacher);
        bottomNav.setBackgroundColor(ContextCompat.getColor(G.context, R.color.pink_tel));
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
        intent.putExtra("isImage", true);
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
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/teacher/" + Pref.getStringValue(PrefKey.pictureId, "") + ".png")
                .fitCenter()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(R.drawable.university)
                .into(imgAppBar);
    }

    @Override
    public void onBackPressed() {
        if (user != null && user.mapIsShow) {
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
        //checkUserType();
        super.onResume();
    }

    public static void checkUserType() {
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
                replaceContentWith(teacher, R.id.contentProfileTeacher);
                teacher.setNavigationItemListener(bottomNav);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (data == null) {
                sendMessageFTT("خطا!!!");
                return;
            }
            if (resultCode == RESULT_OK) {
                if (requestCode == 1)
                    uploadFile(data.getStringExtra("path"));
                else if (requestCode == 2)
                    uploadImage(data.getStringExtra("path"));
            }

        } catch (Exception ex) {
            Toast.makeText(ActivityProfile.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(String path) {
        isResponseForImage = false;
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("madrak", Pref.getStringValue(PrefKey.phone, "") + ".pdf", path);

    }

    private void uploadImage(String path) {
        isResponseForImage = true;
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("teacher", Pref.getStringValue(PrefKey.pictureId, "") + ".png", path);

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
                setImgUrl();
        } else {
            showAlertDialog("خطا", "خطا در بارگذاری لطفا بعدا امتحان کنید.", "", "قبول");
        }
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

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {
        dialogProgres.closeProgresBar();
        if (!flag) {
            sendMessageFTT("خطا در بار گذاری");
            teacher.flagMadrak = 0;
        } else {
            sendMessageFTT("بارگذاری شد");
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
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                return false;
            default:
                return super.onTouchEvent(ev);
        }
    }
}


