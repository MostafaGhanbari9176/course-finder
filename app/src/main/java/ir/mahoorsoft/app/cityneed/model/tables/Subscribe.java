package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 5/8/2018.
 */

public class Subscribe {

    public void getSubscribeList() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StSubscribe>> getData = api.getSubscribeList();
        getData.enqueue(new Callback<ArrayList<StSubscribe>>() {
            @Override
            public void onResponse(Call<ArrayList<StSubscribe>> call, Response<ArrayList<StSubscribe>> response) {
                onSubscribeListener.onReceiveSubscribeList(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StSubscribe>> call, Throwable t) {
                onSubscribeListener.sendMessage(t.getMessage());
            }
        });
    }

    public void saveUserBuy(String ac, String refId, String subscribeId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> saveData = api.saveUserBuy(ac, refId, subscribeId);
        saveData.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onSubscribeListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSubscribeListener.sendMessage(t.getMessage());
            }
        });
    }

    public void getUserBuy(String ac) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StBuy>> getData = api.getUserSubscribe(ac);
        getData.enqueue(new Callback<ArrayList<StBuy>>() {
            @Override
            public void onResponse(Call<ArrayList<StBuy>> call, Response<ArrayList<StBuy>> response) {
                onSubscribeListener.onReceiveUserBuy(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StBuy>> call, Throwable t) {
                onSubscribeListener.sendMessage(t.getMessage());
            }
        });
    }

    public void checkUserBuy(String apiCode){

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StNotifyData>> getData = api.checkUserBuy(apiCode);
        getData.enqueue(new Callback<ArrayList<StNotifyData>>() {
            @Override
            public void onResponse(Call<ArrayList<StNotifyData>> call, Response<ArrayList<StNotifyData>> response) {
                onSubscribeListener.onRecieveBuyNotifyData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StNotifyData>> call, Throwable t) {

            }
        });
    }


    public Subscribe(OnSubscribeListener onSubscribeListener) {
        this.onSubscribeListener = onSubscribeListener;
    }

    OnSubscribeListener onSubscribeListener;

    public interface OnSubscribeListener {
        void onReceiveSubscribeList(ArrayList<StSubscribe> data);

        void onReceiveUserBuy(ArrayList<StBuy> data);

        void sendMessage(String message);

        void onReceiveFlag(ArrayList<ResponseOfServer> res);

        void onRecieveBuyNotifyData(ArrayList<StNotifyData> data);
    }
}
