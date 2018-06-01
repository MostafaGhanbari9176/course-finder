package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 01-Jun-18.
 */

public class FeedBack {
    public interface OnFeedBackListener {
        void OnReceiveFlag(ArrayList<ResponseOfServer> res);

        void OnReceiveMessage(String message);
    }

    private OnFeedBackListener onFeedBackListener;

    public FeedBack(OnFeedBackListener onFeedBackListener) {
        this.onFeedBackListener = onFeedBackListener;
    }

    public void saveFeedBack(String ac, String feedBackText) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> save = api.saveFeedBack(ac, feedBackText);
        save.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onFeedBackListener.OnReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onFeedBackListener.OnReceiveMessage(t.getMessage());
            }
        });

    }
}
