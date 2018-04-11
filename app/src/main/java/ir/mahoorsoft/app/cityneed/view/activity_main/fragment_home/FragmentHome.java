package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentGrouping;
import ir.mahoorsoft.app.cityneed.presenter.PresentTeacher;
import ir.mahoorsoft.app.cityneed.view.GlideLoader;
import ir.mahoorsoft.app.cityneed.view.RandomColor;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterHomeLists;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterGroupingListHome;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterTeacherList;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityCoursesListByGroupingId;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivityCoursesListByTeacherId;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentHome extends Fragment implements AdapterHomeLists.setOnClickItem, ViewPager.OnPageChangeListener, OnPageClickListener, PresentCourse.OnPresentCourseLitener, PresentGrouping.OnPresentTabagheListener, AdapterGroupingListHome.OnClickItemTabagheList, PresentTeacher.OnPresentTeacherListener, AdapterTeacherList.OnClickItemTeacherList {
    LinearLayout scrollView;
    LinearLayout llitems;
    CardView btnDelete;
    LinearLayout llViewPager;
    private ArrayList<Page> pageViews;
    AdapterGroupingListHome adapterGrouping;
    private InfiniteIndicator mAnimCircleIndicator;
    View view;
    RecyclerView groupingList;
    DialogProgres dialogProgres;
    TextView txtEmpty;
    public static int id = -1;

    ProgressBar pbarSelectedTeacherList;
    ProgressBar pbarNewTeacherList;
    ProgressBar pbarNewCourseList;
    TextView txtEmptySelectedTeacherList;
    TextView txtEmptyNewTeacherList;
    TextView txtEmptyNewCourseList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        dialogProgres = new DialogProgres(G.context);
        pointer();
        getNewCourse();
        getSelectedTeacher();
        getNewTeacher();
        queeyForGroupingListData();
        dialogProgres.showProgresBar();
        queryForCourses(id);


    }

    private void queeyForGroupingListData() {
        PresentGrouping presentGrouping = new PresentGrouping(this);
        presentGrouping.getTabaghe(-1);
    }

    private void settingUpVPager() {
        mAnimCircleIndicator = (InfiniteIndicator) view.findViewById(R.id.viewPager);
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new GlideLoader())
                .isAutoScroll(true)
                .isLoop(true)
                .isDrawIndicator(true)
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
        presentCourse.getCourseForListHome(groupId);
    }

    private void pointer() {
       // (view.findViewById(R.id.llViewPager)).setBackgroundColor(RandomColor.randomColor(G.context));
        btnDelete = (CardView) view.findViewById(R.id.btnDeletGroupingHome);
        llViewPager = (LinearLayout) view.findViewById(R.id.llViewPager);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryForCourses(-1);
                adapterGrouping.setSelectedItem(null);
            }
        });
        btnDelete.setVisibility(View.GONE);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmptyHome);
        groupingList = (RecyclerView) view.findViewById(R.id.RVGroupingItemsHome);
        scrollView = (LinearLayout) view.findViewById(R.id.llSV);
        llitems = (LinearLayout) view.findViewById(R.id.llItemsHome);

        txtEmptyNewCourseList = (TextView) view.findViewById(R.id.txtEmptyNewCourseListHome);
        txtEmptyNewTeacherList = (TextView) view.findViewById(R.id.txtEmptyNewTeacherListHome);
        txtEmptySelectedTeacherList = (TextView) view.findViewById(R.id.txtEmptySelectedTeacherListHome);
        pbarNewTeacherList = (ProgressBar) view.findViewById(R.id.pbarNewTeacherListHome);
        pbarNewCourseList = (ProgressBar) view.findViewById(R.id.pbarNewCourseListHome);
        pbarSelectedTeacherList = (ProgressBar) view.findViewById(R.id.pbarSelectedTeacherListHome);

        setPaletteSize();
    }

    private void setPaletteSize(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        G.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        llViewPager.getLayoutParams().height = (int)(width/1.7);
    }

    @Override
    public void itemClick(int id, String teacherId) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", id);
        intent.putExtra("teacherId", teacherId);
        startActivity(intent);

    }

    @Override
    public void moreCourse(int groupingId) {
        Intent intent = new Intent(G.context, ActivityCoursesListByGroupingId.class);
        intent.putExtra("groupingId", groupingId);
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
        Intent intent = new Intent(G.context, ActivityCoursesListByTeacherId.class);
        intent.putExtra("apiCode", page.data);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void getSelectedTeacher() {
        pbarSelectedTeacherList.setVisibility(View.VISIBLE);
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getSelectedTeacher();
    }

    private void getNewTeacher() {
        pbarNewTeacherList.setVisibility(View.VISIBLE);
        PresentTeacher presentTeacher = new PresentTeacher(this);
        presentTeacher.getNewTeacher();
    }

    private void getNewCourse() {
        pbarNewCourseList.setVisibility(View.VISIBLE);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getNewCourse();
    }

    @Override
    public void sendMessageFCT(String message) {
        pbarNewCourseList.setVisibility(View.GONE);
        txtEmptyNewCourseList.setVisibility(View.VISIBLE);
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
    public void onReceiveNewCourse(ArrayList<StCourse> data) {
        pbarSelectedTeacherList.setVisibility(View.GONE);
        if (data == null || data.size() == 0 || data.get(0).empty == 1) {
            txtEmptyNewCourseList.setVisibility(View.VISIBLE);
            return;
        }
        txtEmptyNewCourseList.setVisibility(View.GONE);
        settingUpNewCourseList(data);
    }

    private void settingUpNewCourseList(ArrayList<StCourse> data) {
        RecyclerView list = (RecyclerView) view.findViewById(R.id.RVNewCourseHome);
        AdapterHomeLists adapter = new AdapterHomeLists(G.context, data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StHomeListItems> items) {
        dialogProgres.closeProgresBar();
        creatLists(items);
    }

    private void creatLists(ArrayList<StHomeListItems> items) {

        if (((LinearLayout) llitems).getChildCount() > 0)
            ((LinearLayout) llitems).removeAllViews();

        if (items.size() == 0 || items.get(0).empty == 1) {
            txtEmpty.setVisibility(View.VISIBLE);
            return;
        } else
            txtEmpty.setVisibility(View.GONE);

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

            TextView textView = new TextView(G.context);
            textView.setText(items.get(i).GroupingSubject);
            textView.setGravity(Gravity.RIGHT);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            line.addView(textView, textParams);

            CardView cardView = new CardView(G.context);
            LinearLayout.LayoutParams cardParam = new LinearLayout.LayoutParams(0, 4, 1);
            cardView.setLayoutParams(cardParam);
            try {
                cardView.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.blue_eq));
            } catch (Exception e) {

            }
            cardParam.setMargins(2, 2, 16, 2);
            line.addView(cardView, cardParam);


            masterLayout.addView(line);

            RecyclerView list = new RecyclerView(G.context);
            AdapterHomeLists adapter = new AdapterHomeLists(G.context, items.get(i).courses, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                    , LinearLayoutManager.HORIZONTAL, false);
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
        adapterGrouping = new AdapterGroupingListHome(G.context, data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, false);
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

    @Override
    public void sendMessageFTT(String message) {
        dialogProgres.closeProgresBar();
        pbarNewTeacherList.setVisibility(View.GONE);
        pbarSelectedTeacherList.setVisibility(View.GONE);
        txtEmptySelectedTeacherList.setVisibility(View.VISIBLE);
        txtEmptyNewTeacherList.setVisibility(View.VISIBLE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmTeacher(boolean flag) {

    }

    @Override
    public void onReceiveTeacher(ArrayList<StTeacher> data) {
        dialogProgres.closeProgresBar();
        if (data == null || data.size() == 0 || data.get(0).empty == 1)
            return;

    }

    @Override
    public void onReceiveNewTeacher(ArrayList<StTeacher> data) {
        pbarNewTeacherList.setVisibility(View.GONE);
        if (data == null || data.size() == 0 || data.get(0).empty == 1) {
            txtEmptyNewTeacherList.setVisibility(View.VISIBLE);
            return;
        }
        txtEmptyNewTeacherList.setVisibility(View.GONE);
        settingUpNewTeacherList(data);
    }

    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> data) {
        pbarSelectedTeacherList.setVisibility(View.GONE);
        if (data == null || data.size() == 0 || data.get(0).empty == 1) {

            return;
        }
        txtEmptySelectedTeacherList.setVisibility(View.GONE);
        settingUpSelectedTeacherList(data);

    }

    private void settingUpSelectedTeacherList(ArrayList<StTeacher> data) {
        pageViews = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            pageViews.add(new Page(data.get(i).ac, ApiClient.serverAddress + "/city_need/v1/uploads/teacher/" + data.get(i).pictureId + ".png", this));
        }
        settingUpVPager();


/*        RecyclerView list = (RecyclerView) view.findViewById(R.id.RVSelectedTeacherHome);
        AdapterTeacherList adapter = new AdapterTeacherList(G.context, data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }

    private void settingUpNewTeacherList(ArrayList<StTeacher> data) {

        txtEmptyNewTeacherList.setVisibility(View.GONE);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.RVNewTeacherHome);
        AdapterTeacherList adapter = new AdapterTeacherList(G.context, data, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void responseForMadrak(ResponseOfServer res) {

    }

    @Override
    public void teacherListItemClick(int position) {

    }
}