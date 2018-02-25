package ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by MAHNAZ on 10/15/2017.
 */

public class ActivityShowFeature extends AppCompatActivity implements PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener {

    ImageView img;
    ImageView backImage;
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
    CardView moreDetails;
    LinearLayout btnMoreDetails;
    Button btnRegister;
    int courseId;
    String idTeacher;
    String idUser = Pref.getStringValue(PrefKey.phone,"");
    DialogProgres dialogProgres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feature);
        init();
    }

    private void init() {
        dialogProgres = new DialogProgres(this);
        dialogProgres.showProgresBar();
        pointers();
        if (getIntent().getExtras() != null)
            courseId = getIntent().getIntExtra("id", -1);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseById(courseId);


    }

    private void pointers() {

        img = (ImageView) this.findViewById(R.id.imgFeature);
        backImage = (ImageView) this.findViewById(R.id.imgBackFeatutre);
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
        txtCapacity = (TextView) this.findViewById(R.id.txtCapacityFeature);
        moreDetails = (CardView) this.findViewById(R.id.MoreDetailsShowFeature);
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
                if (moreDetails.getVisibility() == View.GONE)
                    moreDetails.setVisibility(View.VISIBLE);
                else
                    moreDetails.setVisibility(View.GONE);
            }
        });

    }

    private void registerHe() {
        dialogProgres.showProgresBar();
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.add(courseId, idTeacher, idUser);
    }

    private void setImage() {
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + courseId + ".png")
                .centerCrop()
                .clone()
                .into(img);
        Glide.with(this)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/course/" + courseId + ".png")
                .centerCrop()
                .clone()
                .into(backImage);
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
        txtMony.setText(course.get(0).mony + "");
        txtCapacity.setText(course.get(0).capacity + "");
        txtRange.setText("از("+course.get(0).minOld+")تا("+course.get(0).maxOld+")سال");
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
        if(flag){
            sendMessageFCT("انجام شد");
        }
        else
            sendMessageFCT("خطا");
    }
}
