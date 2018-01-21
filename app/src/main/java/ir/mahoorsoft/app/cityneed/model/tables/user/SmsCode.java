package ir.mahoorsoft.app.cityneed.model.tables.user;

import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 12/17/2017.
 */

public class SmsCode {

    OnSmsCodeListener onSmsCodeListener;

    public SmsCode(OnSmsCodeListener onSmsCodeListener) {
        this.onSmsCodeListener = onSmsCodeListener;
    }

    public void createAndSaveSmsCode(String phone) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> smsCode = api.createSmsCode(phone);
        smsCode.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onSmsCodeListener.onRecirveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onSmsCodeListener.sendMessage(t.toString());
            }
        });
    }

    public void checkedSmsCode(String phone, int code) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> checkedSmsCode = api.checkedSmsCode(phone, code);
        checkedSmsCode.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onSmsCodeListener.onRecirveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onSmsCodeListener.sendMessage(t.toString());
            }
        });
    }

    public interface OnSmsCodeListener {
        void onRecirveFlag(ArrayList<Response> response);

        void sendMessage(String message);
    }
}
