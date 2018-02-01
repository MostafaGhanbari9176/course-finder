package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;

import ir.mahoorsoft.app.cityneed.presenter.PresentCheckedServer;

import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;

import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class FragmentErrorServer extends Fragment implements PresentCheckedServer.OnPresentCheckServrer {

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
        PresentCheckedServer presentCheckedServer = new PresentCheckedServer(FragmentErrorServer.this);
        presentCheckedServer.checkedServer();
    }

    @Override
    public void serverChecked(boolean online) {
        dialogProgres.closeProgresBar();
        if (online)
            ActivityMain.replaceContentWith(new FragmentHome());
    }
}
