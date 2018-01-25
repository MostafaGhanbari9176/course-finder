package ir.mahoorsoft.app.cityneed;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import ir.mahoorsoft.app.cityneed.view.BlurBuilder;

/**
 * Created by M-gh on 07-Oct-17.
 */

public class G extends Application {

    public static AppCompatActivity activity;
    public static Context context;
    public static SharedPreferences preferences;
    public static String Name;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = context.getSharedPreferences(Name, MODE_PRIVATE);

    }


}
