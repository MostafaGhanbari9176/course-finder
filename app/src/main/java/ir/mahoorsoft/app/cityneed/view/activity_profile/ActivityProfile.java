package ir.mahoorsoft.app.cityneed.view.activity_profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentUpload;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activityFiles.ActivityFiles;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.FragmentProfileAmozeshgah;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_karbar.FragmentProfileKarbar;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityCourseRegistring;
import ir.mahoorsoft.app.cityneed.view.registering.ActivityTeacherRegistering;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityTeacherCoursesList;
import ir.mahoorsoft.app.cityneed.view.acivity_launcher.FragmentErrorServer;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;


/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener, PresentUser.OnPresentUserLitener, PresentTeacher.OnPresentTeacherListener, PresentUpload.OnPresentUploadListener {
    boolean mapIsShow = false;
    RatingBar ratBar;
    ImageView imgProfile;
    Button btnLogOut;
    Button btnAddCourse;
    Button btnListCourse;
    Button btnListAddCourse;
    Button btnSmsBox;
    Button btnMap;
    Button btnTrendingUp;
    TextView txtUpload;
    RelativeLayout rlUpload;
    LinearLayout llAddCourse;
    LinearLayout llMap;
    LinearLayout llListAddCourse;
    LinearLayout llTrendingUP;
    DialogProgres dialogProgres;
    int flagMadrak = 0;
    boolean haveASubscribe = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        setContentView(R.layout.activity_profile);
        pointer();
    }

    private void pointer() {

        ratBar = (RatingBar) findViewById(R.id.ratBarProfile);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        (btnLogOut = (Button) findViewById(R.id.btnLogOut)).setOnClickListener(this);
        (rlUpload = (RelativeLayout) findViewById(R.id.rlUploadMadrak)).setOnClickListener(this);
        (btnAddCourse = (Button) findViewById(R.id.btnAddCourseProfile)).setOnClickListener(this);
        (btnListAddCourse = (Button) findViewById(R.id.btnSabtenamListProfile)).setOnClickListener(this);
        (btnTrendingUp = (Button) findViewById(R.id.btnTrendingUpProfile)).setOnClickListener(this);
        (btnMap = (Button) findViewById(R.id.btnMapProfile)).setOnClickListener(this);
        (btnListCourse = (Button) findViewById(R.id.btnAddListProfile)).setOnClickListener(this);
        (btnSmsBox = (Button) findViewById(R.id.btnMessageBoxProfile)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnHelp)).setOnClickListener(this);
        txtUpload = (TextView) findViewById(R.id.txtUploadMadrak);
        llAddCourse = (LinearLayout) findViewById(R.id.llAddCourseProfile);
        llListAddCourse = (LinearLayout) findViewById(R.id.llAddCourseListProfile);
        llTrendingUP = (LinearLayout) findViewById(R.id.llTrendingUpProfile);
        llMap = (LinearLayout) findViewById(R.id.llMapProfil);

    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();
    }

    public void replaceContentWith(Fragment fragment) {
        G.activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.contentProfile, fragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogOut:
                showAlertDialog("خروج از حساب", "آیا می خواهید از حساب کاربری خود خارج شوید", "بله", "خیر");
                break;
            case R.id.btnAddCourseProfile:
                addCourse();
                break;
            case R.id.btnAddListProfile:
                starterActivity(ActivityTeacherCoursesList.class);
                break;
            case R.id.btnTrendingUpProfile:
                starterActivity(ActivityTeacherRegistering.class);
                this.finish();
                break;
            case R.id.btnSabtenamListProfile:
                starterActivity(ActivitySabtenamList.class);
                break;
            case R.id.btnMessageBoxProfile:
                starterActivity(ActivitySmsBox.class);
                break;

            case R.id.rlUploadMadrak:
                uploalMadrak();
                break;
            case R.id.btnHelp:
                runHelper();
                break;
            case R.id.btnMapProfile:
                if (!mapIsShow) {
                    replaceContentWith(new FragmentMap());
                    mapIsShow = true;
                } else {
                    mapIsShow = false;
                    checkUserType();
                }

                break;
        }
    }

    private void runHelper() {

        int typeMode = Pref.getIntegerValue(PrefKey.userTypeMode, 0);
        switch (typeMode) {
            case 0:
                showDialogForHelper("بله", "");
                break;
            case 1:
            case 2:
                showDialogForHelper("", "بله");
                break;
        }

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

    private void uploalMadrak() {
        if (txtUpload.getText().equals("بارگذاری مدرک آموزشی")) {
            Intent intent = new Intent(this, ActivityFiles.class);
            intent.putExtra("isImage", false);
            startActivityForResult(intent, 1);
        } else if (txtUpload.getText().equals("مدرک شما در انتظار تایید است")) {
            showDialogForMadrakState("مدرک یا مجوز آموزشی", "مدرک شما در انتظار تایید است,برای سرعت بخشیدن به روند تایید می توانید با ما تماس بگیرید.", "", "متوجه شدم", "تماس باما");
        } else if (txtUpload.getText().equals("مدرک شما تایید شده")) {
            sendMessageFTT(txtUpload.getText().toString());
        }
    }

    private void queryForLogOut() {
        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.logOut(Pref.getStringValue(PrefKey.phone, ""));
    }

    private void starterActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }


    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        this.finish();
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
    protected void onResume() {
        G.activity = this;
        G.context = this;
//        pointer();
        checkUserType();
        super.onResume();
    }

    private void checkUserType() {
        int typeMode = Pref.getIntegerValue(PrefKey.userTypeMode, 0);
        switch (typeMode) {
            case 0:
                rlUpload.setVisibility(View.GONE);
                llListAddCourse.setVisibility(View.GONE);
                llAddCourse.setVisibility(View.GONE);
                ratBar.setVisibility(View.GONE);
                setImage(R.drawable.defult, imgProfile);
                replaceContentWith(new FragmentProfileKarbar());
                if (Pref.getBollValue(PrefKey.profileUserPage, true))
                    showDialogForHelper("بله", "");
                break;
            case 1:
            case 2:
                checkMadrak();
                llTrendingUP.setVisibility(View.GONE);
                llMap.setVisibility(View.GONE);
                setImgUrl(Pref.getStringValue(PrefKey.pictureId, ""), imgProfile);
                replaceContentWith(new FragmentProfileAmozeshgah());
                if (Pref.getBollValue(PrefKey.profileTeacherPage, true))
                    showDialogForHelper("", "بله");
                break;
        }
    }

    private void checkMadrak() {
        dialogProgres.showProgresBar();
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getMadrakStateAndRat();
    }

    private void setImgUrl(String name, ImageView image) {
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/teacher/" + name + ".png")
                .fitCenter()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(R.drawable.defult)
                .into(image);
    }

    private void setImage(int img, ImageView image) {
        Glide.with(this)
                .load(img)
                .fitCenter()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(R.drawable.defult)
                .into(image);
    }

    private void showAlertDialog(String title, String message, String buttonTrue, String btnFalse) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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

    @Override
    public void sendMessageFTT(String message) {
        sendMessageFUT(message);
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
    public void responseForMadrak(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        ratBar.setRating(res.code);
        if ((new String(Base64.decode(Base64.decode(res.bus, Base64.DEFAULT), Base64.DEFAULT))).equals("YoEkS"))
            haveASubscribe = true;
        else
            haveASubscribe = false;

        if ((new String(Base64.decode(Base64.decode(res.ms, Base64.DEFAULT), Base64.DEFAULT))).equals("error")) {
            replaceContentWith(new FragmentErrorServer());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK) {
                uploadFile(data.getStringExtra("path"));
            }

        } catch (Exception ex) {
            Toast.makeText(ActivityProfile.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(String path) {
        dialogProgres = new DialogProgres(this, "درحال بارگذاری");
        dialogProgres.showProgresBar();
        PresentUpload presentUpload = new PresentUpload(this);
        presentUpload.uploadFile("madrak", Pref.getStringValue(PrefKey.phone, "") + ".pdf", path);

    }

    @Override
    public void messageFromUpload(String message) {
        sendMessageFTT(message);
    }

    @Override
    public void flagFromUpload(ResponseOfServer res) {
        dialogProgres.closeProgresBar();
        if (res.code == 0) {
            showAlertDialog("خطا", "خطا در بارگذاری لطفا بعدا امتحان کنید.", "", "قبول");
        } else if (res.code == 1) {
            dialogProgres.showProgresBar();
            PresentTeacher presentTeacher = new PresentTeacher(this);
            presentTeacher.upMs();
        } else if (res.code == 2) {
            showAlertDialog("خطا", "حجم فایل باید بین یک تا پنج مگابایت باشد", "", "قبول");
        }
    }

    private void showDialogForMadrakState(String title, String message, String buttonTrue, String btnFalse, String btnNeutral) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
            Toast.makeText(ActivityProfile.this, "خطا!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogForHelper(String user, String teacher) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("راهنما");
        builder.setMessage("آیا مایل به مشاهده راهنما هستید؟");
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(user, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                runHelperForUser();
            }
        });
        builder.setNeutralButton(teacher, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                runHelperForTeacher();
            }
        });
        builder.show();
    }

    private void runHelperForUser() {
        SimpleTarget map = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnMapProfile))
                .setRadius(100f)
                .setTitle("نقشه")
                .setDescription("نقشه حاوی موقعیت مکانی آموزشگاهای ثبت شده می باشد")
                .build();
        SimpleTarget sabtenam = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnSabtenamListProfile))
                .setRadius(100f)
                .setTitle("لیست")
                .setDescription("لیست دوره های ثبتنام شده")
                .build();
        SimpleTarget sms = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnMessageBoxProfile))
                .setRadius(100f)
                .setTitle("صندوق پیام")
                .setDescription("پیام های ارسالی و دریافتی برای شما")
                .build();
        SimpleTarget trend = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnTrendingUpProfile))
                .setRadius(100f)
                .setTitle("مدرس")
                .setDescription("ارتقا کاربری به مدرس")
                .build();
        SimpleTarget logout = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnLogOut))
                .setRadius(100f)
                .setTitle("خروج")
                .setDescription("خروج از حساب کاربری")
                .build();


        Spotlight.with(ActivityProfile.this)
                .setOverlayColor(ContextCompat.getColor(ActivityProfile.this, R.color.blue_ios))
                .setDuration(500L)
                .setAnimation(new DecelerateInterpolator(4f))
                .setTargets(map, sabtenam, sms, trend, logout)
                .setClosedOnTouchedOutside(true)
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                    @Override
                    public void onStarted() {
                        Toast.makeText(ActivityProfile.this, "شروع شد", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                    @Override
                    public void onEnded() {
                        Toast.makeText(ActivityProfile.this, "پایان", Toast.LENGTH_SHORT).show();
                        Pref.saveBollValue(PrefKey.profileUserPage, false);
                    }
                })
                .start();
    }

    private void runHelperForTeacher() {

        SimpleTarget starter = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnSabtenamListProfile))
                .setRadius(1f)
                .setTitle("تبریک و تشکر از اینکه به ما در جهت توسعه کسب و کار خود اعتماد کردید")
                .setDescription("در ابتدا باید شما از سمت ما تایید اعتبار شوید سپس می توانید دوره های خود را ثبت کنید پس از ثیت هر دوره محصلین می توانند درخواست ثبتنام دهند و پس از تایید از جانب شما از طریق برنامه و مراجعه حضوری محصل ثبتنام نهایی می شود.")
                .build();

        SimpleTarget sabtenam = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnSabtenamListProfile))
                .setRadius(100f)
                .setTitle("لیست")
                .setDescription("لیست دوره های ثبتنام شده")
                .build();
        SimpleTarget add = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnAddCourseProfile))
                .setRadius(100f)
                .setTitle("دوره")
                .setDescription("اضافه کردن دوره")
                .build();
        SimpleTarget sms = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnMessageBoxProfile))
                .setRadius(100f)
                .setTitle("صندوق پیام")
                .setDescription("پیام های ارسالی و دریافتی برای شما")
                .build();
        SimpleTarget madrak = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.txtUploadMadrak))
                .setRadius(300f)
                .setTitle("مدرک یا مجوز آموزشی")
                .setDescription("جهت بارگزاری مدرک از این قسمت اقدام کنید")
                .build();
        SimpleTarget addList = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnAddListProfile))
                .setRadius(100f)
                .setTitle("لیست دوره")
                .setDescription("لیست دوره های اضافه شده توسط شما")
                .build();
        SimpleTarget logout = new SimpleTarget.Builder(ActivityProfile.this).setPoint(findViewById(R.id.btnLogOut))
                .setRadius(100f)
                .setTitle("خروج")
                .setDescription("خروج از حساب کاربری")
                .build();


        Spotlight.with(ActivityProfile.this)
                .setOverlayColor(ContextCompat.getColor(ActivityProfile.this, R.color.blue_ios))
                .setDuration(500L)
                .setAnimation(new DecelerateInterpolator(4f))
                .setTargets(starter, sabtenam, add, sms, madrak, addList, logout)
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

}


