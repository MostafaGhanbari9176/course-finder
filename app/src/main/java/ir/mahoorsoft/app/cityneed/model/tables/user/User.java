package ir.mahoorsoft.app.cityneed.model.tables.user;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
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

    public void logIn(long phone) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> savePhone = api.logIn(phone);
        savePhone.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onUserLitener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void updateUser(long phone, String name, String family, int status, int type, int cityIde, int apiCode) {


        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> updateUser = api.updateUser(phone,name,family,status,type,cityIde,apiCode);
        updateUser.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onUserLitener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void getUser(long phone){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StUser>> getUser = api.getUser(phone);
        getUser.enqueue(new Callback<ArrayList<StUser>>() {
            @Override
            public void onResponse(Call<ArrayList<StUser>> call, retrofit2.Response<ArrayList<StUser>> response) {
                onUserLitener.onReceiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StUser>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });

    }

    public void logOut(long phone){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> logOut = api.logOut(phone);
        logOut.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onUserLitener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onUserLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public interface OnUserLitener {
        void onReceiveFlag(ArrayList<Response> res);
        void onReceiveData(ArrayList<StUser> data);
        void sendMessage(String message);
    }
}
