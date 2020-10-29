package ir.mahoorsoft.app.cityneed.view.activity_account.activity_acount_confirm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.adapter.AdapterViewPager;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class ActivityAcountConfirm extends AppCompatActivity {
   // Toolbar tlb;
   // TabLayout tabLayout;
    AdapterViewPager adapterViewPager;
    ViewPager viewPager;

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
        //setSupportActionBar(tlb);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setTitle("حساب کاربری");
        //tabLayout.setupWithViewPager(viewPager);
        settingUpViewPager();
        showDialog();

    }

    private void settingUpViewPager() {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.add(new FragmentEmailConfirm(), "ثبت نام یا ورود با ایمیل");
       // adapterViewPager.add(new FragmentPhoneConfirm(), "ثبت نام یا ورود با شماره همراه");
        viewPager.setAdapter(adapterViewPager);

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("درحال حاظر سرویس ما فقط در شهر زاهدان ارائه می شود.");
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
        //tlb = (Toolbar) findViewById(R.id.tlbAcountConfirm);
       // tabLayout = (TabLayout) findViewById(R.id.tabsAcountConfirm);
    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();

    }


}
