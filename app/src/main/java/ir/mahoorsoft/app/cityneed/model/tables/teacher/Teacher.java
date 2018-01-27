package ir.mahoorsoft.app.cityneed.model.tables.teacher;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.Response;
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

    public void addTeacher(String landPhone, String address, String subject, String tozihat, int type, int cityId){

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<Response>> updateUser = api.addTeacher(Phone, landPhone, address, subject, tozihat, type, cityId);
        updateUser.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                onTeacherListener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
                onTeacherListener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public  interface OnTeacherListener {

        void onReceiveFlag(ArrayList<Response> res);
        void onReceiveData(ArrayList<StTeacher> data);
        void sendMessage(String message);
    }
}
