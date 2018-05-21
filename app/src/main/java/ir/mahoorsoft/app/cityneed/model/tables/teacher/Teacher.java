package ir.mahoorsoft.app.cityneed.model.tables.teacher;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCC1 on 12/21/2017.
 */

public class Teacher {

    String Phone = Pref.getStringValue(PrefKey.apiCode, "");
    OnTeacherListener onTeacherListener;

    public Teacher(OnTeacherListener onTeacherListener) {
        this.onTeacherListener = onTeacherListener;
    }

    public void addTeacher(String landPhone, String subject, String tozihat, int type, String lat, String lon, String address) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> updateUser = api.addTeacher(Pref.getStringValue(PrefKey.apiCode, ""), landPhone, subject, tozihat, type, lat, lon, address);
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

    public void getTeacher(String teacherId) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StTeacher>> getTeacher = api.getTeacher(teacherId);
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

    public void getAllTeacher() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StTeacher>> getTeacher = api.getAllTeacher();
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

    public void getSelectedTeacher() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StTeacher>> getTeacher = api.selectedTeacher();
        getTeacher.enqueue(new Callback<ArrayList<StTeacher>>() {
            @Override
            public void onResponse(Call<ArrayList<StTeacher>> call, retrofit2.Response<ArrayList<StTeacher>> response) {
                onTeacherListener.onReceiveSelectedTeacher(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StTeacher>> call, Throwable t) {
                onTeacherListener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCustomTeacherListData() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCustomTeacherListHome>> getCustomTeacherListData = api.getCustomTeacherListData();
        getCustomTeacherListData.enqueue(new Callback<ArrayList<StCustomTeacherListHome>>() {
            @Override
            public void onResponse(Call<ArrayList<StCustomTeacherListHome>> call, Response<ArrayList<StCustomTeacherListHome>> response) {
                onTeacherListener.onReceiveCustomTeacherListData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCustomTeacherListHome>> call, Throwable t) {
                onTeacherListener.sendMessage(t.getMessage());
            }
        });
    }

    public void updateTeacher(String landPhone, String subject, String address, int cityId, int madrak) {
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

    public void getMadrakStateAndRat() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> getMs = api.getMadrakStateAndRat(Pref.getStringValue(PrefKey.apiCode, ""));
        getMs.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onTeacherListener.responseForMadrak(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onTeacherListener.sendMessage(t.getMessage());
            }
        });
    }

    public void upMadrakState() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> getMs = api.upMadrakState(Pref.getStringValue(PrefKey.apiCode, ""));
        getMs.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
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

    public interface OnTeacherListener {
        void responseForMadrak(ArrayList<ResponseOfServer> res);

        void onReceiveFlag(ArrayList<ResponseOfServer> res);

        void onReceiveData(ArrayList<StTeacher> data);

        void onReceiveSelectedTeacher(ArrayList<StTeacher> data);

        void onReceiveCustomTeacherListData(ArrayList<StCustomTeacherListHome> data);

        void sendMessage(String message);
    }
}
