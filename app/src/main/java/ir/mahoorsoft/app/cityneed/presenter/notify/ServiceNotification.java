package ir.mahoorsoft.app.cityneed.presenter.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

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
import ir.mahoorsoft.app.cityneed.model.tables.SmsBox;
import ir.mahoorsoft.app.cityneed.model.tables.course.Course;
import ir.mahoorsoft.app.cityneed.model.tables.sabtenam.Sabtenam;
import ir.mahoorsoft.app.cityneed.model.tables.teacher.Teacher;
import ir.mahoorsoft.app.cityneed.view.activity_main.ActivityMain;
import ir.mahoorsoft.app.cityneed.view.activity_profile.fragment_profile_amozeshgah.ActivityTeacherCoursesList;
import ir.mahoorsoft.app.cityneed.view.activity_sms_box.ActivitySmsBox;
import ir.mahoorsoft.app.cityneed.view.date.DateCreator;

/**
 * Created by M-gh on 14-Aug-17.
 */

public class ServiceNotification extends Service implements Sabtenam.OnSabtenamListener, SmsBox.OnSmsBoxResponseListener {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        checkForNewMessage();
        checkForNewStudent();


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

    void notification(String title, String message, Intent intent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder builder = (NotificationCompat.Builder) new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.noti)
                    .setColor(ContextCompat.getColor(this, R.color.blue_tel))
                    .setTicker("دوره یاب")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setGroup("CourseFinderGroupNotify")
                    .setGroupSummary(true)
                    .setLights(Color.argb(1, 0, 50, 100), 1000, 1000)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true);
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
    public void onReceiveFlag(ArrayList<ResponseOfServer> res) {

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


