package ir.mahoorsoft.app.cityneed.presenter;

/**
 * Created by RCC1 on 7/7/2018.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.course.Course;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;
import ir.mahoorsoft.app.cityneed.model.tables.teacher.Teacher;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;

public class ServiceUpdate extends Service implements Sabtenam.OnSabtenamListener, Teacher.OnTeacherListener, Course.OnCourseLitener, SmsBox.OnSmsBoxResponseListener {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ////////////////////////////////////////////////////////////checkForPm();
        G.makeNotification(this, new Intent(this, ActivityMain.class), 1, "fffff", "ffff");
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkForNewMessage() {

        (new SmsBox(this)).getNotifyData(Pref.getStringValue(PrefKey.apiCode, ""), 1);
    }

    private void checkForNewStudent() {
    }

    private void checkForNewCourse() {
    }

    private void checkForNewTeacher() {
    }

    @Override
    public void messageNotifyData(ArrayList<StNotifyData> data) {

    }

    @Override
    public void newCourseNotifyData(ArrayList<StNotifyData> data) {

    }

    @Override
    public void newTeacherNotifyData(ArrayList<StNotifyData> data) {

    }


    @Override
    public void SabtenamNotifyData(ArrayList<StNotifyData> data) {

    }


    @Override
    public void responseForMadrak(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void onReceiveData(ArrayList<StCourse> data, int listId) {

    }

    @Override
    public void DataForHomeLists(ArrayList<StCustomCourseListHome> data) {

    }

    @Override
    public void DataForCustomCourseListHome(ArrayList<StCustomCourseListHome> data) {

    }

    @Override
    public void onReceiveData(ArrayList<StTeacher> data) {

    }

    @Override
    public void onReceiveSelectedTeacher(ArrayList<StTeacher> data) {

    }

    @Override
    public void onReceiveCustomTeacherListData(ArrayList<StCustomTeacherListHome> data) {

    }

    @Override
    public void onReceiveFlagForDelete(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void resiveSms(ArrayList<StSmsBox> sms) {

    }

    @Override
    public void resiveFlag(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void deleteSmsFlag(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void sendingMessageFlag(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void checkSabtenam(ArrayList<ResponseOfServer> res) {

    }
}
