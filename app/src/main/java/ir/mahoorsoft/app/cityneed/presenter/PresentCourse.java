package ir.mahoorsoft.app.cityneed.presenter;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;

import ir.mahoorsoft.app.cityneed.model.struct.StHomeListItems;
import ir.mahoorsoft.app.cityneed.model.tables.course.Course;

/**
 * Created by RCC1 on 1/30/2018.
 */

public class PresentCourse implements Course.OnCourseLitener {
    public interface OnPresentCourseLitener {
        void sendMessageFCT(String message);

        void confirmCourse(int id);

        void onReceiveCourse(ArrayList<StCourse> course, int listId);

        void onReceiveCourseForListHome(ArrayList<StHomeListItems> items);

    }

    OnPresentCourseLitener onPresentCourseLitener;

    public PresentCourse(OnPresentCourseLitener onPresentCourseLitener) {
        this.onPresentCourseLitener = onPresentCourseLitener;
    }

    public  void updateDeletedFlag(int courseId, int code){
        Course course = new Course(this);
        course.updateDeletedFlag(courseId, code);
    }

    public void addCourse(String subject, int tabagheId, int type, int capacity, int mony, String sharayet, String tozihat, String startDate, String endDate, String day, String hours, int minOld,int maxOld) {
        Course course = new Course(this);
        course.addtCource(subject, tabagheId, type, capacity, mony, sharayet, tozihat, startDate, endDate, day, hours, minOld, maxOld);
    }

    public void getAllCourse(){
        Course course = new Course(this);
        course.getAllCourse();
    }

    public void getNewCourse(){
        Course course = new Course(this);
        course.getNewCourse();
    }

    public void getCourseByFilter(int minOld, int maxOld, String startDate, String endDate, int groupId, String days){
        Course course = new Course(this);
        course.getCourseByFilter(minOld, maxOld, startDate, endDate, groupId, days);
    }

    public void getCourseForListHome(int id){
        Course course = new Course(this);
        course.getCourseForListHome(id);
    }

    public void getCourseByGroupingId(int id){
        Course course = new Course(this);
        course.getCourseByGroupingId(id);
    }

    public void getUserCourse(){
        Course course = new Course(this);
        course.getUserCourse();
    }

    public void getCourseById(int id) {
        Course course = new Course(this);
        course.getCourseById(id);
    }

    public void getCourseByTeacherId(String apiCode) {
        Course course = new Course(this);
        course.getCourseByTeacherId(apiCode);

    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {
        if(res.get(0).code == 0)
            sendMessage("خطا, باارز پوزش لطفا بعدا امتحان کنید");
      else
          onPresentCourseLitener.confirmCourse((int)res.get(0).code);
    }

    @Override
    public void onReceiveData(ArrayList<StCourse> data ,int listId) {

        onPresentCourseLitener.onReceiveCourse(data, listId);
    }

    @Override
    public void DataForHomeLists(ArrayList<StHomeListItems> data) {
        onPresentCourseLitener.onReceiveCourseForListHome(data);
    }

    @Override
    public void sendMessage(String message) {
        onPresentCourseLitener.sendMessageFCT(message);
    }
}
