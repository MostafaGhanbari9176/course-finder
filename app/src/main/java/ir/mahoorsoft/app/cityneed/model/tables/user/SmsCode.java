package ir.mahoorsoft.app.cityneed.model.tables.user;

import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
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
        Call<ArrayList<ResponseOfServer>> smsCode = api.createSmsCode(phone);
        smsCode.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSmsCodeListener.responseOfServer(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSmsCodeListener.sendMessage(t.toString());
            }
        });
    }

    public interface OnSmsCodeListener {
        void responseOfServer(ArrayList<ResponseOfServer> responseOfServer);

        void sendMessage(String message);
    }
}
