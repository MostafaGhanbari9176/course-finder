package ir.mahoorsoft.app.cityneed.model;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class CheckedSTatuse {
    public interface OnCheckServer {
        void ResponseForSarverStatuse(ArrayList<ResponseOfServer> res);

        void ResponseForUserStatuse(ArrayList<ResponseOfServer> res);

        void sendMessage(String message);
    }

    OnCheckServer onCheckServer;

    public CheckedSTatuse(OnCheckServer onCheckServer) {
        this.onCheckServer = onCheckServer;
    }

    public void checkServerStatuse() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> checkedServer = api.checkedServerStatuse();
        checkedServer.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onCheckServer.ResponseForSarverStatuse(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCheckServer.sendMessage(t.getMessage());
            }
        });
    }

    public void checkUserStatuse() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> checkedServer = api.checkedUserStatuse(Pref.getStringValue(PrefKey.apiCode, "OP's"));
        checkedServer.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onCheckServer.ResponseForUserStatuse(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCheckServer.sendMessage(t.getMessage());
            }
        });
    }
}
