package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import ir.mahoorsoft.app.cityneed.presenter.PresentBookMark;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.presenter.PresenterComment;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.date.DateCreator;


public class FragmentShowcourseFeature extends Fragment implements PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener, PresenterComment.OnPresentCommentListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, PresentBookMark.OnPresentBookMarkListener {
    View view;
    public static boolean issabtenamed = false;
    DecimalFormat formatter;
    ImageView img;
    ImageView imgDrop;
    ImageView imgBookMark;
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
    TextView txtDescription;
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
    SwipeRefreshLayout sDown;
    TextView txtConfirmRegistery;
    int bookMark;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_course_feture, container, false);
        issabtenamed = false;
        inite();
        return view;
    }

    private void inite() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDShowCourseFuture);
        sDown.setOnRefreshListener(this);
        formatter = new DecimalFormat("#,###,###");
        pointers();
        getCourseInf();


    }

    private void getCourseInf() {
        sDown.setRefreshing(true);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseById(courseId);
    }

    private void checkedSabtenamCourse() {
        sDown.setRefreshing(false);
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.checkSabtenam(courseId, idUser);
    }

    private void pointers() {
        llRatBar = (LinearLayout) view.findViewById(R.id.llRatBar);
        ratBar = (RatingBar) view.findViewById(R.id.ratBarShowCourseFeature);
        pbRatBar = (ProgressBar) view.findViewById(R.id.pbRatBarCourseFeature);

        img = (ImageView) view.findViewById(R.id.imgFeature);
        imgDrop = (ImageView) view.findViewById(R.id.imgDropShowFeature);
        imgBookMark = (ImageView) view.findViewById(R.id.imgBookMark);
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
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionCourseFuture);
        txtMony = (TextView) view.findViewById(R.id.txtMonyFeature);
        txtCapacity = (TextView) view.findViewById(R.id.txtCapacityFeature);
        moreDetails = (LinearLayout) view.findViewById(R.id.MoreDetailsShowFeature);
        btnMoreDetails = (LinearLayout) view.findViewById(R.id.btnMoreDetailsShowFeature);
        btnRegister = (Button) view.findViewById(R.id.btnRegisterShowFeature);
        moreDetails.setVisibility(View.GONE);
        ((ImageView) view.findViewById(R.id.imgButtonShareCourseFuture)).setOnClickListener(this);
        imgBookMark.setOnClickListener(this);

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
            getPhoneNumber();
        } else
            showDialog();
    }

    private void queryForRegistery() {
        sDown.setRefreshing(true);
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.add(courseId, idTeacher, idUser);
    }

    private void getPhoneNumber() {
        final Dialog dialog = new Dialog(G.context);
        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_get_phone_number, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.txtGetPhoneNumberFDialog);
        editText.setText(Pref.getStringValue(PrefKey.cellPhone, ""));
        ((Button) view.findViewById(R.id.btnCancelGetPhoneNumberDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        ((Button) view.findViewById(R.id.btnConfirmGetPhoneNumberDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() == 11 && TextUtils.isDigitsOnly(editText.getText().toString().trim())) {
                    Pref.saveStringValue(PrefKey.cellPhone, editText.getText().toString().trim());
                    queryForRegistery();
                    dialog.cancel();
                } else
                    editText.setError("لطفا صحیح وارد کنید");
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
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
        builder.setTitle("حساب کاربری");
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


    private void showDialogForWaitingRegistery() {
        AlertDialog.Builder builder = new AlertDialog.Builder(G.context);
        builder.setMessage("ثبت نام موقت شما انجام شد,می توانید در قسمت حساب کاربری نتیجه آن را مشاهده کنید.");
        builder.setPositiveButton("خوب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        sDown.setRefreshing(false);
        capacity = course.get(0).capacity;
        startDate = course.get(0).startDate;
        idTeacher = course.get(0).idTeacher;
        txtMony.setText(formatter.format(course.get(0).mony) + " تومان");
        txtCapacity.setText(course.get(0).capacity + " نفر");
        txtRange.setText("از " + course.get(0).minOld + " تا " + course.get(0).maxOld + " سال");
        txtStartDate.setText(course.get(0).startDate);
        txtEndDate.setText(course.get(0).endDate);
        txtType.setText(course.get(0).type == 0 ? "آموزش عمومی" : "آموزش خصوصی");
        txtName.setText(course.get(0).CourseName);
        txtMasterName.setText(course.get(0).MasterName);
        txtHours.setText(course.get(0).hours);
        txtDay.setText(course.get(0).day);
        txtDescription.setText(course.get(0).tozihat);
        txtTabaghe.setText(course.get(0).tabaghe);
        txtsharayet.setText(course.get(0).sharayet);
        checkCourseData();
        bookMark = course.get(0).bookMark;
        setBookMarkImage();
        setImage();
        if (Pref.getBollValue(PrefKey.IsLogin, false))
            checkedSabtenamCourse();
    }

    private void checkCourseData() {
        if (!checkDate()) {
            btnRegister.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
            txtNews.setText("دوره در تاریخ " + startDate + " برگزار شده");
        } else if (capacity == 0) {
            btnRegister.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
            txtNews.setText("ظرفیت دوره تکمیل شده");
        }
    }

    private void setBookMarkImage() {
        if (bookMark == 1)
            imgBookMark.setImageResource(R.drawable.icon_book_marck_blue);
        else
            imgBookMark.setImageResource(R.drawable.icon_book_marck_silver);
    }

    @Override
    public void checkSabtenam(float ratBarValue) {
        if (ratBarValue == -3) {
            btnRegister.setVisibility(View.GONE);
            txtNews.setVisibility(View.GONE);
            txtConfirmRegistery.setVisibility(View.VISIBLE);
            txtConfirmRegistery.setText("ثبت نام شما لغو شده است.");
        } else if (ratBarValue == -2) {
            btnRegister.setVisibility(View.GONE);
            txtNews.setVisibility(View.VISIBLE);
            txtNews.setText("ثبت نام شما در انتظار تایید مدرس است");
        } else if (ratBarValue != -1) {
            issabtenamed = true;
            btnRegister.setVisibility(View.GONE);
            llRatBar.setVisibility(View.VISIBLE);
            ratBar.setRating(ratBarValue);
            txtConfirmRegistery.setVisibility(View.VISIBLE);
            txtNews.setVisibility(View.GONE);
            setUpRatBar();
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
        sDown.setRefreshing(false);
        if (flag) {
            issabtenamed = true;
            setUpRatBar();
            sendMessageFCT("انجام شد");
            txtNews.setVisibility(View.VISIBLE);
            txtNews.setText("ثبت نام شما در انتظار تایید مدرس است");
            showDialogForWaitingRegistery();
            btnRegister.setVisibility(View.GONE);
        } else
            sendMessageFCT("خطا");
    }

    @Override
    public void confirmDelete(boolean flag) {

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
        pbRatBar.setVisibility(View.GONE);
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

    @Override
    public void onRefresh() {
        getCourseInf();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBookMark:
                if (Pref.getBollValue(PrefKey.IsLogin, false)) {
                    if (bookMark == 0)
                        queryForSaveBookMark();
                    else
                        queryForRemoveBookMark();
                } else
                    messageFromBookMark("ابتدا وارد حساب کاربری خود شوید");
                break;
            case R.id.imgButtonShareCourseFuture:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "دوره " + txtName.getText());
                    String sAux = "سلام می خوام بهت یک دوره آموزشی عالی معرفی کنم برای شرکت در این دوره کافیه برنامه دوره یاب رو از لینک زیر دانلود کنی و در دوره"+"*"+ txtName.getText() +" *شرکت کنی";
                    sAux = sAux + G.appLink + "\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception ignored) {
                }
                break;
        }
    }

    private void queryForSaveBookMark() {
        messageFromBookMark("درحال نشانه گذاری ...");
        (new PresentBookMark(this)).saveBookMark(courseId);
    }

    private void queryForRemoveBookMark() {
        messageFromBookMark("درحال حذف نشانه ...");
        (new PresentBookMark(this)).removeBookMark(courseId);
    }

    @Override
    public void messageFromBookMark(String message) {
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void flagFromBookMark(boolean flag) {
        if (flag && bookMark == 0) {
            messageFromBookMark("نشانه گذاری شد.");
            bookMark = 1;
            setBookMarkImage();
        } else if (flag && bookMark == 1) {
            messageFromBookMark("نشانه حذف شد.");
            bookMark = 0;
            setBookMarkImage();
        } else
            messageFromBookMark("خطا ,لطفا دوباره سیع کنید ...");
    }
}
