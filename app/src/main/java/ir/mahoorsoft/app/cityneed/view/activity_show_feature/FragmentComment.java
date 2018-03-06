package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.graphics.Typeface;
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
 * Created by RCC1 on 3/5/2018.
 */

public class FragmentComment extends Fragment {
    View view;
    DialogProgres dialogProgres;
    String teacherId = ActivityOptionalCourse.teacherId;
    int courseId = ActivityOptionalCourse.courseId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment, container, false);
        inite();
        return view;
    }

    private void inite() {
        dialogProgres = new DialogProgres(G.context);
        pointers();
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");

    }

    private void pointers() {

    }



}
