package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.presenter.PresenterComment;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 3/5/2018.
 */

public class FragmentComment extends Fragment implements PresenterComment.OnPresentCommentListener {
    View view;
    DialogProgres dialogProgres;
    RatingBar totalRat;
    Button btnAddComment;
    String teacherId = ActivityOptionalCourse.teacherId;
    int courseId = ActivityOptionalCourse.courseId;
    ArrayList<StComment> source = new ArrayList<>();

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
        getCommentList();
        setFont();
    }

    private void getCommentList() {
        dialogProgres.showProgresBar();
        PresenterComment presenterComment = new PresenterComment(this);
        presenterComment.getCommentByTeacherId(Pref.getStringValue(PrefKey.apiCode, ""));

    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
        btnAddComment.setTypeface(typeface);
    }

    private void pointers() {
        totalRat = (RatingBar) view.findViewById(R.id.ratBarTeacherFeature);
        btnAddComment = (Button) view.findViewById(R.id.btnAddComment);

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FragmentShowcourseFeature.issabtenamed)
                    getCommentData();
                else
                    messageFromComment("شما در این دوره ثبتنام نکرده اید");
            }
        });
    }

    private void getCommentData() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(G.context);
        alertDialog.setTitle("ثبت نظر");
        final RatingBar ratingBar = new RatingBar(G.context);
        final EditText editText = new EditText(G.context);
        editText.setHint("نظر خود را وارد کنید");
        editText.setPadding(60, 60, 60, 60);
        alertDialog.setView(ratingBar);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("ثبت نظر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addComment(editText.getText().toString(), ratingBar.getRating());
                dialog.cancel();

            }
        });
        alertDialog.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void addComment(String commentText, float rat) {
        dialogProgres.showProgresBar();
        PresenterComment presenterComment = new PresenterComment(this);
        presenterComment.saveComment(commentText, Pref.getStringValue(PrefKey.apiCode, ""), courseId, teacherId, rat);

    }


    @Override
    public void onResiveComment(ArrayList<StComment> comment) {
        dialogProgres.closeProgresBar();
        source.addAll(comment);

    }

    @Override
    public void onResiveFlagFromComment(boolean flag) {
        dialogProgres.closeProgresBar();
    }

    @Override
    public void messageFromComment(String message) {
        dialogProgres.closeProgresBar();
    }
}
