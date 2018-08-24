package ir.mahoorsoft.app.cityneed.model.tables.course;

import android.content.Context;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.localDatabase.LocalDatabase;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.api.Api;
import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
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

        void DataForHomeLists(ArrayList<StCustomCourseListHome> data, int groupId);

        void DataForCustomCourseListHome(ArrayList<StCustomCourseListHome> data);

        void sendMessage(String message);

        void newCourseNotifyData(ArrayList<StNotifyData> data);
    }

    OnCourseLitener onCourseLitener;

    public Course(OnCourseLitener onCourseLitener) {
        this.onCourseLitener = onCourseLitener;
    }

    public void addtCource(String subject, String teacherName, int tabagheId, int type, int capacity, int mony, String sharayet, String tozihat, String startDate, String endDate, String day, String hours, int minOld, int maxOld) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> methode = api.addCourse(Pref.getStringValue(PrefKey.apiCode, ""), teacherName, subject, tabagheId, type, capacity, mony, sharayet, tozihat, startDate, endDate, day, hours, minOld, maxOld);
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

    public void getAllCourse(Context context) {


        ArrayList<StCourse> data = LocalDatabase.getCourseList(context, "s");
        if (data.size() > 0) {
            onCourseLitener.onReceiveData(data, -11);
        }


        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCourse>> getAllCourse = api.getAllCourse();
        getAllCourse.enqueue(new Callback<ArrayList<StCourse>>() {
            @Override
            public void onResponse(Call<ArrayList<StCourse>> call, retrofit2.Response<ArrayList<StCourse>> response) {
                onCourseLitener.onReceiveData(response.body(), -11);
            }

            @Override
            public void onFailure(Call<ArrayList<StCourse>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }

    public void getBookMarkCourses(String userApi) {
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

    public void getCustomCourseListData(Context context) {

        ArrayList<StCustomCourseListHome> data = LocalDatabase.getCourseListSubject(context, "c");
        ArrayList<StCourse> data2 = LocalDatabase.getCourseList(context, "c");

        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).empty == 1)
                    continue;
                ArrayList<StCourse> courses = new ArrayList<>();
                for (int j = 0; j < data2.size(); j++) {
                    if (data2.get(j).courseListId.equals("c" + i)) {
                        courses.add(data2.get(j));
                    }
                }
                data.get(i).courses = courses;
            }

            onCourseLitener.DataForCustomCourseListHome(data);
        }

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

    public void getCourseForListHome(final int id, Context context) {

        ArrayList<StCustomCourseListHome> data = LocalDatabase.getCourseListSubject(context, "h");
        ArrayList<StCourse> data2 = LocalDatabase.getCourseList(context, "h");

        if (data.size() > 0 && id == -1) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).empty == 1)
                    continue;
                ArrayList<StCourse> courses = new ArrayList<>();
                for (int j = 0; j < data2.size(); j++) {
                    if (data2.get(j).courseListId.equals("h" + i)) {
                        courses.add(data2.get(j));
                    }
                }
                data.get(i).courses = courses;
            }

            onCourseLitener.DataForHomeLists(data, id);
        }

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StCustomCourseListHome>> get = api.getCourseForListHome(id);
        get.enqueue(new Callback<ArrayList<StCustomCourseListHome>>() {
            @Override
            public void onResponse(Call<ArrayList<StCustomCourseListHome>> call, Response<ArrayList<StCustomCourseListHome>> response) {
                onCourseLitener.DataForHomeLists(response.body(), id);
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

    public void upDateCourse(String teacherApi, int courseId, String startDate, String endDate, String hours, String days, int state) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<ResponseOfServer>> upDate = api.upDateCourse(teacherApi, courseId, startDate, endDate, hours, days, state);
        upDate.enqueue(new Callback<ArrayList<ResponseOfServer>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOfServer>> call, Response<ArrayList<ResponseOfServer>> response) {
                onCourseLitener.onReceiveFlag(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOfServer>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });

    }

    public void getNewCourseNotifyData(int lastId) {

        Api api = ApiClient.getClient().create(Api.class);
        Call<ArrayList<StNotifyData>> getData = api.getNewCourseNotifyData(lastId);
        getData.enqueue(new Callback<ArrayList<StNotifyData>>() {
            @Override
            public void onResponse(Call<ArrayList<StNotifyData>> call, Response<ArrayList<StNotifyData>> response) {
                onCourseLitener.newCourseNotifyData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<StNotifyData>> call, Throwable t) {
                onCourseLitener.sendMessage(t.getMessage());
            }
        });
    }
}
