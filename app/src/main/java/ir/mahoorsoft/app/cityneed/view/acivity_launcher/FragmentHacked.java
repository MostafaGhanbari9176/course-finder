package ir.mahoorsoft.app.cityneed.view.acivity_launcher;


import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentHacked extends Fragment {

    View view;
    DialogProgres dialogProgres;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hacked, container, false);
        dialogProgres = new DialogProgres(G.context);
        (view.findViewById(R.id.btnOut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.activity.finish();
            }
        });
        return view;
    }

}

