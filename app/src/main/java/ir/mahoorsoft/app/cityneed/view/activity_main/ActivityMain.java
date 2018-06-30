package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentFeedBack;
import ir.mahoorsoft.app.cityneed.view.ActivityAboutUs;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list.FragmentGroupingList;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_teacher_list.FragmentTeacherList;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentSelfCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search.FragmentSearch;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;


public class ActivityMain extends AppCompatActivity implements PresentFeedBack.OnPresentFeedBackListener {
    Toolbar toolbar;
    LinearLayout llRadioGroup;
    RadioButton rbSelf;
    RadioButton rbOther;
    FrameLayout contentMain;
    public DialogProgres dialogProgres;
    BottomNavigationView navDown;
    HashMap<String, Fragment> fSaver = new HashMap<>();
    Stack<String> keySaver = new Stack<>();
    public RelativeLayout helpSwipeProgress;
    LinearLayout llBackHelpSwipeProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        setContentView(R.layout.activity_main);
        init();
        rbOther.setChecked(true);
        FragmentHome fragmentHome = new FragmentHome();
        replaceContentWith("fHome", fragmentHome);
        fragmentHome.activityMain = this;
        if (!Pref.getBollValue(PrefKey.helpSwipeProgres, false)) {
            helpSwipeProgress.setVisibility(View.VISIBLE);
            Pref.saveBollValue(PrefKey.helpSwipeProgres, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSmsBox:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySmsBox.class);
                else
                    Toast.makeText(G.context, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuRegistryCourse:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySabtenamList.class);
                else
                    Toast.makeText(G.context, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuTeacherLocation:
                replaceContentWith("fMap", new FragmentMap());
                return true;
            case R.id.btnAboutUsMenu:
                starterActivity(ActivityAboutUs.class);
                return true;
            case R.id.btnFeedBackMenu:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    shwoFeedBackDialog();
                else
                    Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.closeApp:
                this.finish();
                return true;
            case R.id.btnCallUSDialog:
                showDialogCallUs();
                return true;

            case R.id.btnInviteFriend:
                inviteFriend();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void inviteFriend() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "برنامه دوره یاب");
            String sAux = "\nبا برنامه دوره یاب می تونی دوره هایه مورد علاقه خودت رو به راحتی پیدا کنی و وقت خودت رو با آموختن بگذرونی یا اینکه دوره خودت رو در دوره یاب ثبت کنی و کلی محصل داشته باشی .\n\n";
            sAux = sAux + G.appLink + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void showDialogCallUs() {

        Dialog callUs = new Dialog(this);
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_call_us, null, false);
        ((LinearLayout) view.findViewById(R.id.llCallUsDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:05431132499"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(G.context, "خطا!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((LinearLayout) view.findViewById(R.id.llWebSiteDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "http://www.mahoorsoft.ir";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(ActivityMain.this, "خطا!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((LinearLayout) view.findViewById(R.id.llEmailDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent mailClient = new Intent(Intent.ACTION_VIEW);
                    mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
                    startActivity(mailClient);
                } catch (Exception e) {
                    Toast.makeText(ActivityMain.this, "خطا!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        callUs.setContentView(view);
        callUs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        callUs.show();

    }

    private void shwoFeedBackDialog() {
        final Dialog dialog = new Dialog(G.context);
        LayoutInflater li = (LayoutInflater) G.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_get_feed_back, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.txtGetFeedBack);

        ((Button) view.findViewById(R.id.btnCancelFeedBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Button btnConfirm = (Button) view.findViewById(R.id.btnSendFeedBack);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    sendFeedBackForServer(editText.getText().toString().trim());
                    dialog.cancel();
                } else
                    editText.setError("هیچ متنی وارد نشده");
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void sendFeedBackForServer(String text) {
        sendMessageFromFeedBack("درحال ارسال بازخورد ...");
        (new PresentFeedBack(this)).saveFeedBack(text);
    }

    private void init() {
        pointers();
        profileCheck();
        setSupportActionBar(toolbar);
    }

    private void pointers() {
        helpSwipeProgress = (RelativeLayout) findViewById(R.id.RLHelpSwipeProgres);
        llBackHelpSwipeProgress = (LinearLayout) findViewById(R.id.LLBackHelpSwipeProgress);
        navDown = (BottomNavigationView) findViewById(R.id.bottomNav_down_Home);
        navDown.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_tel));
        setNavigationItemListener();
        G.disableShiftModeNavigation(navDown);
        rbSelf = (RadioButton) findViewById(R.id.rbSelfMain);
        rbOther = (RadioButton) findViewById(R.id.rbOtherMain);
        llRadioGroup = (LinearLayout) findViewById(R.id.llRadioGroupMain);
        toolbar = (Toolbar) findViewById(R.id.tlbProfile);
        contentMain = (FrameLayout) findViewById(R.id.contentMain);
        rbOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    navDown.setSelectedItemId(R.id.homeNaveDownHome);
            }
        });
        rbSelf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    replaceContentWith("fSelfCourse", new FragmentSelfCourse());
                }
            }
        });
    }

    private void setNavigationItemListener() {

        navDown.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeNaveDownHome:
                        FragmentHome fragmentHome = new FragmentHome();
                        replaceContentWith("fHome", fragmentHome);
                        fragmentHome.activityMain = ActivityMain.this;
                        return true;

                    case R.id.searchNanDownHome:
                        replaceContentWith("fSearch", new FragmentSearch());
                        return true;

                    case R.id.groupingNavDownHome:
                        replaceContentWith("fGroupingList", new FragmentGroupingList());
                        return true;

                    case R.id.teacherListNavDownHome:
                        replaceContentWith("fTeacherList", new FragmentTeacherList());
                        return true;

                    case R.id.profileNavDownHome:
                        acountCheck();
                        return true;
                }
                return false;
            }

        });

    }

    public void replaceContentWith(String key, Fragment value) {
        if (!keySaver.empty() && keySaver.peek().equals(key))
            return;
        boolean isAvailable = false;
        for (Map.Entry m : fSaver.entrySet()) {
            if (m.getKey() == key) {
                isAvailable = true;
                break;
            }
        }
        try {
            if (!isAvailable) {
                fSaver.put(key, value);
            }
        } catch (Exception e) {
            keySaver.clear();
            fSaver.clear();
        }
        G.activity.getSupportFragmentManager()
                .beginTransaction().replace(R.id.contentMain, fSaver.get(key))
                .commit();
        setNavBottomColor(key);
        addKeyForBack(key);
        switch (key) {
            case "fHome":
                rbOther.setChecked(true);
                break;
            case "fSelfCourse":
                rbSelf.setChecked(true);
                break;
        }
    }

    private void setNavBottomColor(String key) {
        switch (key) {
            case "fHome":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_tel));
                llBackHelpSwipeProgress.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_tel));
                break;

            case "fMap":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                llBackHelpSwipeProgress.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                break;

            case "fSearch":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                llBackHelpSwipeProgress.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                break;

            case "fGroupingList":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                llBackHelpSwipeProgress.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                break;

            case "fSelfCourse":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                llBackHelpSwipeProgress.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                break;

            case "fTeacherList":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                llBackHelpSwipeProgress.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                break;
        }
    }

    private void addKeyForBack(String key) {
        if (keySaver.size() >= 4) {
            keySaver.removeElement(key);
        }
        keySaver.add(key);
    }

    public void starterActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (keySaver.size() > 1) {
            keySaver.pop();
            replaceContentWith(keySaver.pop(), null);
        } else {
            G.showSnackBar(findViewById(R.id.LLActivityMain), "", "خروج از برنامه", this);
        }
    }

    private void acountCheck() {
        if (Pref.getBollValue(PrefKey.IsLogin, false)) {
            starterActivity(ActivityProfile.class);

        } else {
            starterActivity(ActivityAcountConfirm.class);
        }
    }

    private void profileCheck() {

        if (Pref.getIntegerValue(PrefKey.userTypeMode, 0) == 0 || !(Pref.getBollValue(PrefKey.IsLogin, false))) {
            llRadioGroup.setVisibility(View.GONE);
        } else {
            rbOther.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        G.activity = this;
        G.context = this;
        profileCheck();
        super.onResume();
    }

    @Override
    public void OnReceiveFlagFromFeedBack(Boolean flag) {
        if (flag)
            sendMessageFromFeedBack("بازخورد شما ثبت شد");
        else
            sendMessageFromFeedBack(Message.getMessage(1));
    }

    @Override
    public void sendMessageFromFeedBack(String Message) {
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }
}
