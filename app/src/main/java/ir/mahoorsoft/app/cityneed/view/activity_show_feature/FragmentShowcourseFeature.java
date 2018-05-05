package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.presenter.PresenterComment;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.date.DateCreator;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;


public class FragmentShowcourseFeature extends Fragment implements PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener, PresenterComment.OnPresentCommentListener {
    View view;
    public static boolean issabtenamed = false;
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
    TextView txtNews;
    LinearLayout moreDetails;
    LinearLayout btnMoreDetails;
    LinearLayout llRatBar;
    RatingBar ratBar;
    ProgressBar pbRatBar;
    int capacity;
    Button btnRegister;
    int courseId = ActivityOptionalCourse.courseId;
    String idTeacher;
    String startDate;
    String idUser = Pref.getStringValue(PrefKey.apiCode, "");
    DialogProgres dialogProgres;
    TextView txtConfirmRegistery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_course_feture, container, false);
        issabtenamed = false;
        inite();
        return view;
    }

    private void inite() {
        dialogProgres = new DialogProgres(G.context);
        formatter = new DecimalFormat("#,###,###");
        pointers();
        setFont();
        getCourseInf();
        if (Pref.getBollValue(PrefKey.IsLogin, false))
            checkedSabtenamCourse();

    }

    private void getCourseInf() {
        dialogProgres.showProgresBar();
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
        llRatBar = (LinearLayout) view.findViewById(R.id.llRatBar);
        ratBar = (RatingBar) view.findViewById(R.id.ratBarShowCourseFeature);
        pbRatBar = (ProgressBar) view.findViewById(R.id.pbRatBarCourseFeature);

        img = (ImageView) view.findViewById(R.id.imgFeature);
        imgDrop = (ImageView) view.findViewById(R.id.imgDropShowFeature);
        txtName = (TextView) view.findViewById(R.id.txtNAmeFeature);
        txtMasterName = (TextView) view.findViewById(R.id.txtMasterNameFeature);
        txtDay = (TextView) view.findViewById(R.id.txtDayFeature);
        txtNews = (TextView) view.findViewById(R.id.txtNewsCourseFeature);
        txtConfirmRegistery = (TextView) view.findViewById(R.id.txtConfirmRegistry);
        txtEndDate = (TextView) view.findViewById(R.id.txtEndDateFeature);
        txtHours = (TextView) view.findViewById(R.id.txtHoursFeature);
        txtType = (TextView) view.findViewById(R.id.txtTypeFeature);
        txtTabaghe = (TextView) view.findViewById(R.id.txtTabagheFeature);
        txtsharayet = (TextView) view.findViewById(R.id.txtsharayetFeature);
        txtStartDate = (TextView) view.findViewById(R.id.txtStartDateFeature);
        txtRange = (TextView) view.findViewById(R.id.txtRangeFeature);
        txtMony = (TextView) view.findViewById(R.id.txtMonyFeature);
        txtCapacity = (TextView) view.findViewById(R.id.txtCapacityFeature);
        moreDetails = (LinearLayout) view.findViewById(R.id.MoreDetailsShowFeature);
        btnMoreDetails = (LinearLayout) view.findViewById(R.id.btnMoreDetailsShowFeature);
        btnRegister = (Button) view.findViewById(R.id.btnRegisterShowFeature);
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

    private boolean checkDate() {
        char[] chartodaydate = (DateCreator.todayDate()).toCharArray();
        char[] charStartDate = (startDate).toCharArray();
        String help = "";
        for (int i = 0; i < chartodaydate.length; i++) {

            if (chartodaydate[i] != '-') {
                help = help + chartodaydate[i];
            }
        }
        int todaydate = Integer.parseInt(help);
        help = "";
        for (int i = 0; i < charStartDate.length; i++) {

            if (charStartDate[i] != '-') {
                help = help + charStartDate[i];
            }
        }
        int startDate = Integer.parseInt(help);

        if (startDate - (todaydate - 2) < 0)
            return false;
        return true;
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
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
                Intent intent = new Intent(G.context, ActivityAcountConfirm.class);
                startActivity(intent);
                G.activity.finish();
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setImage() {
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + courseId + ".png")
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .centerCrop()
                .error(R.drawable.books)
                .clone()
                .into(img);

    }


    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        dialogProgres.closeProgresBar();
        capacity = course.get(0).capacity;
        startDate = course.get(0).startDate;
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
        checkCourseData();
        setImage();

    }

    private void checkCourseData() {
        if (!checkDate()) {
            btnRegister.setVisibility(View.GONE);
            ratBar.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
            txtNews.setText("دوره در تاریخ " + startDate + " برگزار شده");
        } else if (capacity == 0) {
            btnRegister.setVisibility(View.GONE);
            ratBar.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
            txtNews.setText("ظرفیت دوره تکمیل شده");
        }
    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void sendMessageFST(String message) {
        sendMessageFCT(message);
    }

    @Override
    public void confirmSabtenam(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag) {
            issabtenamed = true;
            setUpRatBar();
            sendMessageFCT("انجام شد");
            txtNews.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);
        } else
            sendMessageFCT("خطا");
    }

    @Override
    public void confirmDelete(boolean flag) {

    }

    @Override
    public void checkSabtenam(float ratBarValue) {
        if (ratBarValue == -1) {
            txtNews.setVisibility(View.GONE);
            llRatBar.setVisibility(View.GONE);
        } else if (ratBarValue == -3) {
            btnRegister.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
            llRatBar.setVisibility(View.GONE);
            txtNews.setText("ثبت نام شما لغو شده است.");
        } else if (ratBarValue == -2) {
            btnRegister.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
        } else {
            txtNews.setVisibility(View.GONE);
            issabtenamed = true;
            btnRegister.setVisibility(View.GONE);
            llRatBar.setVisibility(View.VISIBLE);
            ratBar.setRating(ratBarValue);
            txtConfirmRegistery.setVisibility(View.VISIBLE);
            setUpRatBar();
        }

    }

    private void setUpRatBar() {
        ratBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                saveRat(rating);

            }
        });
    }

    private void saveRat(float rat) {
        pbRatBar.setVisibility(View.VISIBLE);
        PresenterComment presenterComment = new PresenterComment(this);
        presenterComment.saveCourseRat(idUser, courseId, idTeacher, rat);
    }

    @Override
    public void onResiveComment(ArrayList<StComment> comment) {

    }

    @Override
    public void onResiveFlagFromComment(boolean flag) {

        if (flag)
            messageFromComment("امتیاز شما ثبت شد");
        else {
            messageFromComment("خطا,امتیاز شما ثبت نشد");
        }
    }

    @Override
    public void messageFromComment(String message) {

        pbRatBar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }
}
