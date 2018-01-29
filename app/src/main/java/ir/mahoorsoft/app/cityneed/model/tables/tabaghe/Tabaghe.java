package ir.mahoorsoft.app.cityneed.model.tables.tabaghe;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StTabaghe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 1/29/2018.
 */

public class Tabaghe {
    public interface OnTabagheListener {
        void resiveData(ArrayList<StTabaghe> data);

        void sendMessage(String message);
    }
    OnTabagheListener onTabagheListener;
    public Tabaghe(OnTabagheListener onTabagheListener){
        this.onTabagheListener = onTabagheListener;
    }
    public void getTabaghe(int uperId) {
        Api aPi = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StTabaghe>> getTabaghe = aPi.getTabaghe(uperId);
        getTabaghe.enqueue(new Callback<ArrayList<StTabaghe>>() {
            @Override
            public void onResponse(Call<ArrayList<StTabaghe>> call, Response<ArrayList<StTabaghe>> response) {
                onTabagheListener.resiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StTabaghe>> call, Throwable t) {
                onTabagheListener.sendMessage(t.getMessage());

            }
        });
    }
}
