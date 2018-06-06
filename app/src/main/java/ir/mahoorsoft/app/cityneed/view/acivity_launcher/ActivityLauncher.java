package ir.mahoorsoft.app.cityneed.view.acivity_launcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.localDatabase.LocalDatabase;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.presenter.PresentCheckedStatuse;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;


public class ActivityLauncher extends AppCompatActivity implements PresentCheckedStatuse.OnPresentCheckServrer, View.OnClickListener {


    LinearLayout serverError;
    LinearLayout updateNews;
    LinearLayout logo;
    TextView txtNewVersion;
    TextView txtCurrentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        pointer();
        if (!Pref.getBollValue(PrefKey.smsListReady, false))
            setSmsTextData();
        runLogo();

    }

    private void pointer() {
        serverError = (LinearLayout) findViewById(R.id.llServerError);
        logo = (LinearLayout) findViewById(R.id.llLogo);
        updateNews = (LinearLayout) findViewById(R.id.llUpdateNews);
        txtCurrentVersion = (TextView) findViewById(R.id.txtCurrentVersion);
        txtNewVersion = (TextView) findViewById(R.id.txtNewVersion);
        txtCurrentVersion.setText(G.VN);
        ((Button) findViewById(R.id.btnDownload)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnContinueLuncher)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnReConnectc)).setOnClickListener(this);
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
        serverError.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        updateNews.setVisibility(View.GONE);
        if (Pref.getBollValue(PrefKey.IsLogin, false))
            checkedUserStatuse();
        else
            checkVersion();

    }

    private void checkVersion() {
        (new PresentCheckedStatuse(this)).checkVersion();
    }

    private void next() {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        this.finish();

    }

    private void checkedUserStatuse() {
        PresentCheckedStatuse presentCheckedStatuse = new PresentCheckedStatuse(this);
        presentCheckedStatuse.checkedUserStatuse();
    }


    @Override
    public void versionChecked(ResponseOfServer rse) {
        if (rse.versionName.equals(G.VN))
            next();
        else {
            serverError.setVisibility(View.GONE);
            //logo.setVisibility(View.GONE);
            updateNews.setVisibility(View.VISIBLE);
            txtNewVersion.setText(rse.versionName);
        }


    }

    @Override
    public void userChecked(ResponseOfServer rse) {

        if (rse.code == 0) {
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
        if (rse.versionName.equals(G.VN))
            next();
        else {
            serverError.setVisibility(View.GONE);
         //   logo.setVisibility(View.GONE);
            updateNews.setVisibility(View.VISIBLE);
            txtNewVersion.setText(rse.versionName);
        }

    }

    @Override
    public void messageFromStatuse(String message) {
        serverError.setVisibility(View.VISIBLE);
      //  logo.setVisibility(View.GONE);
        updateNews.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnDownload:
                downloadApp();
                break;
            case R.id.btnReConnectc:
                runLogo();
                break;
            case R.id.btnContinueLuncher:
                next();
                break;
        }
    }

    private void downloadApp() {
        try {
            String url = G.appLink;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "خطا!", Toast.LENGTH_SHORT).show();
        }
    }
}
