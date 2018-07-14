package ir.mahoorsoft.app.cityneed.presenter.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.localDatabase.LocalDatabase;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.tables.Notify;
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.course.Course;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;
import ir.mahoorsoft.app.cityneed.model.tables.teacher.Teacher;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.ActivityTeacherCoursesList;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.date.DateCreator;
import ir.mahoorsoft.app.cityneed.view.date.Roozh;

/**
 * Created by M-gh on 14-Aug-17.
 */

public class ServiceNotification extends Service implements Sabtenam.OnSabtenamListener, SmsBox.OnSmsBoxResponseListener, Teacher.OnTeacherListener, Course.OnCourseLitener, Notify.OnNotifyResponseListener {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        checkForNewCourse();
        checkForNewMessage();
        checkForNewStudent();
        checkForNewTeacher();
        checkForStartDateNotifyData();
        checkForWeakNotifyData();


        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void checkForNewMessage() {
        int lastId = Pref.getIntegerValue(PrefKey.lastIdMessage, 0);
        (new SmsBox(this)).getNotifyData(Pref.getStringValue(PrefKey.apiCode, ""), lastId);
    }

    private void checkForNewStudent() {
        int lastId = Pref.getIntegerValue(PrefKey.lastIdSabtenam, 0);
        (new Sabtenam(this)).getNotifyData(Pref.getStringValue(PrefKey.apiCode, ""), lastId);
    }

    private void checkForNewCourse() {

        int lastId = Pref.getIntegerValue(PrefKey.lastIdCourse, 0);
        (new Course(this)).getNewCourseNotifyData(lastId);
    }

    private void checkForNewTeacher() {

        (new Teacher(this)).getNotifyData();
    }

    private void checkForStartDateNotifyData() {
        if (!(Pref.getStringValue(PrefKey.startNotifyDate, "").equals(DateCreator.todayDate())))
            (new Notify(this)).getStartNotifyData(Pref.getStringValue(PrefKey.apiCode, "a"), DateCreator.futureDate_day(1));
    }

    private void checkForWeakNotifyData() {
        if (!(Pref.getStringValue(PrefKey.weakNotifyDate, "").equals(DateCreator.todayDate())))
            (new Notify(this)).getWeakNotifyData(Pref.getStringValue(PrefKey.apiCode, "a"));
    }

    void notification(String title, String message, Intent intent) {
        try {

            NotificationCompat.Builder builder = (NotificationCompat.Builder) new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.noti)
                    .setColor(ContextCompat.getColor(this, R.color.blue_tel))
                    .setTicker("دوره یاب")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setGroup("CourseFinderGroupNotify")
                    .setGroupSummary(true)
                    .setLights(Color.argb(1, 0, 50, 100), 1000, 1000)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true);

