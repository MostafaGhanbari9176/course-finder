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
import android.widget.TextView;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
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
    TextView txtPrice;
    TextView txtDescription;
    CardView cv1;
    CardView cv2;
    CardView cv3;
    CardView cv4;
    CardView cv5;
    CardView cv6;

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
        txtPrice = (TextView) view.findViewById(R.id.txtPriceSubscribe);
        txtSubject = (TextView) view.findViewById(R.id.txtSubscribeSubject);
        txtRemainingCourse = (TextView) view.findViewById(R.id.txtRemainingCourses);
        txtBuyDate = (TextView) view.findViewById(R.id.txtBuyDateSubscribe);
        txtEndBuyDate = (TextView) view.findViewById(R.id.txtEndBuyDateSubscribe);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionSubscribe);

        cv1 = (CardView) view.findViewById(R.id.CVSubjectSubscribe);
        cv2 = (CardView) view.findViewById(R.id.CVRemainingCourseSubscribe);
        cv3 = (CardView) view.findViewById(R.id.CVBuyDasteSubscribe);
        cv4 = (CardView) view.findViewById(R.id.CVEndBuyDateSubscribe);
        cv5 = (CardView) view.findViewById(R.id.CVPriceSubscribe);
        cv6 = (CardView) view.findViewById(R.id.CVDescriptionSubscribe);
    }

    private void setData() {
        txtPrice.setText(buyData.price + "");
        txtSubject.setText(buyData.subjectSubscribe);
        txtRemainingCourse.setText(buyData.remainingCourses + "");
        txtEndBuyDate.setText(new String(Base64.decode(Base64.decode(buyData.endBuyDate, Base64.DEFAULT), Base64.DEFAULT)));
        txtDescription.setText(buyData.description);
        txtBuyDate.setText(new String(Base64.decode(Base64.decode(buyData.buyDate, Base64.DEFAULT), Base64.DEFAULT)));
        setColor();
    }

    private void setColor() {
        if(buyData.vaziat == 0){
            cv1.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            cv2.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            cv3.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            cv4.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
            cv5.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_simple_sub));
            cv6.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_simple_sub));
        }
        else if(buyData.subscribeId == 1){
            cv1.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_brown_sub));
            cv2.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            cv3.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_brown_sub));
            cv4.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
            cv5.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_brown_sub));
            cv6.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_brown_sub));
        }
        else if(buyData.subscribeId == 2){
            cv1.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            cv2.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            cv3.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            cv4.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
            cv5.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_silver_sub));
            cv6.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_silver_sub));
        }
        else if(buyData.subscribeId == 3){
            cv1.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_gold_sub));
            cv2.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_gold_sub));
            cv3.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_gold_sub));
            cv4.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_gold_sub));
            cv5.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light_gold_sub));
            cv6.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.dark_gold_sub));
        }
    }
}
