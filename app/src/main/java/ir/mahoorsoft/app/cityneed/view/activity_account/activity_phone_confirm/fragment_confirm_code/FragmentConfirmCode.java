package ir.mahoorsoft.app.cityneed.view.activity_account.activity_phone_confirm.fragment_confirm_code;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TimerTask;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import ir.mahoorsoft.app.cityneed.presenter.PresentUser;
import ir.mahoorsoft.app.cityneed.view.activity_account.activity_profile.ActivityProfile;

/**
 * Created by MAHNAZ on 10/22/2017.
 */

public class FragmentConfirmCode extends Fragment implements View.OnClickListener ,PresentUser.OnPresentUserLitener{

    private static final int SEND_AGAIN_BUTTON = 3;
    Thread thread;
    int conter = 0;
    View view;
    Button btnOk;
    Button btnReplay;
    TextView txt;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_code, container, false);
        pointers();
        timer();
        btnReplay.setEnabled(false);
        return view;
    }

    private void pointers() {
        txt = (TextView) view.findViewById(R.id.txtgetCode);
        btnOk = (Button) view.findViewById(R.id.btnOkCode);
        btnReplay = (Button) view.findViewById(R.id.btnReplaySend);

        btnReplay.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        txt.setText("");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnOkCode:
                getCode();
                break;
            case R.id.btnReplaySend:
                //REPLAY SEND
                break;
        }
    }

    private void getCode() {

        if (txt.getText().length() != 0) {
            checkCode(txt.getText().toString().trim());
            Pref.saveBollValue(PrefKey.codeFlag,false);
            Pref.saveStringValue(PrefKey.phone,Pref.getStringValue(PrefKey.fakePhone,""));
            PresentUser presentUser = new PresentUser(this);
            presentUser.getUser(Long.parseLong(Pref.getStringValue(PrefKey.phone,"")));
            Intent intent = new Intent(G.context,ActivityProfile.class);
            startActivity(intent);
            G.activity.finish();

        } else {

            Toast.makeText(G.context, "لطفا کد را وارد کنید", Toast.LENGTH_SHORT).show();
        }

    }

    private void showButton() {

        handler.post(new TimerTask() {
            @Override
            public void run() {
                if (conter >= SEND_AGAIN_BUTTON) {
                    btnReplay.setText("ارسال مجدد کد");
                    btnReplay.setEnabled(true);
                } else {

                    btnReplay.setText(String.valueOf(conter));
                }
            }
        });

    }

    private void checkCode(String code){

    }

    private void timer(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                while (conter <= SEND_AGAIN_BUTTON) {
                    showButton();
                    conter++;
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void sendMessageFUT(String message) {

    }

    @Override
    public void confirm(boolean flag) {

    }

    @Override
    public void onReceiveUser(ArrayList<StUser> users) {

    }
}
