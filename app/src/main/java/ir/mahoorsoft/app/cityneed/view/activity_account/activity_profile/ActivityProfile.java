package ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.fragment_profile_karbar.FragmentProfileKarbar;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogProgres;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogPrvince;


/**
 * Created by MAHNAZ on 10/16/2017.
 */

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener, PresentUser.OnPresentUserLitener {

    Button btnBack;
    Button btnLogOut;
    DialogProgres dialogProgres;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        dialogProgres = new DialogProgres(this);
        setContentView(R.layout.activity_profile);
        pointer();
        replaceContentWith(new FragmentProfileKarbar());
    }

    private void pointer() {

        (btnBack = (Button) findViewById(R.id.btnBack_Profile)).setOnClickListener(this);
        (btnLogOut = (Button) findViewById(R.id.btnLogOut)).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();
    }

    public static void replaceContentWith(Fragment fragment) {

        G.activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.contentProfile, fragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack_Profile:
                onBackPressed();
                break;
            case R.id.btnLogOut:
                queryForLogOut();
                break;
        }
    }

    private void queryForLogOut() {

        dialogProgres.showProgresBar();
        PresentUser presentUser = new PresentUser(this);
        presentUser.logOut(Long.parseLong(Pref.getStringValue(PrefKey.phone, "")));
    }

    @Override
    public void sendMessageFUT(String message) {
        dialogProgres.closeProgresBar();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveUser(ArrayList<StUser> users) {

    }

    @Override
    public void confirmUser(boolean flag) {
        dialogProgres.closeProgresBar();
        Pref.removeValue(PrefKey.phone);
        Pref.removeValue(PrefKey.userFamily);
        Pref.removeValue(PrefKey.userName);
        Pref.removeValue(PrefKey.location);
        this.finish();
    }

    @Override
    protected void onResume() {
        G.activity = this;
        G.context = this;
        super.onResume();
    }

    /*public static void showDialog(DialogPrvince.OnDialogPrvinceListener onDialogPrvinceListener) {
        if (dialogPrvince == null) {
            dialogPrvince = new DialogPrvince(ActivityProfile.this, onDialogPrvinceListener);
            dialogPrvince.showDialog();
        }else{
            dialogPrvince.showDialog();
        }
    }
*/
}


