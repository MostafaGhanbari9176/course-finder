package ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.AdverFeature;
import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by MAHNAZ on 10/15/2017.
 */

public class ActivityShowFeature extends AppCompatActivity {

    ImageView img;
    TextView txtName;
    TextView txtKind;

    public static AdverFeature adverFeatures ;
    int imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feature);
        init();
    }

    private void init() {

        pointers();
        setData();

    }

    private void pointers() {

        img = (ImageView) this.findViewById(R.id.img_showFeature);
        txtKind = (TextView) this.findViewById(R.id.txtKind_ShowFeature);
        txtName = (TextView) this.findViewById(R.id.txtName_ShowFeature);

    }

    private void setData() {

        setImage();
        setOther();
    }

    private void setImage() {

        imageUrl = adverFeatures.image;

        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .clone()
                .into(img);

    }

    private void setOther() {

        txtKind.setText(adverFeatures.kind);
        txtName.setText(adverFeatures.collegeName);

    }

}
