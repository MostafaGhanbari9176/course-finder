package ir.mahoorsoft.app.cityneed.model.tables.sabtenam;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class Sabtenam {

    public interface OnSabtenamListener {
        void onReceiveFlag(ArrayList<ResponseOfServer> res);

        void onReceiveFlagForDelete(ArrayList<ResponseOfServer> res);

        void sendMessage(String message);

        void checkSabtenam(ArrayList<ResponseOfServer> res);
    }

    OnSabtenamListener onSabtenamListener;

    public Sabtenam(OnSabtenamListener onSabtenamListener) {
        this.onSabtenamListener = onSabtenamListener;
    }

    public void add(int idCourse, String idTeacher, String idUser) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> add = api.sabtenam(idCourse, idTeacher, idUser);
        add.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSabtenamListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSabtenamListener.sendMessage(t.getMessage());
            }
        });
    }

    public void checkSabtenam(int idCourse, String idUser) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> add = api.checkSabtenam(idCourse, idUser);
        add.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSabtenamListener.checkSabtenam(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSabtenamListener.sendMessage(t.getMessage());
            }
        });
    }

    public void updateCanceledFlag(int sabtenamId, int code) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.updateCanceledFlag(sabtenamId, code);
        methode.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSabtenamListener.onReceiveFlagForDelete(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSabtenamListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void updateMoreCanceledFlag(String jsonData) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.updateMoreCanceledFlag(jsonData);
        methode.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSabtenamListener.onReceiveFlagForDelete(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSabtenamListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void confirmStudent(int sabtenamId, String apiCode, int courseId) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.confirmStudent(sabtenamId, courseId, apiCode);
        methode.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSabtenamListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSabtenamListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void confirmMoreStudent(String jsonData) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.confirmMoreStudent(jsonData);
        methode.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onSabtenamListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onSabtenamListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }
}
