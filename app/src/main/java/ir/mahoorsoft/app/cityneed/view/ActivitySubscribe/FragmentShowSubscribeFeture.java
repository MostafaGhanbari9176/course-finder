package ir.mahoorsoft.app.cityneed.view.ActivitySubscribe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;

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
    TextView txtRemainingCourse_2;
    TextView txtBuyDate_2;
    TextView txtEndBuyDate_2;
    TextView txtDescription_2;
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
        txtRemainingCourse_2 = (TextView) view.findViewById(R.id.txtRemainingCourseFeuture_2);
        txtBuyDate_2 = (TextView) view.findViewById(R.id.txtBuyDateFeuture_2);
        txtEndBuyDate_2 = (TextView) view.findViewById(R.id.txtEndBuyDateFeuture_2);
        txtDescription_2 = (TextView) view.findViewById(R.id.txtDescriptionFeutureSubscribe_2);
    }

    private void setData() {
        txtSubject.setText(buyData.subjectSubscribe);
        txtRemainingCourse.setText(buyData.remainingCourses + "");
        txtEndBuyDate.setText(new String(Base64.decode(Base64.decode(buyData.endBuyDate, Base64.DEFAULT), Base64.DEFAULT)));
        txtDescription.setText(buyData.description);
        txtBuyDate.setText(new String(Base64.decode(Base64.decode(buyData.buyDate, Base64.DEFAULT), Base64.DEFAULT)));
        setColor();
        setImage();
    }

    private void setImage() {
        Glide.with(G.context)
                .load(ApiClient.serverAddress + "/city_need/v1/uploads/subscribe/" + buyData.subscribeId + ".png")
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(R.drawable.university)
                .fitCenter()
                .into(img);
    }

    private void setColor() {
        if (buyData.subscribeId == 3) {
            PCV.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_brown_sub));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtDescription_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtEndBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            txtRemainingCourse_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
        } else if (buyData.subscribeId == 2) {
            PCV.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            txtBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            txtDescription_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            txtEndBuyDate_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            txtRemainingCourse_2.setTextColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));

            txtBuyDate.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtDescription.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtEndBuyDate.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtRemainingCourse.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));
            txtSubject.setTextColor(ContextCompat.getColor(G.context, R.color.light_eq));

        }
    }
}
