package ir.mahoorsoft.app.cityneed.view.courseLists;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.presenter.PresentCourse;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.presenter.PresenterSmsBox;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.activity_show_feature.ActivityOptionalCourse;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSabtenamList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivitySabtenamList extends AppCompatActivity implements AdapterSabtenamList.OnClickItemCourseList, PresentCourse.OnPresentCourseLitener, PresentSabtenam.OnPresentSabtenamListaener, PresenterSmsBox.OnPresentSmsBoxListener {

    AdapterSabtenamList adapter;
    RecyclerView list;
    ArrayList<StCourse> surce = new ArrayList<>();
    DialogProgres dialogProgres;
    TextView txt;
    Toolbar tlb;
    boolean isUserChanged = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        txt = (TextView) findViewById(R.id.txtEmptyCourseList);
        tlb = (Toolbar) findViewById(R.id.tlbList);
        setSupportActionBar(tlb);
        getSupportActionBar().setTitle("دوره های ثبت نام شده توسط شما");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialogProgres = new DialogProgres(this);
        list = (RecyclerView) findViewById(R.id.RVList);
        adapter = new AdapterSabtenamList(this, surce, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setSource();


    }

    private void setSource() {
        dialogProgres.showProgresBar();
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.getUserCourse();

    }

    @Override
    public void sendMessageFCT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmCourse(int id) {
//        if (id == 1) {
//            surce.remove(clickedPosition);
//            adapter.notifyItemRemoved(clickedPosition);
//            adapter.notifyItemRangeChanged(clickedPosition, adapter.getItemCount());
//        }
    }


    @Override
    public void onReceiveCourse(ArrayList<StCourse> course, int listId) {
        dialogProgres.closeProgresBar();
        if (course.get(0).empty == 1)
            txt.setVisibility(View.VISIBLE);
        else {
            txt.setVisibility(View.GONE);
            surce.addAll(course);
            adapter = new AdapterSabtenamList(this, surce, this);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
        intent.putExtra("id", surce.get(position).id);
        intent.putExtra("teacherId", surce.get(position).idTeacher);
        startActivity(intent);
    }

    @Override
    public void courseDeletedClick(int position) {
        if (surce.get(position).isCanceled == 1 || surce.get(position).isDeleted == 1)
            queryForUpdateCanceledFlag(surce.get(position).sabtenamId);
        surce.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
    }

    @Override
    public void sendSmsButtonPresed(int position) {

        getTextMessage(position);
    }

    private void getTextMessage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ارسالی به " + surce.get(position).MasterName);
        final EditText editText = new EditText(G.context);
        editText.setPadding(60, 60, 60, 60);
        editText.setGravity(Gravity.RIGHT);
        editText.setHint("متن پیام خود را وارد کنید");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUserChanged) {
                    isUserChanged = false;
                    // txtSharayet.setTextKeepState();
                    editText.setTextKeepState(CharCheck.faCheck(editText.getText().toString()));

                } else
                    isUserChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        builder.setView(editText);
        builder.setPositiveButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.setNegativeButton("ارسال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendMessage(position, editText.getText().toString());
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void sendMessage(int position, String text) {
        dialogProgres.showProgresBar();
        int type;
        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 1 || Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 2)
            type = 1;
        else
            type = 0;
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.saveSms(text, Pref.getStringValue(PrefKey.apiCode, ""), surce.get(position).idTeacher, surce.get(position).id, type);
    }

    private void queryForUpdateDeletedFlag(int id) {
        PresentCourse presentCourse = new PresentCourse(this);
        presentCourse.updateDeletedFlag(id, 2);
    }

    private void queryForUpdateCanceledFlag(int id) {
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.updateCanceledFlag(id, 2,-1,"","","");

    }

    @Override
    public void sendMessageFST(String message) {

    }

    @Override
    public void confirmSabtenam(boolean flag) {

    }

    @Override
    public void confirmDelete(boolean flag) {

    }

    @Override
    public void checkSabtenam(float ratBarValue) {

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onResiveSms(ArrayList<StSmsBox> sms) {

    }

    @Override
    public void onResiveFlagFromSmsBox(boolean flag) {

    }

    @Override
    public void smsDeleteFlag(boolean flag) {

    }

    @Override
    public void sendingMessageFlag(boolean flag) {
        if (flag)
            messageFromSmsBox("ارسال شد");
        else
            messageFromSmsBox("خطا");
    }

    @Override
    public void messageFromSmsBox(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}