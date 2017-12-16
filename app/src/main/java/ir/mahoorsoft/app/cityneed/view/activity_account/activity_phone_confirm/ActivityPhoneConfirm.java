package ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm.fragment_confirm_code.FragmentConfirmCode;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm.fragment_get_phone.FragmentGetPhone;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class ActivityPhoneConfirm extends AppCompatActivity {

    Toolbar tlb;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        setContentView(R.layout.activity_phone_confirm);
        pointers();
        if (Pref.getBollValue(PrefKey.codeFlag, false)) {
            replaceContentWith(new FragmentConfirmCode());
        } else {
            replaceContentWith(new FragmentGetPhone());
        }
        setSupportActionBar(tlb);
    }

    public static void replaceContentWith(Fragment fragment) {

        if (fragment.getClass() == FragmentConfirmCode.class) {
            Pref.saveBollValue(PrefKey.codeFlag, true);
        } else {
            Pref.saveBollValue(PrefKey.codeFlag, false);
        }
        G.activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.contentPhoneConfirm, fragment).commit();
    }

    private void pointers() {
        btnBack = (Button) findViewById(R.id.btnBackPhoneConfirm);
        tlb = (Toolbar) findViewById(R.id.tlbPhoneConfirm);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!Pref.getBollValue(PrefKey.codeFlag, false)) {
            this.finish();
            super.onBackPressed();
        } else {
            replaceContentWith(new FragmentGetPhone());
        }
    }
}
