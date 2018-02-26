package ir.mahoorsoft.app.cityneed.model.tables.user;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by MAHNAZ on 11/30/2017.
 */

public class User {

    OnUserLitener onUserLitener;

    public User(OnUserLitener onUserLitener) {
        this.onUserLitener = onUserLitener;
    }

    public void updateUser(String phone, String name) {


        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> updateUser = api.updateUser(phone,name);
        updateUser.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
    //            onUserLitener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void getUser(String phone){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StUser>> getUser = api.getUser(phone);
        getUser.enqueue(new Callback<ArrayList<StUser>>() {
            @Override
            public void onResponse(Call<ArrayList<StUser>> call, retrofit2.Response<ArrayList<StUser>> response) {
      //          onUserLitener.onReceiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StUser>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });

    }

    public void getRegistrationsName(int courseId){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StUser>> getUser = api.getRegistrationsName(courseId, Pref.getStringValue(PrefKey.apiCode,""));
        getUser.enqueue(new Callback<ArrayList<StUser>>() {
            @Override
            public void onResponse(Call<ArrayList<StUser>> call, retrofit2.Response<ArrayList<StUser>> response) {
                onUserLitener.onReceiveUser(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StUser>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });

    }

    public void logOut(String phone){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> logOut = api.logOut(phone);
        logOut.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onUserLitener.responseForLogOut(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void logUp(String phone, String name, int code){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> logOut = api.logUp(phone, name, code);
        logOut.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onUserLitener.responseForLogUp(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void logIn(String phone, int code){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> logOut = api.logIn(phone, code);
        logOut.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onUserLitener.responseForLogIn(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public interface OnUserLitener {
        void responseForLogIn(ArrayList<ResponseOfServer> res);
        void responseForLogUp(ArrayList<ResponseOfServer> res);
        void responseForLogOut(ArrayList<ResponseOfServer> res);
        void onReceiveUser(ArrayList<StUser> students);
        void sendMessage(String message);
    }
}
