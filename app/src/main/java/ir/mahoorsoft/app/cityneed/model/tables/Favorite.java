package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 24-Jun-18.
 */

public class Favorite {

    public interface OnFavoriteResponseListener {

        void sendMessage(String message);

        void onReceiveFlag(ArrayList<ResponseOfServer> res);
    }

    OnFavoriteResponseListener onFavoriteResponseListener;

    public Favorite(OnFavoriteResponseListener onFavoriteResponseListener) {
        this.onFavoriteResponseListener = onFavoriteResponseListener;
    }

    public void saveFavorite(String teacherApi, String userApi) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.saveFavorite(teacherApi, userApi);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onFavoriteResponseListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onFavoriteResponseListener.sendMessage(t.getMessage());
            }
        });
    }
}
