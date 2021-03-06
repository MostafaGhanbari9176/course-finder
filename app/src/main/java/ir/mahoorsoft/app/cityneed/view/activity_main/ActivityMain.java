package ir.mahoorsoft.app.cityneed.view.activity_main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentFeedBack;
import ir.mahoorsoft.app.cityneed.view.ActivityAboutUs;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_grouping_list.FragmentGroupingList;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentSelfCourse;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_teacher_list.FragmentTeacherList;
import ir.mahoorsoft.app.cityneed.view.activity_profile.ActivityProfile;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm.ActivityAcountConfirm;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home.FragmentHome;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_map.FragmentMap;
import ir.mahoorsoft.app.cityneed.view.activity_main.fragment_search.FragmentSearch;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.courseLists.ActivitySabtenamList;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogAllHelp;
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
                    Toast.makeText(G.context, "?????????? ???????? ???????? ???????????? ?????? ????????", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuRegistryCourse:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    starterActivity(ActivitySabtenamList.class);
                else
                    Toast.makeText(G.context, "?????????? ???????? ???????? ???????????? ?????? ????????", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuTeacherLocation:
                replaceContentWith("fMap", new FragmentMap());
                return true;
            case R.id.menuTelChanel:
                showChanel();
                return true;
            case R.id.btnAboutUsMenu:
                starterActivity(ActivityAboutUs.class);
                return true;
            case R.id.btnFeedBackMenu:
                if (Pref.getBollValue(PrefKey.IsLogin, false))
                    shwoFeedBackDialog();
                else
                    Toast.makeText(this, "?????????? ???????? ???????? ???????????? ?????? ????????", Toast.LENGTH_SHORT).show();
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

            case R.id.menuAllHelp:
                (new DialogAllHelp(this)).Show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void inviteFriend() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "???????????? ???????? ??????");
            String sAux = "\n???????? ???? ???????? ???????????? ???????? ?????? ???? ?????? ?????????? ?????? ???? ???????? ?????? ???? ???????? ???????? ???????? ???????? ?????????? ???????? ???? ???? ?????????? ???????? ?????? ?? ?????? ???????? ???? ???? ???????????? ?????????????? ???? ?????????? ???????? ???????? ???? ???? ???????? ?????? ?????? ?????? ?? ?????? ???????? ?????????? ???????? .\n\n";
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
                    Toast.makeText(G.context, "??????!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityMain.this, "??????!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityMain.this, "??????!", Toast.LENGTH_SHORT).show();
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
                    editText.setError("?????? ???????? ???????? ????????");
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void sendFeedBackForServer(String text) {
        sendMessageFromFeedBack("?????????? ?????????? ?????????????? ...");
        (new PresentFeedBack(this)).saveFeedBack(text);
    }

    private void init() {
        pointers();
        profileCheck();
        setSupportActionBar(toolbar);
    }

    private void pointers() {
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
/*        if(key.equals("fSearch"))
            getSupportActionBar().hide();
        else
            getSupportActionBar().show();*/
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
                break;

            case "fMap":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.blue_ios));
                break;

            case "fSearch":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.purple_tel));
                break;

            case "fGroupingList":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.orange_tel));
                break;

            case "fSelfCourse":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.tealblue_ios));
                break;

            case "fTeacherList":
                navDown.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
                toolbar.setBackgroundColor(ContextCompat.getColor(ActivityMain.this, R.color.green_tel));
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
            showSnackBar(findViewById(R.id.LLActivityMain), "", "???????? ???? ????????????");
        }
    }

    private void showSnackBar(View view, String message, String buttonText) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(buttonText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!(Pref.getBollValue(PrefKey.telChanel, false)))
                            showDialogForTelChanel();
                        else
                            ActivityMain.this.finish();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        snackbar.show();
    }

    private void showDialogForTelChanel() {
        Pref.saveBollValue(PrefKey.telChanel, true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("?????????? ???? ?????????? ??????????????");
        builder.setMessage("???????????? ???? ???? ???????? ?????? ???????????? ???????????? ???? ???????????? ???? ???????? ?????????? ???????? ?????? ???????? ???? ???????????? ????????.");
        builder.setNegativeButton("??????", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityMain.this.finish();
            }
        });

        builder.setPositiveButton("?????????? ???? ??????????", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChanel();
                ActivityMain.this.finish();
            }
        });
        builder.show();
    }

    private void showChanel() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=CourseFinder"));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "???????????? ???????????? ?????? ????????", Toast.LENGTH_SHORT).show();
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
            sendMessageFromFeedBack("?????????????? ?????? ?????? ????");
/*        else
            sendMessageFromFeedBack(Message.getMessage(1));*/
    }

    @Override
    public void sendMessageFromFeedBack(String Message) {
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }
}
