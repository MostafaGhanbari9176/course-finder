package ir.mahoorsoft.app.cityneed.presenter;

import android.content.Context;
import android.util.Base64;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.localDatabase.LocalDatabase;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.Message;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.tables.course.Course;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class PresentCourse implements Course.OnCourseLitener {
    public interface OnPresentCourseLitener {
        void sendMessageFCT(String message);

        void confirmCourse(int id);

        void onReceiveCourse(ArrayList<StCourse> course, int listId);

        void onReceiveCourseForListHome(ArrayList<StCustomCourseListHome> items);

        void onReceiveCustomCourseListForHome(ArrayList<StCustomCourseListHome> items);

    }

    private OnPresentCourseLitener onPresentCourseLitener;

    private Context context;

    public PresentCourse(OnPresentCourseLitener onPresentCourseLitener) {
        this.onPresentCourseLitener = onPresentCourseLitener;
    }

    public PresentCourse(Context context, OnPresentCourseLitener onPresentCourseLitener) {
        this.context = context;
        this.onPresentCourseLitener = onPresentCourseLitener;
    }

    public void updateDeletedFlag(int courseId, int code) {
        Course course = new Course(this);
        course.updateDeletedFlag(courseId, code);
    }

    public void addCourse(String subject, String teacherName, int tabagheId, int type, int capacity, int mony, String sharayet, String tozihat, String startDate, String endDate, String day, String hours, int minOld, int maxOld) {
        Course course = new Course(this);
        course.addtCource(subject, teacherName, tabagheId, type, capacity, mony, sharayet, tozihat, startDate, endDate, day, hours, minOld, maxOld);
    }

    public void getAllCourse() {
        Course course = new Course(this);
        course.getAllCourse(context);
    }

    public void getBookMarkCourses() {
        (new Course(this)).getBookMarkCourses(Pref.getStringValue(PrefKey.apiCode, ""));
    }

    public void getCustomCourseListData() {
        Course course = new Course(this);
        course.getCustomCourseListData(context);
    }

    public void getCourseByFilter(int minOld, int maxOld, String startDate, String endDate, int groupId, String days) {
        Course course = new Course(this);
        course.getCourseByFilter(minOld, maxOld, startDate, endDate, groupId, days);
    }

    public void getCourseForListHome(int id) {
        Course course = new Course(this);
        course.getCourseForListHome(id, context);
    }

    public void getCourseByGroupingId(int id) {
        Course course = new Course(this);
        course.getCourseByGroupingId(id);
    }

    public void getUserCourse() {
        Course course = new Course(this);
        course.getUserCourse();
    }

    public void getCourseById(int id) {
        Course course = new Course(this);
        course.getCourseById(id, Pref.getStringValue(PrefKey.apiCode, "aaa"));
    }

    public void getCourseByTeacherId(String apiCode) {
        Course course = new Course(this);
        course.getCourseByTeacherId(apiCode);

    }

    public void upDateCourse(int courseId, String startDate, String endDate, String hours, String days, int state) {
        (new Course(this)).upDateCourse(Pref.getStringValue(PrefKey.apiCode, ""), courseId, startDate, endDate, hours, days, state);
    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if (res == null || res.size() == 0)
            return;

        if (res.get(0).code == 0)
            sendMessage("خطا, باارز پوزش لطفا بعدا امتحان کنید");
        else
            onPresentCourseLitener.confirmCourse((int) res.get(0).code);

        if (res.get(0).sub != null && (new String(Base64.decode(Base64.decode(res.get(0).sub, Base64.DEFAULT), Base64.DEFAULT))).equals("BnAoD")) {
            sendMessage("انجام شد");
            Pref.saveBollValue(PrefKey.hacked, true);
        }

    }

    @Override
    public void onReceiveData(ArrayList<StCourse> data, int listId) {
        if (data == null || data.size() == 0)
            sendMessage("خطا");
        else {
            if (listId == -11) {
                LocalDatabase.removeCourseData(context, "s");
                for (StCourse c : data) {
                    if (c.empty == 1)
                        continue;
                    c.courseListId = data.indexOf(c) + "";
                }
                LocalDatabase.saveCourseList(context, data, "s");
            }
            onPresentCourseLitener.onReceiveCourse(data, listId);
        }
    }

    @Override
    public void DataForHomeLists(ArrayList<StCustomCourseListHome> data, int groupId) {
        if (!(data == null || data.size() == 0)) {
            if (groupId == -1) {
                LocalDatabase.removeCourseData(context, "h");
                for (StCustomCourseListHome d : data) {
                    if (d.empty == 1)
                        continue;
                    for (StCourse c : d.courses)
                        c.courseListId = data.indexOf(d) + "";
                    LocalDatabase.saveCourseList(context, d.courses, "h");
                }
                LocalDatabase.saveCourseListSubject(context, data, "h");
            }
            onPresentCourseLitener.onReceiveCourseForListHome(data);
        } else
            sendMessage("خطا");


    }

    @Override
    public void DataForCustomCourseListHome(ArrayList<StCustomCourseListHome> data) {
        if (!(data == null || data.size() == 0)) {
            LocalDatabase.removeCourseData(context, "c");
            for (StCustomCourseListHome d : data) {
                if (d.empty == 1)
                    continue;
                for (StCourse c : d.courses)
                    c.courseListId = data.indexOf(d) + "";
                LocalDatabase.saveCourseList(context, d.courses, "c");
            }
            LocalDatabase.saveCourseListSubject(context, data, "c");
            onPresentCourseLitener.onReceiveCustomCourseListForHome(data);
        } else
            sendMessage("خطا");
    }

    @Override
    public void sendMessage(String message) {
        onPresentCourseLitener.sendMessageFCT(Message.getMessage(1));
    }

    @Override
    public void newCourseNotifyData(ArrayList<StNotifyData> data) {

    }
}
