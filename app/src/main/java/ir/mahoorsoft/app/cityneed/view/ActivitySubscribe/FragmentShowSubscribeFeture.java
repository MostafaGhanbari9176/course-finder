package ir.mahoorsoft.app.cityneed.view.ActivitySubscribe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by RCC1 on 5/8/2018.
 */

public class FragmentShowSubscribeFeture extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_subscribe_feture, container, false);

        return view;
    }
}
