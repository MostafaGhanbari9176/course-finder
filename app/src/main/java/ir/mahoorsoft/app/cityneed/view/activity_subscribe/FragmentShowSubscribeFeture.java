package ir.mahoorsoft.app.cityneed.view.activity_subscribe;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.view.activity_subscribe.fragment_chose_subscribe.FragmentChoseSubscrib;
import ir.mahoorsoft.app.cityneed.view.date.DateCreator;

/**
 * Created by RCC1 on 5/8/2018.
 */

public class FragmentShowSubscribeFeture extends Fragment {

    View view;
    public StBuy buyData;
    TextView txtSubject;
    TextView txtRemainingCourse;
    TextView txtBuyDate;
    TextView txtEndBuyDate;
    TextView txtDescription;
    TextView txtRefId;
    TextView txtRefId_2;
    TextView txtRemainingCourse_2;
    TextView txtBuyDate_2;
    TextView txtEndBuyDate_2;
    TextView txtDescription_2;
    Button btnBuyNew;
    CardView PCV;
    ImageView img;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_subscribe_feture, container, false);
        init();
        return view;
    }

    private void init() {
        pointers();
        setData();
    }

    private void pointers() {
        img = (ImageView) view.findViewById(R.id.imgSubscribeFeuture);
        PCV = (CardView) view.findViewById(R.id.PCVSubscribeFeuture);
        txtSubject = (TextView) view.findViewById(R.id.txtSubscribeFutureSubject);
        txtRemainingCourse = (TextView) view.findViewById(R.id.txtRemainingCourseFeuture);
        txtBuyDate = (TextView) view.findViewById(R.id.txtBuyDateFeuture);
        txtEndBuyDate = (TextView) view.findViewById(R.id.txtEndBuyDateFeuture);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionFeutureSubscribe);
        txtRefId = (TextView) view.findViewById(R.id.txtRefIdFutureSub);
        txtRefId_2 = (TextView) view.findViewById(R.id.txtRefIdFutureSub_2);
        txtRemainingCourse_2 = (TextView) view.findViewById(R.id.txtRemainingCourseFeuture_2);
        txtBuyDate_2 = (TextView) view.findViewById(R.id.txtBuyDateFeuture_2);
        txtEndBuyDate_2 = (TextView) view.findViewById(R.id.txtEndBuyDateFeuture_2);
        txtDescription_2 = (TextView) view.findViewById(R.id.txtDescriptionFeutureSubscribe_2);
        btnBuyNew = (Button) view.findViewById(R.id.btnBuyNewSubscribe);
        btnBuyNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySubscribe.replaceContentWith(new FragmentChoseSubscrib());
            }
        });
    }

    private void setData() {

        String endBuyDate = new String(Base64.decode(Base64.decode(buyData.endBuyDate, Base64.DEFAULT), Base64.DEFAULT));
        String todayDate = DateCreator.todayDate();
        if (endBuyDate.compareTo(todayDate) < 0 || buyData.remainingCourses == 0) {///oooooook
            btnBuyNew.setVisibility(View.VISIBLE);
            txtSubject.setText("???????????? ?????? ???? ?????????? ??????????");
        } else
            txtSubject.setText(buyData.subjectSubscribe);
        txtRemainingCourse.setText(buyData.remainingCourses + "");
        txtEndBuyDate.setText(endBuyDate);
        txtDescription.setText(buyData.description);
        txtRefId.setText(buyData.refId);
        txtBuyDate.setText(new String(Base64.decode(Base64.decode(buyData.buyDate, Base64.DEFAULT), Base64.DEFAULT)));
        setColor();
        setImage();
    }

    private void setImage() {
        Glide.with(G.context)
                .load(ApiClient.serverAddress + "/city-need/v1/uploads/subscribe/" + buyData.subscribeId + ".png")
                .error(R.drawable.subscribe_pressure)
                .fitCenter()
                .into(img);
    }

    private void setColor() {
        if (buyData.subjectSubscribe.equals("????????????")) {
            PCV.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_brown_sub));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtRefId_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtDescription_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtEndBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtRemainingCourse_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
        } else if (buyData.subjectSubscribe.equals("???????? ??????")) {
            PCV.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            txtBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            txtRefId_2.setTextColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            txtDescription_2.setTextColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            txtEndBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            txtRemainingCourse_2.setTextColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));

            txtBuyDate.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtRefId.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtDescription.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtEndBuyDate.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtRemainingCourse.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));

        } else if (!buyData.subjectSubscribe.equals("??????????")) {
            PCV.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            txtBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            txtRefId_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            txtDescription_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            txtEndBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            txtRemainingCourse_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));

        }
    }
}
