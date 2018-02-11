package ir.mahoorsoft.app.cityneed.model.tables.teacher;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by RCC1 on 12/21/2017.
 */

public class Teacher {

    String Phone = Pref.getStringValue(PrefKey.phone,"");
    OnTeacherListener onTeacherListener;
    public Teacher(OnTeacherListener onTeacherListener){this.onTeacherListener = onTeacherListener;}

    public void addTeacher(String landPhone, String subject, String tozihat, int type, String lat, String lon){

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> updateUser = api.addTeacher(Phone, landPhone, subject, tozihat, type, lat, lon);
        updateUser.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onTeacherListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onTeacherListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void getTeacher(){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StTeacher>> getTeacher = api.getTeacher(Pref.getStringValue(PrefKey.apiCode,""));
        getTeacher.enqueue(new Callback<ArrayList<StTeacher>>() {
            @Override
            public void onResponse(Call<ArrayList<StTeacher>> call, retrofit2.Response<ArrayList<StTeacher>> response) {
                onTeacherListener.onReceiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StTeacher>> call, Throwable t) {
                onTeacherListener.sendMessage(t.getMessage());
            }
        });
    }

    public void updateTeacher(String landPhone, String subject, String address, int cityId, int madrak){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> updateTracher = api.updateTeacher(Phone, landPhone, address, subject, cityId, madrak);
        updateTracher.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onTeacherListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onTeacherListener.sendMessage(t.getMessage());
            }
        });
    }

    public  interface OnTeacherListener {

        void onReceiveFlag(ArrayList<ResponseOfServer> res);
        void onReceiveData(ArrayList<StTeacher> data);
        void sendMessage(String message);
    }
}
