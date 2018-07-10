package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StComment;
import ir.mahoorsoft.app.cityneed.presenter.PresentReport;
import ir.mahoorsoft.app.cityneed.presenter.PresenterComment;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCommentList;

/**
 * Created by RCC1 on 3/5/2018.
 */

public class FragmentComment extends Fragment implements PresenterComment.OnPresentCommentListener, AdapterCommentList.OnClickItemCommentList, PresentReport.OnPresentReportListener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    boolean licked = false;
    boolean disLicked = false;
    SwipeRefreshLayout sDown;
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
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDComment);
        sDown.setOnRefreshListener(this);
        pointers();
        getCommentList();
        setFont();
    }

    private void getCommentList() {
        sDown.setRefreshing(true);
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
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    getCommentData();
                else
                    messageFromComment("ابتدا وارد حساب کاربری خود شوید");
            }
        });
    }

    private void getCommentData() {

        final Dialog dialog = new Dialog(G.context);
        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_comment, null, false);
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratBDialogComment);
        final TextView textView = (TextView) view.findViewById(R.id.txtDialogComment);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirmDialogComment);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelDialogComment);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCommentData(textView, ratingBar)) {
                    addComment(textView.getText().toString(), ratingBar.getRating());
                    dialog.cancel();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private boolean checkCommentData(TextView txt, RatingBar ratBar) {

        Animation animation = new TranslateAnimation(0, 500, 0, 0);
        animation.setDuration(10000);
        animation.setFillAfter(true);

        Animation animation2 = new TranslateAnimation(0, 0, 500, 0);
        animation.setDuration(10000);
        animation.setFillAfter(true);

        if (ratBar.getRating() == 0) {
            ratBar.startAnimation(animation);
            ratBar.setVisibility(View.GONE);
            ratBar.startAnimation(animation2);
            ratBar.setVisibility(View.VISIBLE);
//            ratBar.clearAnimation();

        } else if (TextUtils.isEmpty(txt.getText().toString().trim()))
            txt.setError("یه نظر ثبت کنید");
        else
            return true;
        return false;
    }

    private void addComment(String commentText, float rat) {
        sDown.setRefreshing(true);
        PresenterComment presenterComment = new PresenterComment(this);
        presenterComment.saveComment(commentText, Pref.getStringValue(PrefKey.apiCode, ""), courseId, teacherId, rat);

    }


    @Override
    public void onResiveComment(ArrayList<StComment> comment) {
        try {
            sDown.setRefreshing(false);
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
        } catch (Exception ignore) {
        }
    }

    @Override
    public void onResiveFlagFromComment(boolean flag) {
        sDown.setRefreshing(false);
        if (flag)
            messageFromComment("ثبت شد");
        else
            messageFromComment("خطا");
    }

    @Override
    public void messageFromComment(String message) {
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
        sDown.setRefreshing(false);
    }

    @Override
    public void likePresed(int position) {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            PresenterComment presenterComment = new PresenterComment(this);
            presenterComment.commentFeedBack(Pref.getStringValue(PrefKey.apiCode, ""), source.get(position).id, 1);
        } else
            messageFromComment("ابتدا وارد حساب کاربری خود شوید");
    }

    @Override
    public void disLikePresed(int position) {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            PresenterComment presenterComment = new PresenterComment(this);
            presenterComment.commentFeedBack(Pref.getStringValue(PrefKey.apiCode, ""), source.get(position).id, 0);
        } else
            messageFromComment("ابتدا وارد حساب کاربری خود شوید");
    }

    @Override
    public void feedBAckPresed(int position) {
        getReportData(position);
    }

    private void getReportData(final int position) {
        final Dialog dialog = new Dialog(G.context);
        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_report, null, false);
        final TextView textView = (TextView) view.findViewById(R.id.txtDialogReport);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirmDialogReport);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelDialogReport);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkReportData(textView)) {
                    sendReport("comment", textView.getText().toString(), source.get(position).id, source.get(position).userId, Pref.getStringValue(PrefKey.apiCode, ""));
                    dialog.cancel();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private boolean checkReportData(TextView txt) {
        if (TextUtils.isEmpty(txt.getText().toString().trim()))
            txt.setError("یه دلیل ثبت کنید");
        else
            return true;
        return false;
    }

    private void sendReport(String signText, String reportText, int spamId, String spamerId, String reporterId) {
        sDown.setRefreshing(true);
        PresentReport presentReport = new PresentReport(this);
        presentReport.report(signText, reportText, spamId, spamerId, reporterId);
    }

    @Override
    public void flagFromReport(boolean flag) {
        sDown.setRefreshing(false);
        if (flag)
            messageFromComment("ارسال شد,بابت فیدبک شما متشکریم");
        else
            messageFromComment("خطا");
    }

    @Override
    public void messageFromReport(String message) {
        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getCommentList();
    }
}
/*
 final AlertDialog.Builder alertDialog = new AlertDialog.Builder(G.context);
        alertDialog.setTitle("ثبت نظر");

        final RatingBar ratingBar = new RatingBar(G.context);
        ratingBar.setNumStars(5);
        LinearLayout.LayoutParams ratingBarParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ratingBar.setLayoutParams(ratingBarParams);

        final EditText editText = new EditText(G.context);
        editText.setHint("نظر خود را وارد کنید");
        editText.setPadding(60, 60, 60, 60);

        final TranslateAnimation ta = new TranslateAnimation(0, 0, Animation.RELATIVE_TO_SELF, ratingBar.getHeight());
        ta.setDuration(1000);
        ta.setFillAfter(true);

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
 */