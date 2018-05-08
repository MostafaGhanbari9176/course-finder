package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 5/8/2018.
 */

public class Subscribe {

    public void getSubscribeList(){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StSubscribe>> getData = api.getSubscribeList();
        getData.enqueue(new Callback<ArrayList<StSubscribe>>() {
            @Override
            public void onResponse(Call<ArrayList<StSubscribe>> call, Response<ArrayList<StSubscribe>> response) {
                onSubscribeListener.onResiveSubscribeList(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StSubscribe>> call, Throwable t) {
                onSubscribeListener.sendMessage(t.getMessage());
            }
        });
    }






    public Subscribe (OnSubscribeListener onSubscribeListener){
        this.onSubscribeListener = onSubscribeListener;
    }
    OnSubscribeListener onSubscribeListener;
    public interface OnSubscribeListener{
        void onResiveSubscribeList(ArrayList<StSubscribe> data);
        void sendMessage(String message);
    }
}
