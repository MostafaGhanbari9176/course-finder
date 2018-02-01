package ir.mahoorsoft.app.cityneed.model.tables.sabtenam;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
import ir.mahoorsoft.app.cityneed.model.struct.StUser;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 2/1/2018.
 */

public class Sabtenam {

    public interface OnSabtenamListener {
        void onReceiveFlag(ArrayList<Response> res);

        void sendMessage(String message);
    }

    OnSabtenamListener onSabtenamListener;

    public Sabtenam(OnSabtenamListener onSabtenamListener) {
        this.onSabtenamListener = onSabtenamListener;
    }

    public void add(int idCourse, String idTeacher, String idUser) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> add = api.sabtenam(idCourse, idTeacher, idUser);
        add.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onSabtenamListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onSabtenamListener.sendMessage(t.getMessage());
            }
        });
    }
}
