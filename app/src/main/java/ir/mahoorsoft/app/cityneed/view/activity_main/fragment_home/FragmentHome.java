package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.GlideLoader;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityCoursesList;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterHomeLists;
import ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature.ActivityShowFeature;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentHome extends Fragment implements AdapterHomeLists.setOnClickItem, ViewPager.OnPageChangeListener, OnPageClickListener, PresentCourse.OnPresentCourseLitener {

    private ArrayList<Page> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    TextView txtOne;
    TextView txtTwo;
    TextView txtThree;
    View view;
    RecyclerView list;
    AdapterHomeLists adapterListView;
    ArrayList<StCourse> surce = new ArrayList<>();
    DialogProgres dialogProgres;
    ViewPager vPager;

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
        setTextFont();
        settingUpVPager();
        //settingUpList_one();
        settingUpListThree();
        //settingUpList_two();


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

    private void settingUpList_one() {

        list = (RecyclerView) view.findViewById(R.id.RV_one);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, true);
        adapterListView = new AdapterHomeLists(G.context, surce, this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapterListView);
        adapterListView.notifyDataSetChanged();


    }

    private void settingUpList_two() {

        list = (RecyclerView) view.findViewById(R.id.RV_two);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, true);
        adapterListView = new AdapterHomeLists(G.context, surce, this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapterListView);
        adapterListView.notifyDataSetChanged();


    }

    private void settingUpListThree() {

        list = (RecyclerView) view.findViewById(R.id.RV_three);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, true);
        adapterListView = new AdapterHomeLists(G.context, surce, this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapterListView);
        adapterListView.notifyDataSetChanged();
        setSurceThree();

    }

    private void setSurceThree() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getAllCourse();
    }

    private void pointer() {

        txtOne = (TextView) view.findViewById(R.id.txtOne);
        txtTwo = (TextView) view.findViewById(R.id.txtTwo);
        txtThree = (TextView) view.findViewById(R.id.txtThree);

    }

    private void setTextFont() {

        Typeface typeface = Typeface.createFromAsset(G.context.getResources().getAssets(),
                "fonts/Far_Homa.ttf");
        txtOne.setTypeface(typeface);
        txtTwo.setTypeface(typeface);
        txtThree.setTypeface(typeface);
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
        intent.putExtra("id", surce.get(position).id);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initData() {
        pageViews = new ArrayList<>();
        for (int i = 0 ;i < surce.size(); i++) {
            pageViews.add(new Page(surce.get(i).id+"",ApiClient.serverAddress + "/city_need/v1/uploads/course/" + surce.get(i).id + ".png", this));
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
    public void onReceiveCourse(ArrayList<StCourse> course) {
        dialogProgres.closeProgresBar();
        settingUpVPager();
        surce = course;
        list = (RecyclerView) view.findViewById(R.id.RV_three);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, true);
        adapterListView = new AdapterHomeLists(G.context, surce, this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapterListView);
        adapterListView.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        setSurceThree();
    }
}