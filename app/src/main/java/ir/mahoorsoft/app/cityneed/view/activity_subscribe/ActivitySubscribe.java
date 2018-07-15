package ir.mahoorsoft.app.cityneed.view.activity_subscribe;

import android.content.Intent;
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

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.presenter.PresentSubscribe;
import ir.mahoorsoft.app.cityneed.view.activity_subscribe.fragment_chose_subscribe.FragmentChoseSubscrib;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.FragmentProfileAmozeshgah;

/**
 * Created by RCC1 on 3/11/2018.
 */

public class ActivitySubscribe extends AppCompatActivity implements PresentSubscribe.OnPresentSubscribeListener {

    Toolbar tlb;
    boolean haveSubscribe;
    FragmentChoseSubscrib fragmentChoseSubscrib;
    RelativeLayout pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        G.context = this;
        G.activity = this;
        pbar = (RelativeLayout) findViewById(R.id.RLPbar);
        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    if (Pref.getStringValue(PrefKey.SubId, "").length() != 0) {
                        Pref.saveBollValue(PrefKey.isPaymentSuccess, true);
                        Pref.saveStringValue(PrefKey.refId, refID);
                        saveUserBuy(refID);
                    }
                } else {
                    PaymentRequest paymentRequest1 = paymentRequest;
                    /* When Payment Request is Failure :) */
                    //        fragmentChoseSubscrib.cancelPbar();
                    String message = "خطا,در صورت کسر مبلغ از حساب شما تا 48 ساعت آینده بازگشت داده خواهد شد,در غیر این صورت با ما تماس حاصل فرمایید.";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    ActivitySubscribe.this.finish();
                }


            }
        });

        init();
    }

    private void saveUserBuy(String refId) {
        pbar.setVisibility(View.VISIBLE);
        (new PresentSubscribe(this)).saveUserBuy(refId);
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
        getSupportActionBar().setTitle("اشتراک");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public static void replaceContentWith(Fragment fragment) {
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

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {

    }

    @Override
    public void sendMessageFromSubscribe(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        ActivitySubscribe.this.finish();
    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {

    }

    @Override
    public void onReceiveFlagFromSubscribe(boolean flag) {
        Pref.saveBollValue(PrefKey.isPaymentSaved, flag);
        if (flag) {
            Toast.makeText(this, "خرید موفق ...", Toast.LENGTH_SHORT).show();
            Pref.removeValue(PrefKey.SubId);
            Pref.removeValue(PrefKey.isPaymentSuccess);
            Pref.removeValue(PrefKey.refId);
        } else
            Toast.makeText(this, "خطا !", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        intent.putExtra("buyResult", flag);
        setResult(RESULT_OK, intent);
        ActivitySubscribe.this.finish();
    }
}
