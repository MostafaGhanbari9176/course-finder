package ir.mahoorsoft.app.cityneed.view.activity_subscribe.fragment_chose_subscribe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.presenter.PresentGift;
import ir.mahoorsoft.app.cityneed.presenter.PresentSubscribe;
import ir.mahoorsoft.app.cityneed.view.activity_subscribe.ActivitySubscribe;

import static android.app.Activity.RESULT_OK;

/**
 * Created by RCC1 on 5/7/2018.
 */

public class FragmentChoseSubscrib extends Fragment implements PresentSubscribe.OnPresentSubscribeListener, AdapterChoseSubscribe.SubscribeClick, View.OnClickListener, PresentGift.OnPresentGiftListener {

    View view;
    ArrayList<StSubscribe> source = new ArrayList<>();
    RecyclerView list;
    AdapterChoseSubscribe adapter;
    RelativeLayout pbar;
    Button btnCheckGiftCode;
    TextView txtGetGiftCide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chose_subscribe, container, false);
        init();
        return view;
    }

    private void init() {
        pointers();
        queryForSubscribes();
    }

    private void pointers() {
        pbar = (RelativeLayout) view.findViewById(R.id.RLPbarSubscribeList);
        list = (RecyclerView) view.findViewById(R.id.RVSubscribeList);
        btnCheckGiftCode = (Button) view.findViewById(R.id.btnCheckGiftCode);
        btnCheckGiftCode.setOnClickListener(this);
        txtGetGiftCide = (TextView) view.findViewById(R.id.txtGetGiftCode);
    }

    private void queryForSubscribes() {
        pbar.setVisibility(View.VISIBLE);
        (new PresentSubscribe(this)).getSubscribeList();
    }

    @Override
    public void onResiveSubscribeList(ArrayList<StSubscribe> data) {
        pbar.setVisibility(View.GONE);
        source.clear();
        source.addAll(data);
        adapter = new AdapterChoseSubscribe(G.context, source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendMessageFromSubscribe(String message) {
        pbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onReceiveFlagFromSubscribe(boolean flag) {
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void subscribeItemClick(int position) {
        requestPayment(position);
    }

    private void requestPayment(final int position) {

        pbar.setVisibility(View.VISIBLE);

        ZarinPal purchase = ZarinPal.getPurchase(G.context);
        PaymentRequest payment = ZarinPal.getPaymentRequest();

        payment.setMerchantID(G.MID);
        payment.setAmount(source.get(position).price);
        payment.setDescription("خرید اشتراک " + source.get(position).subject + " به قیمت " + source.get(position).price + " تومان ");
        payment.setCallbackURL("ir.mahoorsoft.app.cityneed://app");     /* Your App Scheme */
        payment.setEmail("godhelot1@gmail.com");     /* Optional Parameters */


        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {


                if (status == 100) {
                    /*
                    When Status is 100 Open Zarinpal PG on Browser
                    */
                    Pref.saveIntegerValue(PrefKey.SubId, source.get(position).id);
                    startActivity(intent);
                    G.activity.finish();

                } else {
                    pbar.setVisibility(View.GONE);
                    Toast.makeText(G.context, "خطا,لطفا دوباره تلاش کنید.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheckGiftCode:
                checkGiftCode();
                break;
        }
    }

    private void checkGiftCode() {
        if (TextUtils.isEmpty(txtGetGiftCide.getText().toString().trim())) {
            txtGetGiftCide.setError("خالی !!!");
            txtGetGiftCide.requestFocus();
        } else {
            pbar.setVisibility(View.VISIBLE);
            (new PresentGift(this)).checkGiftCode(txtGetGiftCide.getText().toString().trim());
        }
    }

    @Override
    public void flagFromGift(boolean flag) {
        pbar.setVisibility(View.GONE);
        Intent data = new Intent();
        data.putExtra("Gift", "Gift");
        getActivity().setResult(RESULT_OK, data);
        G.activity.finish();
    }

    @Override
    public void messageFromGift(String message) {
        pbar.setVisibility(View.GONE);
        Toast.makeText(G.context, message, Toast.LENGTH_SHORT).show();
    }
}
