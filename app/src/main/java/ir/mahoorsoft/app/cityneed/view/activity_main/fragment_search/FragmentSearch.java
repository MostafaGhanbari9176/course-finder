package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogDayWeek;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogGrouping;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentSearch extends Fragment implements View.OnClickListener, PresentCourse.OnPresentCourseLitener, AdapterCourseList.OnClickItemCourseList, DialogGrouping.OnTabagheItemClick, DialogDayWeek.ReturnDay, SwipeRefreshLayout.OnRefreshListener {

    View view;
    LinearLayout btnFilter;
    LinearLayout btnDeleteFilter;
    RadioButton rbCourseName;
    RadioButton rbTeacherName;
    RecyclerView list;
    ArrayList<StCourse> source = new ArrayList<>();
    ArrayList<StCourse> helpSource = new ArrayList<>();
    AdapterCourseList adapter;
    TextView txtSearch;
    TextView txtEmpty;
    private View dialogView;
    private Dialog dialog;
    private LinearLayout btnStartDate;
    private LinearLayout btnEndDate;
    private LinearLayout btnDay;
    private LinearLayout btnGroup;

    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtDay;
    private TextView txtGroup;

    private Button btnConfirm;
    TextView txtMinOld;
    TextView txtMaxOld;
    String sD = "";
    String eD = "";
    String day = "";
    String groupName = "";
    int groupId = 0;
    int minOld;
    int maxOld;
    boolean isFilterRes = false;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search, container, false);
            init();
        }
        return view;
    }

    private void init() {
        pointers();
        getDataFromServer();
        runTxtSerachListener();
    }

    private void runTxtSerachListener() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0)

                    onReceiveCourse(helpSource, -1);
                else {
                    if (rbTeacherName.isChecked())
                        searchTeacherName(s.toString());
                    else
                        searchCourseName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataFromServer() {
        sDown.setRefreshing(true);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getAllCourse();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llbtnFilter:
                showDialog();
                break;
            case R.id.rbBaseOnCourseNameSearch:
                searchCourseName(txtSearch.getText().toString());
                break;
            case R.id.rbBaseOnTeacherNameSearch:
                searchTeacherName(txtSearch.getText().toString());
                break;
            case R.id.btnConfirmFilter:
                confirmFilter();
                break;
            case R.id.btnSelectDayFilter:
                (new DialogDayWeek(G.context, this)).Show();
                break;
            case R.id.btnEndDateFilter:
                showDatePicker(false);
                break;
            case R.id.btnStartDateFilter:
                showDatePicker(true);
                break;
            case R.id.btnSelectGroupFilter:
                (new DialogGrouping(G.context, this)).Show();
                break;
            case R.id.llDeleteFilter:
                btnDeleteFilter.setVisibility(View.GONE);
                getDataFromServer();
                break;
        }

    }

    private void confirmFilter() {

        try {
            try {
                minOld = Integer.parseInt(txtMinOld.getText().toString().trim());
                maxOld = Integer.parseInt(txtMaxOld.getText().toString().trim());
            } catch (Exception e) {
                throw new Exception("لطفا رنج سنی را صحیح وارد کنید");
            }

            if (maxOld < minOld)
                throw new Exception("لطفا رنج سنی را صحیح وارد کنید");
            if (sD.length() == 0 || eD.length() == 0)
                throw new Exception("لطفا تاریخ شروع و پایان دوره را انتخاب کنید");
            if (eD.compareTo(sD) == -1)
                throw new Exception("لطفا تاریخ شروع و پایان دوره را صحیح انتخاب کنید");
            sendFilterForServer();
            dialog.cancel();
        } catch (Exception e) {
            sendMessageFCT(e.getMessage());
        }
    }

    private void sendFilterForServer() {
        sDown.setRefreshing(true);
        isFilterRes = true;
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByFilter(minOld, maxOld, sD, eD, groupId == 0 ? -1 : groupId, day.length() == 0 ? "-1" : day);
    }

    private void showDatePicker(final boolean isStartDate) {

        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance
                (new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                         if (isStartDate) {
                             sD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             txtStartDate.setText(sD);
                         } else {
                             eD = year + "-" + (((monthOfYear + 1) + "").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "") + "-" + ((dayOfMonth + "").length() == 1 ? "0" + dayOfMonth : dayOfMonth + "");
                             txtEndDate.setText(eD);
                         }
                     }
                 }, now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay());
        datePickerDialog.setThemeDark(true);
        datePickerDialog.show(G.activity.getFragmentManager(), "tpd");

    }

    private void searchCourseName(String searchFlag) {
        if (searchFlag.length() == 0) {
            onReceiveCourse(helpSource, -1);
            return;
        }
        ArrayList<StCourse> serachSource = new ArrayList<>();
        for (int i = 0; i < helpSource.size(); i++) {
            if ((helpSource.get(i).CourseName).contains(searchFlag))
                serachSource.add(helpSource.get(i));
        }
        if (serachSource.size() == 0) {
            txtSearch.setBackgroundResource(R.drawable.txt_search_errore);
            txtSearch.setTextColor(getResources().getColor(R.color.light));
        } else {
            txtSearch.setBackgroundResource(R.drawable.txt_search);
            txtSearch.setTextColor(getResources().getColor(R.color.dark_eq));
        }
        source.clear();
        source.addAll(serachSource);
        adapter = new AdapterCourseList(G.context, source, this);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void searchTeacherName(String searchFlag) {
        if (searchFlag.length() == 0) {
            onReceiveCourse(helpSource, -1);
            return;
        }
        ArrayList<StCourse> serachSource = new ArrayList<>();
        for (int i = 0; i < helpSource.size(); i++) {
            try {
                if ((helpSource.get(i).MasterName).contains(searchFlag))
                    serachSource.add(helpSource.get(i));
            } catch (Exception e) {
                e.getMessage();
            }
        }
        if (serachSource.size() == 0) {
            txtSearch.setBackgroundResource(R.drawable.txt_search_errore);
            txtSearch.setTextColor(getResources().getColor(R.color.light));
        } else {
            txtSearch.setBackgroundResource(R.drawable.txt_search);
            txtSearch.setTextColor(getResources().getColor(R.color.dark_eq));
        }
        source.clear();
        source.addAll(serachSource);
        adapter = new AdapterCourseList(G.context, source, this);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void pointers() {
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentSearch);
        sDown.setOnRefreshListener(this);
        dialog = new Dialog(G.context);
        txtSearch = (TextView) view.findViewById(R.id.txtSearch);
        rbCourseName = (RadioButton) view.findViewById(R.id.rbBaseOnCourseNameSearch);
        rbTeacherName = (RadioButton) view.findViewById(R.id.rbBaseOnTeacherNameSearch);
        btnFilter = (LinearLayout) view.findViewById(R.id.llbtnFilter);
        btnDeleteFilter = (LinearLayout) view.findViewById(R.id.llDeleteFilter);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmptySearch);
        btnDeleteFilter.setVisibility(View.GONE);
        list = (RecyclerView) view.findViewById(R.id.RVSerarch);
        rbCourseName.setChecked(true);
        btnFilter.setOnClickListener(this);
        rbCourseName.setOnClickListener(this);
        rbTeacherName.setOnClickListener(this);
        btnDeleteFilter.setOnClickListener(this);
    }

    private void showDialog() {

        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = li.inflate(R.layout.dialog_filter, null, false);
        btnConfirm = (Button) dialogView.findViewById(R.id.btnConfirmFilter);
        btnDay = (LinearLayout) dialogView.findViewById(R.id.btnSelectDayFilter);
        btnStartDate = (LinearLayout) dialogView.findViewById(R.id.btnStartDateFilter);
        btnEndDate = (LinearLayout) dialogView.findViewById(R.id.btnEndDateFilter);
        btnGroup = (LinearLayout) dialogView.findViewById(R.id.btnSelectGroupFilter);

        txtDay = (TextView) dialogView.findViewById(R.id.txtSelectDayFilter);
        txtStartDate = (TextView) dialogView.findViewById(R.id.txtStartDateFilter);
        txtEndDate = (TextView) dialogView.findViewById(R.id.txtEndDateFilter);
        txtGroup = (TextView) dialogView.findViewById(R.id.txtSelectGroupFilter);

        txtMinOld = (TextView) dialogView.findViewById(R.id.txtminOldFilter);
        txtMaxOld = (TextView) dialogView.findViewById(R.id.txtMaxOldFilter);

        btnGroup.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);
        btnStartDate.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnDay.setOnClickListener(this);


        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void sendMessageFCT(String message) {
        sDown.setRefreshing(false);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {

    }

    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        try {
            sDown.setRefreshing(false);
            dialog.cancel();
            txtSearch.setBackgroundResource(R.drawable.txt_search);
            txtSearch.setTextColor(getResources().getColor(R.color.dark_eq));
            if (isFilterRes) {
                btnDeleteFilter.setVisibility(View.VISIBLE);
                isFilterRes = false;
            }
            if (course.size() == 0 || course.get(0).empty == 1) {
                txtEmpty.setVisibility(View.VISIBLE);
                source.clear();
                adapter = new AdapterCourseList(G.context, source, this);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            } else
                txtEmpty.setVisibility(View.GONE);
            if (source != course) {
                source.clear();
                source.addAll(course);

            }
            if (helpSource != course) {
                helpSource.clear();
                helpSource.addAll(course);
            }
            adapter = new AdapterCourseList(G.context, source, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                    , LinearLayoutManager.VERTICAL, false);
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void courseListItemClick(int position) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", source.get(position).id);
        intent.putExtra("teacherId", source.get(position).idTeacher);
        startActivity(intent);
    }

    @Override
    public void days(String days) {
        day = days;
        txtDay.setText("تعیین شده");
        Toast.makeText(G.context, days, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tabagheInf(String name, int id) {
        groupId = id;
        groupName = name;
        txtGroup.setText(name);
    }

    @Override
    public void onRefresh() {
        init();
        ((ActivityMain) G.activity).helpSwipeProgress.setVisibility(View.GONE);
    }
}

