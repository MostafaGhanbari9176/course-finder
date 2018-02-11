package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

import ir.mahoorsoft.app.cityneed.model.tables.course.Course;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class PresentCourse implements Course.OnCourseLitener {
    public interface OnPresentCourseLitener {
        void sendMessageFCT(String message);

        void confirmCourse(int id);

        void onReceiveCourse(ArrayList<StCourse> course);

    }

    OnPresentCourseLitener onPresentCourseLitener;

    public PresentCourse(OnPresentCourseLitener onPresentCourseLitener) {
        this.onPresentCourseLitener = onPresentCourseLitener;
    }

    public void addCourse(String teacherId, String subject, int tabagheId, int type, int capacity, int mony, String sharayet, String tozihat, String startDate, String endDate, String day, String hours, int oldRange) {
        Course course = new Course(this);
        course.addtCource(teacherId, subject, tabagheId, type, capacity, mony, sharayet, tozihat, startDate, endDate, day, hours, oldRange);
    }

    public void getAllCourse(){
        Course course = new Course(this);
        course.getAllCourse();
    }

    public void getCourseById(int id) {
        Course course = new Course(this);
        course.getCourseById(id);
    }

    public void getCourseByTeacherId(String id) {
        Course course = new Course(this);
        course.getCourseByTeacherId(id);

    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if(res.get(0).code == 0)
            sendMessage("error");
      else
          onPresentCourseLitener.confirmCourse(res.get(0).code);
    }

    @Override
    public void onReceiveData(ArrayList<StCourse> data) {
        onPresentCourseLitener.onReceiveCourse(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentCourseLitener.sendMessageFCT(message);
    }
}
