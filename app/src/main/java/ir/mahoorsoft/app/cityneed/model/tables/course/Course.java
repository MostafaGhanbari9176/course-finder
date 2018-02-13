package ir.mahoorsoft.app.cityneed.model.tables.course;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by RCC1 on 1/29/2018.
 */

public class Course {
    public interface OnCourseLitener {
        void onReceiveFlag(ArrayList<ResponseOfServer> res);

        void onReceiveData(ArrayList<StCourse> data);

        void sendMessage(String message);
    }

    OnCourseLitener onCourseLitener;

    public Course(OnCourseLitener onCourseLitener) {
        this.onCourseLitener = onCourseLitener;
    }

    public void addtCource(String subject, int tabagheId, int type, int capacity, int mony, String sharayet, String tozihat, String startDate, String endDate, String day, String hours, String minOld, String maxOld) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.addCourse(Pref.getStringValue(PrefKey.apiCode,""), subject, tabagheId, type, capacity, mony, sharayet, tozihat, startDate, endDate, day, hours, minOld, maxOld);
        methode.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, retrofit2.Response<ArrayList<ResponseOfServer>> response) {
                onCourseLitener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCourseLitener.sendMessage(Message.convertRetrofitMessage(t.toString()));
            }
        });
    }

    public void getAllCourse() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getAllCourse = api.getAllCourse();
        getAllCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCourseById(int id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getCourse = api.getCourseById(id);
        getCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCourseByTeacherId(String id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getCourse = api.getCourseByTeacherId(id);
        getCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }
}
