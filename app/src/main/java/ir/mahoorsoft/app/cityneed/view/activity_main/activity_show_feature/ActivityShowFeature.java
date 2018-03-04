package ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/15/2017.
 */

public class ActivityShowFeature extends AppCompatActivity implements PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener {
    DecimalFormat formatter;
    ImageView img;
    ImageView imgDrop;
    TextView txtName;
    TextView txtMasterName;
    TextView txtTabaghe;
    TextView txtStartDate;
    TextView txtEndDate;
    TextView txtDay;
    TextView txtHours;
    TextView txtType;
    TextView txtMony;
    TextView txtCapacity;
    TextView txtsharayet;
    TextView txtRange;
    LinearLayout moreDetails;
    LinearLayout btnMoreDetails;
    Button btnRegister;
    int courseId;
    String idTeacher;
    String idUser = Pref.getStringValue(PrefKey.apiCode, "");
    DialogProgres dialogProgres;
    RatingBar ratBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feature);
        init();
    }

    private void init() {
        dialogProgres = new DialogProgres(this);
        dialogProgres.showProgresBar();
        formatter = new DecimalFormat("#,###,###");
        pointers();
        setFont();
        if (getIntent().getExtras() != null)
            courseId = getIntent().getIntExtra("id", -1);
        checkedSabtenamCourse();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseById(courseId);
    }

    private void checkedSabtenamCourse() {
        dialogProgres.showProgresBar();
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.checkSabtenam(courseId, idUser);
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        txtName.setTypeface(typeface);
        txtsharayet.setTypeface(typeface);
        txtTabaghe.setTypeface(typeface);
        txtDay.setTypeface(typeface);
        txtHours.setTypeface(typeface);
        txtType.setTypeface(typeface);
        txtCapacity.setTypeface(typeface);
        txtEndDate.setTypeface(typeface);
        txtStartDate.setTypeface(typeface);
        txtRange.setTypeface(typeface);
        txtMasterName.setTypeface(typeface);
        txtMony.setTypeface(typeface);

    }

    private void pointers() {

        img = (ImageView) this.findViewById(R.id.imgFeature);
        imgDrop = (ImageView) this.findViewById(R.id.imgDropShowFeature);
        txtName = (TextView) this.findViewById(R.id.txtNAmeFeature);
        txtMasterName = (TextView) this.findViewById(R.id.txtMasterNameFeature);
        txtDay = (TextView) this.findViewById(R.id.txtDayFeature);
        txtEndDate = (TextView) this.findViewById(R.id.txtEndDateFeature);
        txtHours = (TextView) this.findViewById(R.id.txtHoursFeature);
        txtType = (TextView) this.findViewById(R.id.txtTypeFeature);
        txtTabaghe = (TextView) this.findViewById(R.id.txtTabagheFeature);
        txtsharayet = (TextView) this.findViewById(R.id.txtsharayetFeature);
        txtStartDate = (TextView) this.findViewById(R.id.txtStartDateFeature);
        txtRange = (TextView) this.findViewById(R.id.txtRangeFeature);
        txtMony = (TextView) this.findViewById(R.id.txtMonyFeature);
        ratBar = (RatingBar) findViewById(R.id.ratBarShowFeature);
        txtCapacity = (TextView) this.findViewById(R.id.txtCapacityFeature);
        moreDetails = (LinearLayout) this.findViewById(R.id.MoreDetailsShowFeature);
        btnMoreDetails = (LinearLayout) this.findViewById(R.id.btnMoreDetailsShowFeature);
        btnRegister = (Button) this.findViewById(R.id.btnRegisterShowFeature);
        moreDetails.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerHe();
            }
        });
        btnMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreDetails.getVisibility() == View.GONE) {
                    moreDetails.setVisibility(View.VISIBLE);
                    imgDrop.setImageResource(R.drawable.icon_drop_up);
                } else {
                    moreDetails.setVisibility(View.GONE);
                    imgDrop.setImageResource(R.drawable.icon_drop_down);
                }
            }
        });

    }

    private void registerHe() {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            dialogProgres.showProgresBar();
            PresentSabtenam presentSabtenam = new PresentSabtenam(this);
            presentSabtenam.add(courseId, idTeacher, idUser);
        } else
            showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ثبت نام");
        builder.setMessage("ابتدا باید وارد حساب خود شوید یا یک حساب جدید ایجاد کنید.");
        builder.setPositiveButton("بعدا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("ورود یا ایجاد حساب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ActivityShowFeature.this, ActivityAcountConfirm.class);
                startActivity(intent);
                ActivityShowFeature.this.finish();
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setImage() {
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + courseId + ".png")
                .centerCrop()
                .error(R.drawable.user)
                .clone()
                .into(img);

    }


    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        dialogProgres.closeProgresBar();
        idTeacher = course.get(0).idTeacher;
        txtMony.setText(formatter.format(course.get(0).mony) + " تومان");
        txtCapacity.setText(course.get(0).capacity + " نفر");
        txtRange.setText("از " + course.get(0).minOld + " تا " + course.get(0).maxOld + " سال");
        txtStartDate.setText(course.get(0).startDate);
        txtEndDate.setText(course.get(0).endDate);
        txtType.setText(course.get(0).type == 0 ? "عمومی" : "خصوصی");
        txtName.setText(course.get(0).CourseName);
        txtMasterName.setText(course.get(0).MasterName);
        txtHours.setText(course.get(0).hours);
        txtDay.setText(course.get(0).day);
        txtTabaghe.setText(course.get(0).tabaghe);
        txtsharayet.setText(course.get(0).sharayet);
        setImage();

    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StHomeListItems> items) {

    }

    @Override
    public void sendMessageFST(String message) {
        sendMessageFCT(message);
    }

    @Override
    public void confirmSabtenam(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag) {
//            registeredBefore();
            sendMessageFCT("انجام شد");
        } else
            sendMessageFCT("خطا");
    }

    @Override
    public void checkSabtenam(int ratBarValue) {


    }

//    @Override
//    public void registeredBefore() {
//        sendMessageFCT("قبلا ثبت نام شده");
//        btnRegister.setVisibility(View.GONE);
//        ratBar.setVisibility(View.VISIBLE);
//        Pref.saveStringValue(PrefKey.sabTenamCorseId, G.myTrim(courseId + "-" + (Pref.getStringValue(PrefKey.sabTenamCorseId, "")), '-'));
//    }
}
