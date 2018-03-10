package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.presenter.PresenterComment;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCommentList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 3/5/2018.
 */

public class FragmentComment extends Fragment implements PresenterComment.OnPresentCommentListener, AdapterCommentList.OnClickItemCommentList {
    View view;
    boolean licked = false;
    boolean disLicked = false;
    DialogProgres dialogProgres;
    RatingBar totalRat;
    FloatingActionButton btnAddComment;
    TextView txtEmptyComment;
    String teacherId = ActivityOptionalCourse.teacherId;
    int courseId = ActivityOptionalCourse.courseId;
    ArrayList<StComment> source = new ArrayList<>();
    AdapterCommentList adapterCommentList;
    RecyclerView commentList;

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
        presenterComment.getCommentByTeacherId(teacherId);

    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Far_Nazanin.ttf");
//        btnAddComment.setTypeface(typeface);
    }

    private void pointers() {
        totalRat = (RatingBar) view.findViewById(R.id.totalRatTeacherComment);
        txtEmptyComment = (TextView) view.findViewById(R.id.txtEmptyComment);
        btnAddComment = (FloatingActionButton) view.findViewById(R.id.btnAddComment);
        totalRat.setEnabled(false);
        commentList = (RecyclerView) view.findViewById(R.id.RVCommentList);
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
        ratingBar.setNumStars(5);
        LinearLayout.LayoutParams ratingBarParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ratingBar.setLayoutParams(ratingBarParams);

        final EditText editText = new EditText(G.context);
        editText.setHint("نظر خود را وارد کنید");
        editText.setPadding(60, 60, 60, 60);

        LinearLayout masterLayout = new LinearLayout(G.context);
        masterLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        masterLayout.setLayoutParams(params);
        masterLayout.setGravity(Gravity.CENTER);

        masterLayout.addView(ratingBar);
        masterLayout.addView(editText);


        alertDialog.setView(masterLayout);

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
        if (comment.get(0).empty == 1) {
            txtEmptyComment.setVisibility(View.VISIBLE);
            return;
        }
        source.clear();
        source.addAll(comment);
        totalRat.setRating(comment.get(0).totalRat);
        adapterCommentList = new AdapterCommentList(G.context, source, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false);
        commentList.setLayoutManager(layoutManager);
        commentList.setAdapter(adapterCommentList);
        adapterCommentList.notifyDataSetChanged();

    }

    @Override
    public void onResiveFlagFromComment(boolean flag) {
        dialogProgres.closeProgresBar();
    }

    @Override
    public void messageFromComment(String message) {
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
        dialogProgres.closeProgresBar();
    }

    @Override
    public void likePresed(int position) {

            PresenterComment presenterComment = new PresenterComment(this);
            presenterComment.commentFeedBack(Pref.getStringValue(PrefKey.apiCode, ""), source.get(position).id, 1);

    }

    @Override
    public void disLikePresed(int position) {
        PresenterComment presenterComment = new PresenterComment(this);
        presenterComment.commentFeedBack(Pref.getStringValue(PrefKey.apiCode, ""), source.get(position).id, 0);
    }

    @Override
    public void feedBAckPresed(int position) {

    }
}
