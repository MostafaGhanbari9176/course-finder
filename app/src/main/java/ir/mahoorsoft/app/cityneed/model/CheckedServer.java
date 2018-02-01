package ir.mahoorsoft.app.cityneed.model;

import java.util.ArrayList;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class CheckedServer {
    public interface OnCheckServer {
        void serverChecked(ArrayList<Response> res);

        void sendMessage(String message);
    }

    OnCheckServer onCheckServer;

    public CheckedServer(OnCheckServer onCheckServer) {
        this.onCheckServer = onCheckServer;
    }

    public void checkServer() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> checkedServer = api.checkedServer();
        checkedServer.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onCheckServer.serverChecked(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onCheckServer.sendMessage(t.getMessage());
            }
        });
    }
}
