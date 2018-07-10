package ir.mahoorsoft.app.cityneed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

import ir.mahoorsoft.app.cityneed.model.api.ApiClient;


public class G extends Application {

    public static String MID = "18e4d686-6592-11e8-803c-005056a205be";
    public static String VN = "1.0.5";
    public static String appLink = "http://www.mahoorsoft.ir/%D8%A7%D8%AE%D8%A8%D8%A7%D8%B1/ID/12/%D8%A7%D9%86%D8%AA%D8%B4%D8%A7%D8%B1-%D8%A7%D9%BE%D9%84%DB%8C%DA%A9%DB%8C%D8%B4%D9%86-%D8%AF%D9%88%D8%B1%D9%87-%DB%8C%D8%A7%D8%A8";
    public static AppCompatActivity activity;
    public static Context context;
    public static SharedPreferences preferences;
    public static String Name;
    public static int INTERVAL_CHECK_PM = 300000;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = context.getSharedPreferences(Name, MODE_PRIVATE);

    }

    public static String myTrim(String string, char subString) {
        int count = string.length();
        int len = count;
        int st = 0;

        while ((st < len) && (string.charAt(st) == subString)) {
            char s = string.charAt(st);
            st++;
        }
        while ((st < len) && (string.charAt(len - 1) == subString)) {
            len--;
        }
        return ((st > 0) || (len < count)) ? string.substring(st, len) : string;
    }

    public static int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static void disableShiftModeNavigation(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void makeNotification(Context context, Intent resultIntent, int id, String title, String text) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification noti;
        Notification.Builder builder = new Notification.Builder(context);
        noti = builder/*.setOngoing(true)*/
                .setSmallIcon(context.getResources().getIdentifier("ic_launcher", "mipmap", context.getPackageName()))
                .setAutoCancel(false)
                //.setLargeIcon(bitmap)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setContentText(text)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        NotificationManager notificationManager = (NotificationManager)
                activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, noti);

    }


    public static void animatingForGone(final View view, float firstAlpha, float lastAlpha, float translationYValue) {
        view.setAlpha(firstAlpha);
        view.animate()
                .translationY(translationYValue)
                .alpha(lastAlpha)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                });
    }

    public static void animatingForVisible(final View view, float firstAlpha, float lastAlpha, float translationYValue) {
        view.setAlpha(firstAlpha);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .translationY(translationYValue)
                .alpha(lastAlpha)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                });
    }

}