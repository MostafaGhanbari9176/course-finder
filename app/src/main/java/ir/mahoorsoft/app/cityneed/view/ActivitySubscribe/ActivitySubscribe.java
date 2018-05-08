package ir.mahoorsoft.app.cityneed.view.ActivitySubscribe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.view.ActivitySubscribe.fragment_chose_subscribe.FragmentChoseSubscrib;

/**
 * Created by RCC1 on 3/11/2018.
 */

public class ActivitySubscribe extends AppCompatActivity {

    Toolbar tlb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        G.context = this;
        G.activity = this;
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
        replaceContentWith(new FragmentChoseSubscrib());
        ((Button) findViewById(R.id.btn1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceContentWith(new FragmentChoseSubscrib());
            }
        });

        ((Button) findViewById(R.id.btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceContentWith(new FragmentShowSubscribeFeture());
            }
        });

    }

    private void pointers(){
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
}


//        Uri data = getIntent().getData();
//        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
//            @Override
//            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
//
//                String phone = paymentRequest.getMobile();
//                if (isPaymentSuccess) {
//                    /* When Payment Request is Success :) */
//                    String message = "Your Payment is Success :) " + refID;
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                } else {
//                    /* When Payment Request is Failure :) */
//                    String message = "Your Payment is Failure :(";
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//
//        btnPayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestPayment();
//            }
//        });
///////////////////////////////////////////
//private void requestPayment() {
////
////        ZarinPal       purchase = ZarinPal.getPurchase(this);
////        PaymentRequest payment  = ZarinPal.getPaymentRequest();
////
////        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
////        payment.setAmount(100);
////        payment.setDescription("In App ActivitySubscribe Test SDK");
////        payment.setCallbackURL("yourapp://app");     /* Your App Scheme */
////        payment.setMobile("09157474087");            /* Optional Parameters */
////        payment.setEmail("godhelot1@gmail.com");     /* Optional Parameters */
////
////
////        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
////            @Override
////            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
////
////                String s = authority;
////                if (status == 100) {
////                    /*
////                    When Status is 100 Open Zarinpal PG on Browser
////                    */
////                    startActivity(intent);
////                } else {
////                    Toast.makeText(getApplicationContext(), "Your Payment Failure :(", Toast.LENGTH_LONG).show();
////                }
////
////            }
////        });
//    }