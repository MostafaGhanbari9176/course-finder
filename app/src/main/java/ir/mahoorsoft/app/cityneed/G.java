package ir.mahoorsoft.app.cityneed;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class G extends Application {

    public static AppCompatActivity activity;
    public static Context context;
    public static SharedPreferences preferences;
    public static String Name ;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = context.getSharedPreferences(Name, MODE_PRIVATE);

    }

}
