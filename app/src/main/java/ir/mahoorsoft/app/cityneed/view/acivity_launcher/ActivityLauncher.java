package ir.mahoorsoft.app.cityneed.view.acivity_launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.localDatabase.LocalDatabase;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.presenter.PresentCheckedStatuse;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;



public class ActivityLauncher extends AppCompatActivity implements PresentCheckedStatuse.OnPresentCheckServrer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        G.context = this;
        G.activity = this;
        //Pref.saveBollValue(PrefKey.smsListReady, false);
        if (!Pref.getBollValue(PrefKey.smsListReady, false))
            setSmsTextData();
        runLogo();

    }

    private void setSmsTextData() {
        ArrayList<String> smsTexts = new ArrayList<>();
        smsTexts.add("جهت تکمیل ثبت نام به آموزشگاه مراجعه کنید");
        smsTexts.add("ثبت نام شما تایید شد.");
        smsTexts.add("ثبت نام شما لغو شد.");
        LocalDatabase.addSmsText(this, smsTexts);
        Pref.saveBollValue(PrefKey.smsListReady, true);
    }

    private void runLogo() {
        replaceContentWith(new FragmentLogo());
        if (Pref.getBollValue(PrefKey.IsLogin, false))
            checkedUserStatuse();
        else {
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    //set the new Content of your activity
                    next();
                }
            }.start();
        }
    }

    private void next() {

/*        if (Pref.getBollValue(PrefKey.hacked, false))
            replaceContentWith(new FragmentHacked());
        else*/
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        this.finish();
/*        else
            checkedServerStatuse();*/
    }

    private void checkedServerStatuse() {
        PresentCheckedStatuse presentCheckedStatuse = new PresentCheckedStatuse(this);
        presentCheckedStatuse.checkedServerStatuse();
    }

    private void checkedUserStatuse() {
        PresentCheckedStatuse presentCheckedStatuse = new PresentCheckedStatuse(this);
        presentCheckedStatuse.checkedUserStatuse();
    }

    @Override
    public void serverChecked(boolean online) {
        if (online) {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
            this.finish();
        } else {
            replaceContentWith(new FragmentErrorServer());
        }
    }

    @Override
    public void userChecked(boolean logIn) {

        if (!logIn) {
            Pref.removeValue(PrefKey.email);
            Pref.removeValue(PrefKey.apiCode);
            Pref.removeValue(PrefKey.userName);
            Pref.removeValue(PrefKey.location);
            Pref.removeValue(PrefKey.cityId);
            Pref.removeValue(PrefKey.subject);
            Pref.removeValue(PrefKey.address);
            Pref.removeValue(PrefKey.userTypeMode);
            Pref.removeValue(PrefKey.landPhone);
            Pref.removeValue(PrefKey.madrak);
            Pref.removeValue(PrefKey.lat);
            Pref.removeValue(PrefKey.lon);
            Pref.removeValue(PrefKey.IsLogin);
        }
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        this.finish();


    }

    public static void replaceContentWith(Fragment fragment) {
        G.activity.getSupportFragmentManager()
                .beginTransaction().replace(R.id.contentLuncher, fragment)
                .commit();
    }
}
