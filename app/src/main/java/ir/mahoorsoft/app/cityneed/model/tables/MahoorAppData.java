package ir.mahoorsoft.app.cityneed.model.tables;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.StMahoorAppData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-gh on 16-Mar-18.
 */

public class MahoorAppData {
    public interface OnMahoorAppDataListener {
        void resiveData(ArrayList<StMahoorAppData> data);

        void sendMessage(String message);

    }

    private OnMahoorAppDataListener onMahoorAppDataListener;

    public MahoorAppData(OnMahoorAppDataListener onMahoorAppDataListener) {
        this.onMahoorAppDataListener = onMahoorAppDataListener;
    }

    public void getMahoorAppData() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StMahoorAppData>> getData = api.getMahoorAppData();
        getData.enqueue(new Callback<ArrayList<StMahoorAppData>>() {
            @Override
            public void onResponse(Call<ArrayList<StMahoorAppData>> call, Response<ArrayList<StMahoorAppData>> response) {
                onMahoorAppDataListener.resiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StMahoorAppData>> call, Throwable t) {
                onMahoorAppDataListener.sendMessage(t.getMessage());
            }
        });

    }
}
