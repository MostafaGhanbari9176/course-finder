package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_teacher_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 21-Jun-18.
 */

public class FragmentFavoriteTeacher extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_teacher_list, container, false);
        return view;
    }
}