            if (intent != null)
                builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 0));

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
            this.stopSelf();
        } catch (Exception ignore) {
        }
    }

    @Override
    public void messageNotifyData(ArrayList<StNotifyData> data) {

        if (!(data == null || data.size() == 0 || data.get(0).number == 0)) {
            notification("دوره یاب", "شما " + data.get(0).number + " پیام جدید دارید", new Intent(this, ActivitySmsBox.class));
            Pref.saveIntegerValue(PrefKey.lastIdMessage, data.get(0).lastId);
        }
    }

    @Override
    public void SabtenamNotifyData(ArrayList<StNotifyData> data) {

        if (!(data == null || data.size() == 0 || data.get(0).empty == 1)) {
            notification("دوره یاب", data.size() + " محصل جدید درانتظار تایید", new Intent(this, ActivityTeacherCoursesList.class));
            Pref.saveIntegerValue(PrefKey.lastIdSabtenam, data.get(data.size() - 1).lastId);
        }
    }

    @Override
    public void newCourseNotifyData(ArrayList<StNotifyData> data) {
        if (!(data == null || data.size() == 0 || data.get(0).empty == 1)) {
            notification("دوره یاب", data.size() + " دوره جدید در دوره یاب ثبت شد", null);
            Pref.saveIntegerValue(PrefKey.lastIdCourse, data.get(data.size() - 1).lastId);
        }
    }

    @Override
    public void newTeacherNotifyData(ArrayList<StNotifyData> data) {
        if (data == null || data.size() == 0 || data.get(0).empty == 1)
            return;
        int counter = 0;
        String[] localData = (Pref.getStringValue(PrefKey.lastTeachers, "")).split("%");
        if (localData.length > 0 && (Pref.getStringValue(PrefKey.lastDate, "")).equals(DateCreator.todayDate())) {
            for (int i = 0; i < data.size(); i++) {
                boolean equal = false;
                for (int j = 0; j < localData.length; j++) {
                    if ((data.get(i).apiCode).equals(localData[j]))
                        equal = true;
                }
                if (!equal) {
                    counter++;
                    Pref.saveStringValue(PrefKey.lastTeachers, (Pref.getStringValue(PrefKey.lastTeachers, "")) + data.get(i).apiCode + "%");
                }
            }
        } else {
            Pref.removeValue(PrefKey.lastTeachers);
            for (int i = 0; i < data.size(); i++)
                Pref.saveStringValue(PrefKey.lastTeachers, (Pref.getStringValue(PrefKey.lastTeachers, "")) + data.get(i).apiCode + "%");
            Pref.saveStringValue(PrefKey.lastDate, DateCreator.todayDate());
            counter = data.size();
        }

        if (counter > 0)
            notification("دوره یاب", counter + " آموزشگاه جدید در دوره یاب ثبت شد", null);


    }

    @Override
    public void onReceiveWeakNotifyData(ArrayList<StNotifyData> res) {

        if (res != null && res.size() != 0 && res.get(0).empty != 1) {
            String message = "";
            for (int i = 0; i < res.size(); i++) {
                if (checkHoldingDay(res.get(i).days))
                    message = res.get(i).name + " , " + message;
            }
            if (message.isEmpty())
                return;
            message = G.myTrim(message.trim(), ',');
            Pref.saveStringValue(PrefKey.weakNotifyDate, DateCreator.todayDate());
            notification("کلاس امروز رو یادت نره!", message, null);

        }


    }

    private boolean checkHoldingDay(String days) {

        String[] courseDays = days.split("-");
        for (String courseDay : courseDays) {
            if (courseDay.equals(currentDayName()))
                return true;
        }
        return false;
    }

    private String currentDayName() {
        try {
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            String day = (simpleDateFormat.format(now)).toLowerCase();
            switch (day) {
                case "saturday":
                    return "شنبه";
                case "sunday":
                    return "یکشنبه";
                case "monday":
                    return "دوشنبه";
                case "tuesday":
                    return "سه شنبه";
                case "wednesday":
                    return "چهار شنبه";
                case "thursday":
                    return "پنجشنبه";
                case "friday":
                    return "جمعه";
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void onReceiveStartDateNotifyData(ArrayList<StNotifyData> res) {

        if (res != null && res.size() != 0 && res.get(0).empty != 1) {
            String message = "";
            for (int i = 0; i < res.size(); i++)
                message = res.get(i).name + " , " + message;
            message = G.myTrim(message.trim(), ',');
            Pref.saveStringValue(PrefKey.startNotifyDate, DateCreator.todayDate());
            notification("یادآور فرارسیدن تاریخ برگزاری دوره ها", message, null);
        }
    }

    @Override
    public void responseForMadrak(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {

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
    public void onReceiveData(ArrayList<StCourse> data, int listId) {

    }

    @Override
    public void DataForHomeLists(ArrayList<StCustomCourseListHome> data) {

    }

    @Override
    public void DataForCustomCourseListHome(ArrayList<StCustomCourseListHome> data) {

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

    @Override
    public void onReceiveDataFromNotify(ArrayList<StNotifyData> res) {

    }

    @Override
    public void onReceiveFlagFromNotify(ArrayList<ResponseOfServer> res) {

    }

    @Override
    public void sendMessageFromNotify(String message) {

    }
}


