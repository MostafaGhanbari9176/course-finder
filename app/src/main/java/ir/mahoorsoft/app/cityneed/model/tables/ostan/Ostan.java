package ir.mahoorsoft.app.cityneed.model.tables.ostan;


import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import retrofit2.Call;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.db.DB;
import ir.mahoorsoft.app.cityneed.model.struct.StOstan;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAHNAZ on 11/18/2017.
 */

public class Ostan {

    private OnOstanListener onOstanListener;

    public Ostan(OnOstanListener onOstanListener) {
        this.onOstanListener = onOstanListener;
    }

    public void getOstan() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StOstan>> getOstan = api.getOstan();
        getOstan.enqueue(new Callback<ArrayList<StOstan>>() {
            @Override
            public void onResponse(Call<ArrayList<StOstan>> call, Response<ArrayList<StOstan>> response) {
                onOstanListener.onReceiveOstan(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<StOstan>> call, Throwable t) {
                onOstanListener.sendMessage(Message.convertRetrofitMessage(t.toString()));

            }
        });

    }

    public void searchOstan(String flag) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StOstan>> search = api.serachOstan(flag);
        search.enqueue(new Callback<ArrayList<StOstan>>() {
            @Override
            public void onResponse(Call<ArrayList<StOstan>> call, Response<ArrayList<StOstan>> response) {
                onOstanListener.onReceiveOstan(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StOstan>> call, Throwable t) {
                onOstanListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public interface OnOstanListener {
        void onReceiveOstan(ArrayList<StOstan> ostans);
        void sendMessage(String message);
    }
}
