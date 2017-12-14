package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.fragment_slider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.AdverFeature;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature.ActivityShowFeature;

/**
 * Created by MAHNAZ on 10/9/2017.
 */

public class FragmentSlide extends Fragment {

    private View view;
    private int imageUrl;
    private String title;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slide, container, false);
        imageView = (ImageView) view.findViewById(R.id.imgPager);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImageId();
            }
        });
        imageSetup();
        return view;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void imageSetup(){
        Glide.with(G.context)
                .load(imageUrl)
                .centerCrop()
                .into(imageView);


    }

    private void sendImageId(){

        AdverFeature adverFeature =new AdverFeature();
        adverFeature.image=imageUrl;
        ActivityShowFeature.adverFeatures= adverFeature;
        Intent intent = new Intent(G.context,ActivityShowFeature.class);
        startActivity(intent);

    }


}
