package ir.mahoorsoft.app.cityneed.presenter.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;
import ir.mahoorsoft.app.cityneed.model.struct.ResponseOfServer;
import ir.mahoorsoft.app.cityneed.model.struct.StBuy;
import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomTeacherListHome;
import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;
import ir.mahoorsoft.app.cityneed.model.struct.StSmsBox;
import ir.mahoorsoft.app.cityneed.model.struct.StSubscribe;
import ir.mahoorsoft.app.cityneed.model.struct.StTeacher;
import ir.mahoorsoft.app.cityneed.model.tables.Notify;
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.Subscribe;
import ir.mahoorsoft.app.cityneed.model.tables.course.Course;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;
import ir.mahoorsoft.app.cityneed.model.tables.teacher.Teacher;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.ActivityTeacherCoursesList;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.activity_subscribe.ActivitySubscribe;
import ir.mahoorsoft.app.cityneed.view.date.DateCreator;

/**
 * Created by M-gh on 14-Aug-17.
 */

public class ServiceNotification extends Service implements Sabtenam.OnSabtenamListener, SmsBox.OnSmsBoxResponseListener, Teacher.OnTeacherListener, Course.OnCourseLitener, Notify.OnNotifyResponseListener, Subscribe.OnSubscribeListener


{


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // checkForNewCourse();
        checkForNewMessage();
        checkForNewStudent();
        //checkForNewTeacher();
        checkForStartDateNotifyData();
        checkForWeakNotifyData();
        checkUserBuy();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void checkUserBuy() {
        (new Subscribe(this)).checkUserBuy(Pref.getStringValue(PrefKey.apiCode, ""));
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
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setColor(ContextCompat.getColor(this, R.color.blue_tel))
                    .setTicker("???????? ??????")
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
    public void onRecieveBuyNotifyData(ArrayList<StNotifyData> data) {

        if (data != null && data.size() != 0) {
            String message = getBuyDateNotifyMessage(data.get(0).endBuyDate);
            if (message.length() > 0 && ! (data.get(0).endBuyDate).equals("0000-00-00"))
                notification("???????? ??????", message, new Intent(this, ActivitySubscribe.class));
            if((data.get(0).endBuyDate).compareTo(DateCreator.futureDate_day(7)) > 0){
                Pref.saveBollValue(PrefKey.endBuyNews2, false);
                Pref.saveBollValue(PrefKey.endBuyNews3, false);
                Pref.saveBollValue(PrefKey.endBuyNews4, false);
            }
        }
    }

    private String getBuyDateNotifyMessage(String date) {
        String todayDate = DateCreator.todayDate();
        if (todayDate.compareTo(date) > 0 && !Pref.getBollValue(PrefKey.endBuyNews4, false)) {
            Pref.saveBollValue(PrefKey.endBuyNews4, true);
            return "?????? ?????? ???????????? ?????????? ????????????.???? ?????????? ?????????? ???????????? ???????? ???? ?? ???????????????? ?????? ???????? ?????????? ???????? ???????????? ???????????? ??????.???????? ?????????? ???? ???????? ???????????? ????????????.";
        }
        if (todayDate.equals(date) && !Pref.getBollValue(PrefKey.endBuyNews3, false)) {
            Pref.saveBollValue(PrefKey.endBuyNews3, true);
            Pref.saveBollValue(PrefKey.endBuyNews4, true);
            return "???????????? ?????? ?????????? ???? ?????????? ??????????.???? ?????????? ?????????? ???????????? ???????? ???? ?? ???????????????? ?????? ???????? ?????????? ???????? ???????????? ???????????? ??????.???????? ?????????? ???? ???????? ???????????? ????????????.";
        }
        for (int i = 1; i < 6; i++) {
            String nextDay = DateCreator.futureDate_day(i);
            if (nextDay.equals(date) && !Pref.getBollValue(PrefKey.endBuyNews2, false)) {
                Pref.saveBollValue(PrefKey.endBuyNews2, true);
                Pref.saveBollValue(PrefKey.endBuyNews4, true);
                return "???????????? ?????? " + i + " ?????? ???????? ???? ?????????? ??????????.???? ?????????? ?????????? ???????????? ???????? ???? ?? ???????????????? ?????? ???????? ?????????? ???????? ???????????? ???????????? ??????.";
            }
        }
        return "";
    }

    @Override
    public void messageNotifyData(ArrayList<StNotifyData> data) {

        if (!(data == null || data.size() == 0 || data.get(0).number == 0)) {
            notification("???????? ??????", "?????? " + data.get(0).number + " ???????? ???????? ??????????", new Intent(this, ActivitySmsBox.class));
            Pref.saveIntegerValue(PrefKey.lastIdMessage, data.get(0).lastId);
        }
    }

    @Override
    public void SabtenamNotifyData(ArrayList<StNotifyData> data) {

        if (!(data == null || data.size() == 0 || data.get(0).empty == 1)) {
            notification("???????? ??????", data.size() + " ???????? ???????? ???????????????? ??????????", new Intent(this, ActivityTeacherCoursesList.class));
            Pref.saveIntegerValue(PrefKey.lastIdSabtenam, data.get(data.size() - 1).lastId);
        }
    }

    @Override
    public void newCourseNotifyData(ArrayList<StNotifyData> data) {
        if (!(data == null || data.size() == 0 || data.get(0).empty == 1)) {
            notification("???????? ??????", data.size() + " ???????? ???????? ???? ???????? ?????? ?????? ????", null);
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
            notification("???????? ??????", counter + " ???????????????? ???????? ???? ???????? ?????? ?????? ????", null);


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
            notification("???????? ?????????? ???? ???????? ??????!", message, null);

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
                    return "????????";
                case "sunday":
                    return "????????????";
                case "monday":
                    return "????????????";
                case "tuesday":
                    return "???? ????????";
                case "wednesday":
                    return "???????? ????????";
                case "thursday":
                    return "??????????????";
                case "friday":
                    return "????????";
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
            notification("???????????? ???????????????? ?????????? ?????????????? ???????? ????", message, null);
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
    public void DataForHomeLists(ArrayList<StCustomCourseListHome> data, int groupId) {

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
    public void onReceiveSubscribeList(ArrayList<StSubscribe> data) {

    }

    @Override
    public void onReceiveUserBuy(ArrayList<StBuy> data) {

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


