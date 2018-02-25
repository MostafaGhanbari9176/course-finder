package ir.mahoorsoft.app.cityneed.model.tables.grouping;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StGrouping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 1/29/2018.
 */

public class Grouping {
    public interface OnTabagheListener {
        void resiveData(ArrayList<StGrouping> data);

        void sendMessage(String message);
    }
    OnTabagheListener onTabagheListener;
    public Grouping(OnTabagheListener onTabagheListener){
        this.onTabagheListener = onTabagheListener;
    }
    public void getTabaghe(int uperId) {
        Api aPi = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StGrouping>> getTabaghe = aPi.getTabaghe(uperId);
        getTabaghe.enqueue(new Callback<ArrayList<StGrouping>>() {
            @Override
            public void onResponse(Call<ArrayList<StGrouping>> call, Response<ArrayList<StGrouping>> response) {
                onTabagheListener.resiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StGrouping>> call, Throwable t) {
                onTabagheListener.sendMessage(t.getMessage());

            }
        });
    }
}
