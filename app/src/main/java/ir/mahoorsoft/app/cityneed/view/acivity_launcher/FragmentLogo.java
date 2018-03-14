package ir.mahoorsoft.app.cityneed.view.acivity_launcher;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Random;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentCheckedStatuse;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class FragmentLogo extends Fragment {

    View view;
    DialogProgres dialogProgres;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_logo, container, false);
        dialogProgres = new DialogProgres(G.context);
     //((LinearLayout)view.findViewById(R.id.llLogo)).setBackgroundColor(randomColor());
    // ((ProgressBar)view.findViewById(R.id.pbLogo)).setBackgroundColor(randomColor());
     //((FloatingActionButton)view.findViewById(R.id.fltbLogo)).setImageResource(randomColor());
        return view;
    }

    private int randomColor() {
        int color = (new Random()).nextInt(8);
        switch (color) {
            case 0:
                return ContextCompat.getColor(G.context, R.color.blue_ios);
            case 1:
                return ContextCompat.getColor(G.context, R.color.green_ios);
            case 2:
                return ContextCompat.getColor(G.context, R.color.orange_ios);
            case 3:
                return ContextCompat.getColor(G.context, R.color.pink_ios);
            case 4:
                return ContextCompat.getColor(G.context, R.color.purple_ios);
            case 5:
                return ContextCompat.getColor(G.context, R.color.tealblue_ios);
            case 6:
                return ContextCompat.getColor(G.context, R.color.red_ios);
            case 7:
                return ContextCompat.getColor(G.context, R.color.yellow_ios);
            default:
                return ContextCompat.getColor(G.context, R.color.dark_eq);
        }

    }

}
