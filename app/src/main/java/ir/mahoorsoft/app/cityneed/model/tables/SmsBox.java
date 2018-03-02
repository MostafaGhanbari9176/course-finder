package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 27-Feb-18.
 */

public class SmsBox {

    public interface OnSmsBoxResponseListener {
        void resiveSms(ArrayList<StSmsBox> sms);

        void resiveFlag(ResponseOfServer res);

        void deleteSmsFlag(ResponseOfServer res);

        void sendingMessageFlag(ResponseOfServer res);

        void sendMessage(String message);
    }

    OnSmsBoxResponseListener onSmsBoxResponseListener;

    public SmsBox(OnSmsBoxResponseListener onSmsBoxResponseListener) {
        this.onSmsBoxResponseListener = onSmsBoxResponseListener;
    }

    public void saveSms(String smsText, String tsId, String rsId, int courseId, int howSending) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.saveSms(smsText, tsId, rsId, courseId, howSending);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onSmsBoxResponseListener.sendingMessageFlag(response.body().get(0));
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSmsBoxResponseListener.sendMessage(t.getMessage());
            }
        });
    }

    public void getRsSms(String rsId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StSmsBox>> save = api.getRsSms(rsId);
        save.enqueue(new Callback<ArrayList<StSmsBox>>() {
            @Override
            public void onResponse(Call<ArrayList<StSmsBox>> call, Response<ArrayList<StSmsBox>> response) {
                onSmsBoxResponseListener.resiveSms(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StSmsBox>> call, Throwable t) {
                onSmsBoxResponseListener.sendMessage(t.getMessage());
            }
        });

    }

    public void getTsSms(String tsId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StSmsBox>> save = api.getTsSms(tsId);
        save.enqueue(new Callback<ArrayList<StSmsBox>>() {
            @Override
            public void onResponse(Call<ArrayList<StSmsBox>> call, Response<ArrayList<StSmsBox>> response) {
                onSmsBoxResponseListener.resiveSms(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StSmsBox>> call, Throwable t) {
                onSmsBoxResponseListener.sendMessage(t.getMessage());
            }
        });

    }

    public void upDateSeen(int smsId) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.upDateSeen(smsId);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onSmsBoxResponseListener.resiveFlag(response.body().get(0));
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSmsBoxResponseListener.sendMessage(t.getMessage());
            }
        });
    }

    public void deleteSms(int smsId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.deleteSms(smsId);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onSmsBoxResponseListener.deleteSmsFlag(response.body().get(0));
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSmsBoxResponseListener.sendMessage(t.getMessage());
            }
        });
    }
}
