package ir.mahoorsoft.app.cityneed.model;

import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class CheckedServer {
    public interface OnCheckServer {
        void serverChecked(ArrayList<ResponseOfServer> res);

        void sendMessage(String message);
    }

    OnCheckServer onCheckServer;

    public CheckedServer(OnCheckServer onCheckServer) {
        this.onCheckServer = onCheckServer;
    }

    public void checkServer() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> checkedServer = api.checkedServer();
        checkedServer.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onCheckServer.serverChecked(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCheckServer.sendMessage(t.getMessage());
            }
        });
    }
}
