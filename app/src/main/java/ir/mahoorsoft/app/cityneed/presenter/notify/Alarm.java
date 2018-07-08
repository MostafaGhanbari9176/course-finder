package ir.mahoorsoft.app.cityneed.presenter.notify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ir.mahoorsoft.app.cityneed.G;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by M-gh on 18-Nov-17.
 */

public class Alarm {


    public void setAlarm(Context context) {
        Intent intent = new Intent(context, ServiceNotification.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                , G.INTERVAL_CHECK_PM, pendingIntent);

    }
}
