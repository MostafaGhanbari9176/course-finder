package ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
import ir.mahoorsoft.app.cityneed.view.dialog.DialogGetSmsText;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterSdudentNameList;

/**
 * Created by RCC1 on 1/22/2018.
 */

public class ActivityStudentNameList extends AppCompatActivity implements AdapterSdudentNameList.OnClickItemSdutentNameList, PresentUser.OnPresentUserLitener, PresenterSmsBox.OnPresentSmsBoxListener, PresentSabtenam.OnPresentSabtenamListaener, View.OnClickListener, DialogGetSmsText.DialogGetSmsTextListener, SwipeRefreshLayout.OnRefreshListener {
    boolean isUserChanged = true;
    SwipeRefreshLayout swipe;
    AdapterSdudentNameList adapter;
    RecyclerView list;
    ArrayList<StUser> source = new ArrayList<>();
    TextView txt;
    int courseId;
    public static boolean removeWaiting = false;
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
    private CardView cardView;
    private int position = -1;
    String needToBeDown = "";
    DialogGetSmsText dialogGetSmsText;
    String ac = Pref.getStringValue(PrefKey.apiCode, "");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_name_list);
        removeWaiting = false;
        if (getIntent().getExtras() != null) {
            courseId = getIntent().getIntExtra("id", 0);
            courseName = getIntent().getStringExtra("name");
        }
        pointers();
        setSupportActionBar(tlb);
        getSupportActionBar().setTitle("محصلین دوره " + courseName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialogGetSmsText = new DialogGetSmsText(this, this);
        queryForData();
    }

    private void pointers() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.SDStudentName);
        swipe.setOnRefreshListener(this);
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
            adapter = new AdapterSdudentNameList(this, source, this, true);
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
            adapter = new AdapterSdudentNameList(this, source, this, true);
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
            adapter = new AdapterSdudentNameList(this, source, this, true);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        confirmMore = true;
        smsMore = deleteMore = false;
    }

    private void queryForData() {
        swipe.setRefreshing(true);
        PresentUser presentUser = new PresentUser(this);
        presentUser.getRegistrationsName(courseId);
    }

    @Override
    public void sendMessageFUT(String message) {
        swipe.setRefreshing(false);
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
        swipe.setRefreshing(false);
        if (students.get(0).empty == 1) {
            txt.setVisibility(View.VISIBLE);
            txt.setText("هیچ محصلی موجود نیست");
        } else {
            txt.setVisibility(View.GONE);
            source.clear();
            source.addAll(students);
            adapter = new AdapterSdudentNameList(this, source, this, false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                    , LinearLayoutManager.VERTICAL, false);
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sendSms(int position) {
        dialogGetSmsText = new DialogGetSmsText(this, this, "ارسال پیام");
        dialogGetSmsText.showDialog();
        needToBeDown = "sendSms";
        this.position = position;
    }

    @Override
    public void deleteSabtenam(int position) {
        dialogGetSmsText = new DialogGetSmsText(this, this, "لغو ثبت نام");
        dialogGetSmsText.showDialog();
        needToBeDown = "deleteStudent";
        this.position = position;
    }

    @Override
    public void confirmStudent(int position, CardView cardView) {
        this.cardView = cardView;
        needToBeDown = "confirmStudent";
        this.position = position;
        dialogGetSmsText = new DialogGetSmsText(this, this, "تایید ثبت نام");
        dialogGetSmsText.showDialog();
    }

    private void queryForConfirm(int position, String message) {
        swipe.setRefreshing(true);
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.confirmStudent(source.get(position).sabtenamId, courseId, message, ac, source.get(position).apiCode);
    }

    private void deleteMore(String message) {
        checkedUser.clear();
        checkedUser.addAll(AdapterSdudentNameList.checkedPositions);
        JSONArray array = new JSONArray();
        int size = AdapterSdudentNameList.checkedPositions.size();
        for (int i = 0; i < size; i++) {
            int position = checkedUser.pop();
            try {
                JSONObject object = new JSONObject();
                object.put("sabtenamId", source.get(position).sabtenamId);
                object.put("courseId", courseId);
                object.put("userId", source.get(position).apiCode);
                object.put("ac", ac);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        queryForMoreDelete(array.toString(), message);
    }

    private void queryForMoreDelete(String jsonData, String message) {
        swipe.setRefreshing(true);
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.updateMoreCanceledFlag(jsonData, message);
    }

    private void queryForDelete(int position, String message) {
        swipe.setRefreshing(true);
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.updateCanceledFlag(source.get(position).sabtenamId, 1, courseId, message, ac, source.get(position).apiCode);
    }

    private void sendMessage(int position, String text) {
        swipe.setRefreshing(true);
        int type;
        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 1 || Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 2)
            type = 1;
        else
            type = 0;
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.saveSms(text, ac, source.get(position).apiCode, courseId, type);
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
        swipe.setRefreshing(false);
        onClick(findViewById(R.id.fltbCancelActivityList));
        if (flag)
            messageFromSmsBox("پیام ارسال شد");
        else
            messageFromSmsBox("خطا,پیام ارسال نشد");
    }

    @Override
    public void messageFromSmsBox(String message) {
        swipe.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMessageFST(String message) {
        swipe.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmSabtenam(boolean flag) {

        swipe.setRefreshing(false);
        if (flag) {
            changeColorItems();
            removeWaiting = true;
            sendMessageFUT("انجام شد");
        } else
            sendMessageFUT("خطا!!!");
        position = -1;
        onClick(findViewById(R.id.fltbCancelActivityList));
    }

    @Override
    public void confirmDelete(boolean flag) {
        swipe.setRefreshing(false);
        if (flag) {
            deleteItems();
            removeWaiting = true;
            sendMessageFUT("انجام شد");
        } else
            sendMessageFUT("خطا!!!");
        position = -1;
        onClick(findViewById(R.id.fltbCancelActivityList));
    }

    private void deleteItems() {
        checkedUser.clear();
        checkedUser.addAll(AdapterSdudentNameList.checkedPositions);
        Collections.sort(checkedUser);
        Collections.reverse(checkedUser);
        int size = AdapterSdudentNameList.checkedPositions.size();
        if (size == 0 && position != -1) {
            source.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, adapter.getItemCount());
        } else if (size != 0) {
            for (int i = 0; i < size; i++) {
                int position = checkedUser.pop();
                source.remove(position - i);
                adapter.notifyItemRemoved(position - i);
                adapter.notifyItemRangeChanged(position - i, adapter.getItemCount());
            }
        }
    }

    private void changeColorItems() {
        int size = AdapterSdudentNameList.checkedPositions.size();
        if (size == 0 && cardView != null) {
            StUser user = source.get(position);
            user.status = 1;
            source.set(position, user);
           // cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.light));
        } else if (size != 0) {
            for (int i = 0; i < size; i++) {
                StUser user = source.get( AdapterSdudentNameList.checkedPositions.get(i));
                user.status = 1;
                source.set(AdapterSdudentNameList.checkedPositions.get(i), user);
               // (AdapterSdudentNameList.checkedViews.pop()).setCardBackgroundColor(ContextCompat.getColor(this, R.color.light));
            }
        }
        adapter.notifyDataSetChanged();
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

                if (AdapterSdudentNameList.checkedPositions.size() == 0) {
                    Toast.makeText(this, "لطفا چند نفر را انتخاب کنید", Toast.LENGTH_SHORT).show();
                    break;
                }
                confirmAction();
                break;
        }
    }

    private void removeCheckBox() {
        adapter = new AdapterSdudentNameList(this, source, this, false);
        confirmMore = deleteMore = smsMore = false;
        smallFltbIsShow = false;
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void confirmAction() {
        if (smsMore) {
            dialogGetSmsText = new DialogGetSmsText(this, this, "ارسال پیام");
            dialogGetSmsText.showDialog();
            needToBeDown = "sendMoreSms";
        } else if (deleteMore) {
            dialogGetSmsText = new DialogGetSmsText(this, this, "لغو ثبت نام");
            dialogGetSmsText.showDialog();
            needToBeDown = "deleteMoreStudent";
        } else if (confirmMore) {
            dialogGetSmsText = new DialogGetSmsText(this, this, "تایید ثبت نام");
            dialogGetSmsText.showDialog();
            needToBeDown = "confirmMoreStudent";
        }
    }

    private void sendMoreMessage(String message) {
        checkedUser.clear();
        checkedUser.addAll(AdapterSdudentNameList.checkedPositions);
        JSONArray array = new JSONArray();
        int size = AdapterSdudentNameList.checkedPositions.size();
        for (int i = 0; i < size; i++) {
            int position = checkedUser.pop();
            try {
                JSONObject object = new JSONObject();
                object.put("sabtenamId", source.get(position).sabtenamId);
                object.put("courseId", courseId);
                object.put("userId", source.get(position).apiCode);
                object.put("ac", ac);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        queryForMoreSms(array.toString(), message);
    }

    private void queryForMoreSms(String data, String message) {
        swipe.setRefreshing(true);
        PresenterSmsBox presenterSmsBox = new PresenterSmsBox(this);
        presenterSmsBox.sendMoreSms(data, message);
    }

    private void confirmMoreStudent(String message) {
        checkedUser.clear();
        checkedUser.addAll(AdapterSdudentNameList.checkedPositions);
        JSONArray array = new JSONArray();
        int size = AdapterSdudentNameList.checkedPositions.size();
        for (int i = 0; i < size; i++) {
            int position = checkedUser.pop();
            try {
                JSONObject object = new JSONObject();
                object.put("sabtenamId", source.get(position).sabtenamId);
                object.put("courseId", courseId);
                object.put("userId", source.get(position).apiCode);
                object.put("ac", ac);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        queryForMoreConfirm(array.toString(), message);
    }

    private void queryForMoreConfirm(String jsonData, String message) {

        swipe.setRefreshing(true);
        PresentSabtenam presentSabtenam = new PresentSabtenam(this);
        presentSabtenam.confirmMoreStudent(jsonData, message);
    }

    @Override
    public void sendindSms(String smsText) {
        switch (needToBeDown) {
            case "confirmStudent":
                queryForConfirm(position, smsText);
                break;
            case "confirmMoreStudent":
                confirmMoreStudent(smsText);
                break;
            case "deleteStudent":
                queryForDelete(position, smsText);
                break;
            case "deleteMoreStudent":
                deleteMore(smsText);
                break;
            case "sendSms":
                sendMessage(position, smsText);
                break;
            case "sendMoreSms":
                sendMoreMessage(smsText);
                break;
        }
    }

    @Override
    public void onRefresh() {
        queryForData();
    }
}

