package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class ActivityAcountConfirm extends AppCompatActivity {
    Toolbar tlb;
    TabLayout tabLayout;
    AdapterViewPager adapterViewPager;
    ViewPager viewPager;
    FragmentEmailConfirm fEC;
    FragmentPhoneConfirm fPC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        G.activity = this;
        G.context = this;
        setContentView(R.layout.activity_acount_confirm);
        init();
    }

    private void init() {
        pointer();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("حساب کاربری");
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
        showDialog();

    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        fEC = new FragmentEmailConfirm();
        adapterViewPager.add(fEC, "ثبت نام یا ورود با ایمیل");
        fPC = new FragmentPhoneConfirm();
        adapterViewPager.add(fPC, "ثبت نام یا ورود با شماره همراه");
        viewPager.setAdapter(adapterViewPager);

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("درحال حاظر سرویس ما فقط در زاهدان ارائه می شود.");
        builder.setNegativeButton("خب", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void pointer() {
        viewPager = (ViewPager) findViewById(R.id.vpAcountConfirm);
        tlb = (Toolbar) findViewById(R.id.tlbAcountConfirm);
        tabLayout = (TabLayout) findViewById(R.id.tabsAcountConfirm);
    }

    @Override
    public void onBackPressed() {
        if ((fEC.rbLogIn.isChecked() && fEC.txtEmail.length() > 0) || (fEC.rbLogUp.isChecked() && (fEC.txtEmail.length() > 0 || fEC.txtName.length() > 0))) {
            G.showSnackBar(findViewById(R.id.LLAccountConfirm), "در صورت بازگشت اطلاعات وارد شده پاک میشود", "بازگشت", this);
        } else {
            this.finish();
            super.onBackPressed();
        }
    }


}
