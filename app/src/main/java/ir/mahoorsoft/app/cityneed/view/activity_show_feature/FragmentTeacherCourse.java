package ir.mahoorsoft.app.cityneed.view.activity_show_feature;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterCourseList;

/**
 * Created by M-gh on 03-Feb-18.
 */

public class FragmentTeacherCourse extends Fragment implements AdapterCourseList.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    TextView txt;
    AdapterCourseList adapter;
    RecyclerView list;
    ArrayList<StCourse> surce;
    SwipeRefreshLayout sDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list, container, false);
        init();
        return view;
    }

    private void init() {
        ((Toolbar) view.findViewById(R.id.tlbList)).setVisibility(View.GONE);
        txt = (TextView) view.findViewById(R.id.txtEmptyCourseList);
        sDown = (SwipeRefreshLayout) view.findViewById(R.id.SDFragmentSelfCourse);
        sDown.setOnRefreshListener(this);
        surce = new ArrayList<>();
        list = (RecyclerView) view.findViewById(R.id.RVList);
        adapter = new AdapterCourseList(G.context, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setSource();
    }

    private void setSource() {
        sDown.setRefreshing(true);
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getCourseByTeacherId(ActivityOptionalCourse.teacherId);
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
            if (course.get(0).empty == 1)
                txt.setVisibility(View.VISIBLE);
            else {
                txt.setVisibility(View.GONE);
                list = (RecyclerView) view.findViewById(R.id.RVList);
                surce.clear();
                surce.addAll(course);
                for (int i = 0; i < course.size(); i++) {
                    if (course.get(i).vaziat == 0)
                        surce.remove(course.get(i));
                }
                if (surce.size() == 0)
                    txt.setVisibility(View.VISIBLE);
                adapter = new AdapterCourseList(G.context, surce, this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(G.context
                        , LinearLayoutManager.VERTICAL, false);
                list.setLayoutManager(layoutManager);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception ignore) {
        }
    }


    @Override
    public void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items) {

    }

    @Override
    public void courseListItemClick(int position , View view) {
        Intent intent = new Intent(G.context, ActivityOptionalCourse.class);
        intent.putExtra("id", surce.get(position).id);
        intent.putExtra("teacherId", ActivityOptionalCourse.teacherId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(G.activity, view, "courseInHome");
            startActivity(intent, options.toBundle());
        }
        else
            startActivity(intent);
    }


    @Override
    public void onRefresh() {
        setSource();
    }
}
