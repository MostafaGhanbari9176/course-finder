package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 7/12/2018.
 */

public class Notify {

    public interface OnNotifyResponseListener {
        void onReceiveDataFromNotify(ArrayList<StNotifyData> res);

        void onReceiveWeakNotifyData(ArrayList<StNotifyData> res);

        void onReceiveStartDateNotifyData(ArrayList<StNotifyData> res);

        void onReceiveFlagFromNotify(ArrayList<ResponseOfServer> res);

        void sendMessageFromNotify(String message);
    }

    private OnNotifyResponseListener onNotifyResponseListener;

    public Notify(OnNotifyResponseListener onNotifyResponseListener) {
        this.onNotifyResponseListener = onNotifyResponseListener;
    }

    public void saveNotifySetting(String apiCode, int courseId, int weakNotify, int startNotify) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> saveSetting = api.notifySetting(apiCode, courseId, weakNotify, startNotify);
        saveSetting.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onNotifyResponseListener.onReceiveFlagFromNotify(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onNotifyResponseListener.sendMessageFromNotify(t.getMessage());
            }
        });

    }

    public void getWeakNotifyData(String apiCode) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StNotifyData>> getData = api.getWeakNotifyData(apiCode);
        getData.enqueue(new Callback<ArrayList<StNotifyData>>() {
            @Override
            public void onResponse(Call<ArrayList<StNotifyData>> call, Response<ArrayList<StNotifyData>> response) {
                onNotifyResponseListener.onReceiveWeakNotifyData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StNotifyData>> call, Throwable t) {
                onNotifyResponseListener.sendMessageFromNotify(t.getMessage());
            }
        });
    }

    public void getStartNotifyData(String apiCode, String tomDate) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StNotifyData>> getData = api.getStartNotifyData(apiCode, tomDate);
        getData.enqueue(new Callback<ArrayList<StNotifyData>>() {
            @Override
            public void onResponse(Call<ArrayList<StNotifyData>> call, Response<ArrayList<StNotifyData>> response) {
                onNotifyResponseListener.onReceiveStartDateNotifyData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StNotifyData>> call, Throwable t) {
                onNotifyResponseListener.sendMessageFromNotify(t.getMessage());
            }
        });
    }

    public void getSettingNotifyData(String apiCode, int courseId) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StNotifyData>> getData = api.getSettingNotifyData(apiCode, courseId);
        getData.enqueue(new Callback<ArrayList<StNotifyData>>() {
            @Override
            public void onResponse(Call<ArrayList<StNotifyData>> call, Response<ArrayList<StNotifyData>> response) {
                onNotifyResponseListener.onReceiveDataFromNotify(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StNotifyData>> call, Throwable t) {
                onNotifyResponseListener.sendMessageFromNotify(t.getMessage());
            }
        });
    }


}
