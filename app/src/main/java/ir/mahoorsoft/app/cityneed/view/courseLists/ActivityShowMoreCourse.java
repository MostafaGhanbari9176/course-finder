package ir.mahoorsoft.app.cityneed.view.courseLists;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterHomeLists;

/**
 * Created by RCC1 on 4/29/2018.
 */

public class ActivityShowMoreCourse extends AppCompatActivity implements PresentCourse.OnPresentCourseLitener, AdapterHomeLists.setOnClickItem{
    int groupId = -1;
    String groupingRootName = "";
    ProgressBar pbar;
    LinearLayout llScrollView;
    Toolbar toolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_course);
        if(getIntent().getExtras() != null) {
            groupId = getIntent().getIntExtra("groupId", -1);
            groupingRootName = getIntent().getStringExtra("groupName");
        }
        pointer();
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(groupingRootName);
        getData();
    }

    private void getData(){
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseForListHome(groupId);
    }

    private void pointer(){
        toolBar = (Toolbar) findViewById(R.id.tlbShowMoreCourse);
        pbar = (ProgressBar) findViewById(R.id.pbarShowMoreCourse);
        llScrollView = (LinearLayout) findViewById(R.id.llScrollViewShowMoreCource);
    }

    @Override
    public void sendMessageFCT(String message) {
        pbar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }

    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {

    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {
        pbar.setVisibility(View.GONE);
        settingUpLists(items);
    }

    private void settingUpLists(ArrayList<StCustomCourseListHome> items){
        if (((LinearLayout) llScrollView).getChildCount() > 0)
            ((LinearLayout) llScrollView).removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            LinearLayout masterLayout = new LinearLayout(G.context);
            masterLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            masterLayout.setLayoutParams(params);
            masterLayout.setGravity(Gravity.CENTER);

            TextView textView = new TextView(G.context);
            textView.setTextColor(ContextCompat.getColor(G.context, R.color.light));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setText("دوره های " + items.get(i).groupSubject);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            int dp = G.dpToPx(16);
            textParams.setMargins(dp, dp, dp, dp);
            masterLayout.addView(textView, textParams);

            CardView cardView = new CardView(G.context);
            LinearLayout.LayoutParams cardParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardView.setLayoutParams(cardParam);
            cardParam.setMargins(0, G.dpToPx(4), 0, 0);
            masterLayout.addView(cardView, cardParam);

            if (items.get(i).empty == 0) {
                RecyclerView list = new RecyclerView(G.context);
                AdapterHomeLists adapter = new AdapterHomeLists(G.context, items.get(i).courses, this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                        , LinearLayoutManager.HORIZONTAL, false);
                list.setLayoutManager(layoutManager);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                cardView.addView(list);
            } else {
                TextView textEmpty = new TextView(G.context);
                textEmpty.setTextColor(ContextCompat.getColor(G.context, R.color.pink_tel));
                textEmpty.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textEmpty.setText("موجود نیست");

                LinearLayout.LayoutParams textEmptyParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textEmptyParams.setMargins(dp, dp, dp, dp);
                textEmpty.setLayoutParams(textEmptyParams);
                textEmpty.setGravity(Gravity.CENTER);
                cardView.addView(textEmpty);
            }


            CardView cardViewMaster = new CardView(G.context);
            cardViewMaster.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.blue_tel));
            LinearLayout.LayoutParams cardViewMasterParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardViewMaster.setLayoutParams(cardViewMasterParam);
            dp =  G.dpToPx(8);
            cardViewMasterParam.setMargins(dp, dp, dp, dp);
            cardViewMaster.addView(masterLayout);
            llScrollView.addView(cardViewMaster, cardViewMasterParam);
        }
    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void itemClick(int id, String teacherId) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", id);
        intent.putExtra("teacherId", teacherId);
        startActivity(intent);
    }

    @Override
    public void moreCourse(int groupingId, String groupName) {
        Intent intent = new Intent(G.context, ActivityCoursesListByGroupingId.class);
        intent.putExtra("groupingId", groupingId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
