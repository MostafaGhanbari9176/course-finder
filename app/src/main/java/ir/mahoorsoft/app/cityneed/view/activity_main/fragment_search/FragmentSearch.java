package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogDayWeek;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogGrouping;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class FragmentSearch extends Fragment implements View.OnClickListener, PresentCourse.OnPresentCourseLitener, AdapterCourseList.OnClickItemCourseList, DialogGrouping.OnTabagheItemClick, DialogDayWeek.ReturnDay, SwipeRefreshLayout.OnRefreshListener {

    CardView cvSearch;
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
    private Dialog dialog;
    ImageView imgSearch;
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtDay;
    private TextView txtGroup;

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
            rbCourseName.setChecked(true);
        }
        return view;
    }

    private void init() {
        pointers();
        getDataFromServer();
        runTxtSearchListener();
    }

    private void runTxtSearchListener() {
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
        PresentCourse presentCourse = new PresentCourse(G.context, this);
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
                throw new Exception("???????? ?????? ?????? ???? ???????? ???????? ????????");
            }

            if (maxOld < minOld)
                throw new Exception("???????? ?????? ?????? ???? ???????? ???????? ????????");
            if (sD.length() == 0 || eD.length() == 0)
                throw new Exception("???????? ?????????? ???????? ?? ?????????? ???????? ???? ???????????? ????????");
            if (eD.compareTo(sD) == -1)
                throw new Exception("???????? ?????????? ???????? ?? ?????????? ???????? ???? ???????? ???????????? ????????");
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
            if (((helpSource.get(i).CourseName).toLowerCase()).contains((searchFlag).toLowerCase()))
                serachSource.add(helpSource.get(i));
        }
        settingsView(serachSource.size() != 0);
        source.clear();
        source.addAll(serachSource);
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
                if (((helpSource.get(i).MasterName).toLowerCase()).contains((searchFlag).toLowerCase()))
                    serachSource.add(helpSource.get(i));
            } catch (Exception e) {
                e.getMessage();
            }
        }
        settingsView(serachSource.size() != 0);
        source.clear();
        source.addAll(serachSource);
        adapter.notifyDataSetChanged();
    }

    private void pointers() {
        imgSearch = (ImageView) view.findViewById(R.id.imgSearchFragmentSearch);
        cvSearch = (CardView) view.findViewById(R.id.CVTxtSearch);
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
        btnFilter.setOnClickListener(this);
        rbCourseName.setOnClickListener(this);
        rbTeacherName.setOnClickListener(this);
        btnDeleteFilter.setOnClickListener(this);
    }

    private void showDialog() {

        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = li.inflate(R.layout.dialog_filter, null, false);
        Button btnConfirm = (Button) dialogView.findViewById(R.id.btnConfirmFilter);
        LinearLayout btnDay = (LinearLayout) dialogView.findViewById(R.id.btnSelectDayFilter);
        LinearLayout btnStartDate = (LinearLayout) dialogView.findViewById(R.id.btnStartDateFilter);
        LinearLayout btnEndDate = (LinearLayout) dialogView.findViewById(R.id.btnEndDateFilter);
        LinearLayout btnGroup = (LinearLayout) dialogView.findViewById(R.id.btnSelectGroupFilter);

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
            settingsView(true);
            if (isFilterRes) {
                btnDeleteFilter.setVisibility(View.VISIBLE);
                isFilterRes = false;
            }
            if (course.size() == 0 || course.get(0).empty == 1) {
                txtEmpty.setVisibility(View.VISIBLE);
                source.clear();
                helpSource.clear();
                adapter = new AdapterCourseList(G.context, source, this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                        , LinearLayoutManager.VERTICAL, false);
                list.setLayoutManager(layoutManager);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            } else
                txtEmpty.setVisibility(View.GONE);
            if (helpSource != course) {
                helpSource.clear();
                helpSource.addAll(course);
            }
            if (source != course) {
                source.clear();
                source.addAll(course);
                if (txtSearch.getText().length() != 0) {
                    if (rbTeacherName.isChecked())
                        searchTeacherName(txtSearch.getText().toString());
                    else
                        searchCourseName(txtSearch.getText().toString());
                }

            }
            if (adapter == null) {
                adapter = new AdapterCourseList(G.context, source, this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                        , LinearLayoutManager.VERTICAL, false);
                list.setLayoutManager(layoutManager);
                list.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception ignore){}
    }

    private void settingsView(boolean normal) {

        if (normal) {
            cvSearch.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.light));
            imgSearch.setImageResource(R.drawable.icon_search_true);
            txtSearch.setTextColor(ContextCompat.getColor(G.context, R.color.dark_eq));
        } else {
            cvSearch.setCardBackgroundColor(ContextCompat.getColor(G.context, R.color.red_one));
            imgSearch.setImageResource(R.drawable.icon_search_false);
            txtSearch.setTextColor(getResources().getColor(R.color.light));
        }
    }

    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void courseListItemClick(int position, View view) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", source.get(position).id);
        intent.putExtra("teacherId", source.get(position).idTeacher);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(G.activity, view, "courseInHome");
            startActivity(intent, options.toBundle());
        } else
            startActivity(intent);
    }

    @Override
    public void days(String days) {
        day = days;
        txtDay.setText("?????????? ??????");
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

    }
}

