package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentSabtenam;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.presenter.PresenterSmsBox;
import ir.mahoorsoft.app.cityneed.view.CharCheck;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSdudentNameList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivityStudentNameList extends AppCompatActivity implements AdapterSdudentNameList.OnClickItemSdutentNameList, PresentUser.OnPresentUserLitener, PresenterSmsBox.OnPresentSmsBoxListener, PresentSabtenam.OnPresentSabtenamListaener, View.OnClickListener {
    boolean isUserChanged = true;
    AdapterSdudentNameList adapter;
    RecyclerView list;
    ArrayList<StUser> surce;
    DialogProgres dialogProgres;
    TextView txt;
    int courseId;
    String courseName;
    Toolbar tlb;
    FloatingActionButton fltbMenu;
    FloatingActionButton fltbCancel;
    FloatingActionButton fltbConfirm;
    boolean smsMore = false;
    boolean deleteMore = false;
    boolean confirmMore = false;
    boolean smallFltbIsShow = false;
    private Stack<Integer> checkedUser = new Stack<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_name_list);
        if (getIntent().getExtras() != null) {
            courseId = getIntent().getIntExtra("id", 0);
            courseName = getIntent().getStringExtra("name");
        }
        pointers();

        setSupportActionBar(tlb);
        getSupportActionBar().setTitle("محصلین این دوره");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialogProgres = new DialogProgres(this);
        surce = new ArrayList<>();

        adapter = new AdapterSdudentNameList(this, surce, this, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setSource();


    }

    private void pointers() {
        txt = (TextView) findViewById(R.id.txtEmptyCourseList);
        tlb = (Toolbar) findViewById(R.id.tlbList);
        list = (RecyclerView) findViewById(R.id.RVList);
        fltbMenu = (FloatingActionButton) findViewById(R.id.fltbActivityList);
        fltbCancel = (FloatingActionButton) findViewById(R.id.fltbCancelActivityList);
        fltbConfirm = (FloatingActionButton) findViewById(R.id.fltbConfirmActivityList);
        fltbMenu.setOnClickListener(this);
        fltbCancel.setOnClickListener(this);
        fltbConfirm.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.moreDelete:
                setUpDeleteMore();
                return true;
            case R.id.moreSms:
                setUpSmsMore();
                return true;
            case R.id.moreConfirm:
                setUpConfirmMore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpDeleteMore() {
        fltbMenu.setVisibility(View.VISIBLE);
        fltbConfirm.setImageResource(R.drawable.icon_delete_red);
        if (!(smsMore || deleteMore || confirmMore)) {
            adapter = new AdapterSdudentNameList(this, surce, this, true);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        deleteMore = true;
        smsMore = confirmMore = false;

    }

    private void setUpSmsMore() {
        fltbMenu.setVisibility(View.VISIBLE);
        fltbConfirm.setImageResource(R.drawable.icon_message_box);
        if (!(smsMore || deleteMore || confirmMore)) {
            adapter = new AdapterSdudentNameList(this, surce, this, true);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        smsMore = true;
        deleteMore = confirmMore = false;
    }

    private void setUpConfirmMore() {
        fltbMenu.setVisibility(View.VISIBLE);
        fltbConfirm.setImageResource(R.drawable.icon_checked);
        if (!(smsMore || deleteMore || confirmMore)) {
            adapter = new AdapterSdudentNameList(this, surce, this, true);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        confirmMore = true;
        smsMore = deleteMore = false;
    }

    private void setSource() {
        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.getRegistrationsName(courseId);
    }


    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LogOut(boolean flag) {

    }

    @Override
    public void LogIn(ResponseOfServer res) {

    }

    @Override
    public void LogUp(ResponseOfServer res) {

    }

    @Override
    public void onReceiveUser(ArrayList<StUser> students) {
        dialogProgres.closeProgresBar();
        if (students.get(0).empty == 1) {
            txt.setVisibility(View.VISIBLE);
            txt.setText("هیچ محصلی موجود نیست");
        } else {
            txt.setVisibility(View.GONE);
            surce.addAll(students);
            adapter = new AdapterSdudentNameList(this, surce, this, false);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sendSms(int position) {
        getTextMessage(position);
    }

    @Override
    public void deleteSabtenam(int position) {
        answerForDelete(position);
    }

    private void getTextMessage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ارسالی به " + surce.get(position).name);
        final EditText editText = new EditText(this);
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

    private void getTextMessageForMoreUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ارسالی به چندین نفر");
        final EditText editText = new EditText(this);
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
                sendMoreMessage(editText.getText().toString());
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void answerForDelete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("لغو ثبت نام " + surce.get(position).name);
        builder.setMessage("آیا از ادامه این کار مطمعن هستید؟؟");
        builder.setPositiveButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.setNegativeButton("لغو ثبت نام", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                queryForDelete(position);
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void queryForDelete(int position) {
        dialogProgres.showProgresBar();
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.updateCanceledFlag(surce.get(position).sabtenamId, 1);
    }

    private void sendMessage(int position, String text) {
        dialogProgres.showProgresBar();
        int type;
        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 1 || Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 2)
            type = 1;
        else
            type = 0;
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.saveSms(text, Pref.getStringValue(PrefKey.apiCode, ""), surce.get(position).apiCode, courseId, type);
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
        dialogProgres.closeProgresBar();
        if (flag)
            messageFromSmsBox("پیام ارسال شد");
        else
            messageFromSmsBox("پیام ارسال نشد");
    }

    @Override
    public void messageFromSmsBox(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMessageFST(String message) {

    }

    @Override
    public void confirmSabtenam(boolean flag) {
        dialogProgres.closeProgresBar();
        if (flag)
            sendMessageFUT("ثبت نام کاربر لغو شد");
        else
            sendMessageFUT("خطا!!!");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fltbActivityList:
                if (!smallFltbIsShow) {
                    smallFltbIsShow = true;
                    fltbConfirm.setVisibility(View.VISIBLE);
                    fltbCancel.setVisibility(View.VISIBLE);
                } else {
                    smallFltbIsShow = false;
                    fltbConfirm.setVisibility(View.GONE);
                    fltbCancel.setVisibility(View.GONE);
                }
                break;
            case R.id.fltbCancelActivityList:
                removeCheckBox();
                fltbConfirm.setVisibility(View.GONE);
                fltbCancel.setVisibility(View.GONE);
                fltbMenu.setVisibility(View.GONE);
                break;
            case R.id.fltbConfirmActivityList:

                if (AdapterSdudentNameList.checkedUser.size() == 0) {
                    Toast.makeText(this, "لطفا چند نفر را انتخاب کنید", Toast.LENGTH_SHORT).show();
                    break;
                }
                confirmAction();
                break;
        }
    }

    private void removeCheckBox() {
        adapter = new AdapterSdudentNameList(this, surce, this, false);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void confirmAction() {
        if (smsMore) {
            getTextMessageForMoreUser();
        } else if (deleteMore) {
        } else if (confirmMore) {
        }
    }

    private void sendMoreMessage(String message) {
        checkedUser.clear();
        checkedUser.addAll(AdapterSdudentNameList.checkedUser);
        int size = AdapterSdudentNameList.checkedUser.size();
        for (int i = 0; i < size; i++) {
            sendMessage(checkedUser.pop(), message);
        }
    }
}