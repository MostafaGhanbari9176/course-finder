package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 6/27/2018.
 */

public class Gift {

    public interface OnGiftResponseListener {
        void onReceiveFlag(ArrayList<ResponseOfServer> res);

        void sendMessage(String message);
    }

    private OnGiftResponseListener onGiftResponseListener;

    public Gift(OnGiftResponseListener onGiftResponseListener) {
        this.onGiftResponseListener = onGiftResponseListener;
    }

    public void checkGiftCode(String giftCode, String userApi) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> check = api.checkGiftCode(giftCode, userApi);
        check.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onGiftResponseListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onGiftResponseListener.sendMessage(t.getMessage());
            }
        });
    }
}
