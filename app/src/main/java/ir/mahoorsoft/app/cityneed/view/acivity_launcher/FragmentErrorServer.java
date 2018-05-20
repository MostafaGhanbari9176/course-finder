package ir.mahoorsoft.app.cityneed.view.acivity_launcher;


import android.content.Intent;
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

public class FragmentErrorServer extends Fragment implements PresentCheckedStatuse.OnPresentCheckServrer {

    View view;
    DialogProgres dialogProgres;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_error_server, container, false);
        dialogProgres = new DialogProgres(G.context);
        ((view.findViewById(R.id.btnReCheck))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedServer();
            }
        });
        return view;
    }

    private void checkedServer() {
        dialogProgres.showProgresBar();
        PresentCheckedStatuse presentCheckedStatuse = new PresentCheckedStatuse(FragmentErrorServer.this);
        presentCheckedStatuse.checkedServerStatuse();
    }

    @Override
    public void serverChecked(boolean online) {
        dialogProgres.closeProgresBar();
        if (online){
            Intent intent = new Intent(G.context, ActivityMain.class);
            startActivity(intent);
            G.activity.finish();
        }

    }

    @Override
    public void userChecked(boolean logIn) {
        if(!logIn){
            dialogProgres.closeProgresBar();
            Pref.removeValue(PrefKey.phone);
            Pref.removeValue(PrefKey.apiCode);
            Pref.removeValue(PrefKey.userName);
            Pref.removeValue(PrefKey.location);
            Pref.removeValue(PrefKey.cityId);
            Pref.removeValue(PrefKey.subject);
            Pref.removeValue(PrefKey.address);
            Pref.removeValue(PrefKey.userTypeMode);
            Pref.removeValue(PrefKey.landPhone);
            Pref.removeValue(PrefKey.madrak);
            Pref.removeValue(PrefKey.lat);
            Pref.removeValue(PrefKey.lon);
            Pref.removeValue(PrefKey.IsLogin);
        }

    }
}
