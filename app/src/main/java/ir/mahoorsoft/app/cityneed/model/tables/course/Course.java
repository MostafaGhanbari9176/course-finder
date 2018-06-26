package ir.mahoorsoft.app.cityneed.model.tables.course;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by RCC1 on 1/29/2018.
 */

public class Course {
    public interface OnCourseLitener {
        void onReceiveFlag(ArrayList<ResponseOfServer> res);

        void onReceiveData(ArrayList<StCourse> data, int listId);

        void DataForHomeLists(ArrayList<StCustomCourseListHome> data);

        void DataForCustomCourseListHome(ArrayList<StCustomCourseListHome> data);

        void sendMessage(String message);
    }

    OnCourseLitener onCourseLitener;

    public Course(OnCourseLitener onCourseLitener) {
        this.onCourseLitener = onCourseLitener;
    }

    public void addtCource(String subject, int tabagheId, int type, int capacity, int mony, String sharayet, String tozihat, String startDate, String endDate, String day, String hours, int minOld, int maxOld) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.addCourse(Pref.getStringValue(PrefKey.apiCode, ""), subject, tabagheId, type, capacity, mony, sharayet, tozihat, startDate, endDate, day, hours, minOld, maxOld);
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

    public void updateDeletedFlag(int courseId, int code) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.updateDeletedFlag(courseId, code);
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


    public void getCourseByFilter(int minOld, int maxOld, String startDate, String endDate, int groupId, String days) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getAllCourse = api.getCourseByFilter(minOld, maxOld, startDate, endDate, groupId, days);
        getAllCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getAllCourse() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getAllCourse = api.getAllCourse();
        getAllCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getBookMarkCourses(String userApi){
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getAllCourse = api.getBookMarkCourses(userApi);
        getAllCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCustomCourseListData() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCustomCourseListHome>> get = api.getCustomCourseListData();
        get.enqueue(new Callback<ArrayList<StCustomCourseListHome>>() {
            @Override
            public void onResponse(Call<ArrayList<StCustomCourseListHome>> call, Response<ArrayList<StCustomCourseListHome>> response) {
                onCourseLitener.DataForCustomCourseListHome(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCustomCourseListHome>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCourseById(int id, String userApi) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getCourse = api.getCourseById(id, userApi);
        getCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCourseForListHome(int id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCustomCourseListHome>> get = api.getCourseForListHome(id);
        get.enqueue(new Callback<ArrayList<StCustomCourseListHome>>() {
            @Override
            public void onResponse(Call<ArrayList<StCustomCourseListHome>> call, Response<ArrayList<StCustomCourseListHome>> response) {
                onCourseLitener.DataForHomeLists(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StCustomCourseListHome>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCourseByGroupingId(int id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> get = api.getCourseByGroupingId(id);
        get.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getCourseByTeacherId(String apiCode) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getAllCourse = api.getCourseByTeacherId(apiCode);
        getAllCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getUserCourse() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getCourse = api.getUserCourse(Pref.getStringValue(PrefKey.apiCode, ""));
        getCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -1);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }
}
