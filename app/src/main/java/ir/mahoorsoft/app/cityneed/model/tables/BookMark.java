package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 26-Jun-18.
 */

public class BookMark {

    public interface OnBookMarkResponseListener {
        void sendMessage(String message);

        void onReceiveFlag(ArrayList<ResponseOfServer> res);
    }

    private OnBookMarkResponseListener onBookMarkResponseListener;

    public BookMark(OnBookMarkResponseListener onBookMarkResponseListener) {
        this.onBookMarkResponseListener = onBookMarkResponseListener;
    }

    public void saveBookMark(int courseId, String userApi) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.saveBookMark(courseId, userApi);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onBookMarkResponseListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onBookMarkResponseListener.sendMessage(t.getMessage());
            }
        });
    }

    public void removeBookMark(int courseId, String userApi) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.removeBookMark(courseId, userApi);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onBookMarkResponseListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onBookMarkResponseListener.sendMessage(t.getMessage());
            }
        });
    }
}

