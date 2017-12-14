package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentSearch extends Fragment implements View.OnClickListener {

    View view;
    Button btnFilter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        init();
        return view;
    }

    private void init() {

        pointers();

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnFilter :
                showDialog();
                break;
        }

    }

    private void pointers(){

        btnFilter = (Button) view.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this);
    }

    private void showDialog(){

       final Dialog dialog = new Dialog(getContext());
       final LayoutInflater lI = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final View v = lI.inflate(R.layout.dialog_filter,null);
       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        dialog.show();



    }
}

