package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.view.GlideLoader;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterHomeLists;
import ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature.ActivityShowFeature;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTabagheList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentHome extends Fragment implements AdapterHomeLists.setOnClickItem, ViewPager.OnPageChangeListener, OnPageClickListener, PresentCourse.OnPresentCourseLitener, PresentGrouping.OnPresentTabagheListener, AdapterTabagheList.OnClickItemTabagheList {
    LinearLayout scrollView;
    LinearLayout llitems;
    CardView btnDelete;
    private ArrayList<Page> pageViews;
    AdapterTabagheList adapterGrouping;
    private InfiniteIndicator mAnimCircleIndicator;
    View view;
    RecyclerView groupingList;
    DialogProgres dialogProgres;
    LinearLayout txtEmpty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ActivityMain.toolbar.setVisibility(View.VISIBLE);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        pointer();
        settingUpVPager();
        queeyForGroupingListData();
        dialogProgres.showProgresBar();
        queryForCourses(-1);


    }

    private void queeyForGroupingListData() {
        PresentGrouping presentGrouping = new PresentGrouping(this);
        presentGrouping.getTabaghe(-1);
    }

    private void settingUpVPager() {

        initData();
        mAnimCircleIndicator = (InfiniteIndicator) view.findViewById(R.id.viewPager);
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new GlideLoader())
                .isStopWhileTouch(true)
                .onPageChangeListener(this)
                .onPageClickListener(this)
                .direction(1)
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .internal(5000)
                .scrollDurationFactor(5)
                .build();
        mAnimCircleIndicator.init(configuration);
        mAnimCircleIndicator.notifyDataChange(pageViews);

    }

    public void queryForCourses(int groupId) {
        if (groupId == -1)
            btnDelete.setVisibility(View.GONE);
        else
            btnDelete.setVisibility(View.VISIBLE);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByGroupingId(groupId);
    }

    private void pointer() {
        btnDelete = (CardView) view.findViewById(R.id.btnDeletGroupingHome);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryForCourses(-1);
            }
        });
        btnDelete.setVisibility(View.GONE);
        txtEmpty = (LinearLayout) view.findViewById(R.id.llTxtEmptyListHome);
        txtEmpty.setVisibility(View.GONE);
        groupingList = (RecyclerView) view.findViewById(R.id.RVGroupingItemsHome);
        scrollView = (LinearLayout) view.findViewById(R.id.llSV);
        llitems = (LinearLayout) view.findViewById(R.id.llItemsHome);

    }

    @Override
    public void itemClick(int id) {
        Intent intent = new Intent(G.context, ActivityShowFeature.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {
        Intent intent = new Intent(G.context, ActivityShowFeature.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initData() {
        pageViews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pageViews.add(new Page(i + "", ApiClient.serverAddress + "/city_need/v1/uploads/course/" + i + ".png", this));
        }
    }

    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }

    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {

    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StHomeListItems> items) {
        dialogProgres.closeProgresBar();
        creatLists(items);
    }

    private void creatLists(ArrayList<StHomeListItems> items) {
        if (items.size() == 0)
            txtEmpty.setVisibility(View.VISIBLE);
        else
            txtEmpty.setVisibility(View.GONE);
        if (((LinearLayout) llitems).getChildCount() > 0)
            ((LinearLayout) llitems).removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            LinearLayout masterLayout = new LinearLayout(G.context);
            masterLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            masterLayout.setLayoutParams(params);
            masterLayout.setGravity(Gravity.RIGHT);

            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lineParams.setMargins(8, 50, 25, 25);
            LinearLayout line = new LinearLayout(G.context);
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setLayoutParams(lineParams);
            line.setGravity(Gravity.CENTER);

            CardView cardView = new CardView(G.context);
            LinearLayout.LayoutParams cardParam = new LinearLayout.LayoutParams(0, 4, 1);
            cardView.setLayoutParams(cardParam);
            try {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.blue_eq));
            } catch (Exception e) {

            }
            cardParam.setMargins(2, 2, 16, 2);
            line.addView(cardView, cardParam);

            TextView textView = new TextView(G.context);
            textView.setText(items.get(i).GroupingSubject);
            textView.setGravity(Gravity.RIGHT);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            line.addView(textView, textParams);

            masterLayout.addView(line);

            RecyclerView list = new RecyclerView(G.context);
            AdapterHomeLists adapter = new AdapterHomeLists(G.context, items.get(i).courses, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                    , LinearLayoutManager.HORIZONTAL, true);
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            masterLayout.addView(list);

            llitems.addView(masterLayout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onResiveTabaghe(ArrayList<StGrouping> data) {
        adapterGrouping = new AdapterTabagheList(G.context, data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, true);
        groupingList.setLayoutManager(layoutManager);
        groupingList.setAdapter(adapterGrouping);
        adapterGrouping.notifyDataSetChanged();

    }

    @Override
    public void tabagheNahaei() {
        dialogProgres.closeProgresBar();
    }

    @Override
    public void sendMessageFTabagheT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void tabagheListItemClick(int position, int sourceNumber, int groupId) {
        dialogProgres.showProgresBar();
        queryForCourses(groupId);
    }
}