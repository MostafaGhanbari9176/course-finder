package ir.mahoorsoft.app.cityneed.view.ActivitySubscribe;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.ActivitySubscribe.fragment_chose_subscribe.FragmentChoseSubscrib;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.FragmentProfileAmozeshgah;

/**
 * Created by RCC1 on 3/11/2018.
 */

public class ActivitySubscribe extends AppCompatActivity {

    Toolbar tlb;
    boolean haveSubscribe;
    FragmentChoseSubscrib fragmentChoseSubscrib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        G.context = this;
        G.activity = this;

        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    String message = "پرداخت با موفقیت انجام شد" + refID;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    /* When Payment Request is Failure :) */
            //        fragmentChoseSubscrib.cancelPbar();
                    String message = "خطا,در صورت کسر مبلغ از حساب شما تا 48 ساعت آینده بازگشت داده خواهد شد,در غیر این صورت با ما تماس حاصل فرمایید.";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }


            }
        });

        init();
    }

    private void init() {
        if (getIntent().getExtras() != null)
            haveSubscribe = getIntent().getBooleanExtra("haveASubscribe", false);
        if (haveSubscribe) {
            FragmentShowSubscribeFeture fragment = new FragmentShowSubscribeFeture();
            replaceContentWith(fragment);
            fragment.buyData = FragmentProfileAmozeshgah.subscribeData;
        } else {
            fragmentChoseSubscrib = new FragmentChoseSubscrib();
            replaceContentWith(fragmentChoseSubscrib);
        }
        pointers();
        setSupportActionBar(tlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlb.setTitle("لیست محصولات ما");
        tlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void pointers() {
        tlb = (Toolbar) findViewById(R.id.tlbSubscribe);
    }

    private void replaceContentWith(Fragment fragment) {
        G.activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.contentSubscribe, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        G.activity = this;
        G.context = this;
        super.onResume();
    }
}
