package ir.mahoorsoft.app.cityneed.model.tables.city;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.Message;
import retrofit2.Call;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StCity;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAHNAZ on 11/18/2017.
 */

public class City {

    private OnCityListener onCityListener;

    public City(OnCityListener onCityListener) {
        this.onCityListener = onCityListener;
    }

    public void getCity(String flag) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCity>> getCiy = api.getCity(flag);
        getCiy.enqueue(new Callback<ArrayList<StCity>>() {
            @Override
            public void onResponse(Call<ArrayList<StCity>> call, Response<ArrayList<StCity>> response) {
                onCityListener.onReceiveCity(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCity>> call, Throwable t) {
               onCityListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });


    }

    public interface OnCityListener {

        void onReceiveCity(ArrayList<StCity> citys);

        void sendMessage(String message);
    }
}
