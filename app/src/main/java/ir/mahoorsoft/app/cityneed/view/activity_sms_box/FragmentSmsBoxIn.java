package ir.mahoorsoft.app.cityneed.view.activity_sms_box;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class FragmentSmsBoxIn extends Fragment {

    View view;
    DialogProgres dialogProgres ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_sms_box,container,false);
        init();
        return view;
    }

    private void init(){
        dialogProgres = new DialogProgres(G.context);
        getData();
    }

    private void getData(){}
}
