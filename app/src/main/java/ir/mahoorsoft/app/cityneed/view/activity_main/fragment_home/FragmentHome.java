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


import java.util.ArrayList;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.Items;
import ir.mahoorsoft.app.cityneed.view.GlideLoader;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityCoursesList;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterHomeLists;
import ir.mahoorsoft.app.cityneed.view.activity_main.activity_show_feature.ActivityShowFeature;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentHome extends Fragment implements AdapterHomeLists.setOnClickItem, ViewPager.OnPageChangeListener, OnPageClickListener {

    private ArrayList<Page> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    TextView txtOne;
    TextView txtTwo;
    TextView txtThree;
    View view;
    RecyclerView list;
    AdapterHomeLists adapterListView;
    ArrayList<Items> surce = new ArrayList<>();
    ViewPager vPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        pointer();
        setTextFont();
        settingUpVPager();
        settingUpList_one();
        settingUpList_three();
        settingUpList_two();


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

    private void settingUpList_three() {

        list = (RecyclerView) view.findViewById(R.id.RV_three);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, true);
        adapterListView = new AdapterHomeLists(G.context, surce, this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapterListView);
        adapterListView.notifyDataSetChanged();
        setSurce();

    }

    private void setSurce() {

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
    public void itemClick(int number) {

    }

    private void showCourcesMenu() {
        Intent intent = new Intent(G.context, ActivityCoursesList.class);
        startActivityForResult(intent,1);

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new Page("A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg", this));
        pageViews.add(new Page("B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg", this));
        pageViews.add(new Page("C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg", this));
    }
}