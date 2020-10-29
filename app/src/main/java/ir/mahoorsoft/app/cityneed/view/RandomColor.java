package ir.mahoorsoft.app.cityneed.view;

import android.content.Context;
import androidx.core.content.ContextCompat;

import java.util.Random;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 13-Mar-18.
 */

public class RandomColor {

    public static int randomColor(Context context) {
        int color = (new Random()).nextInt(6);
        switch (color) {
            case 0:
                return ContextCompat.getColor(context, R.color.blue_tel);
            case 1:
                return ContextCompat.getColor(context, R.color.green_tel);
            case 2:
                return ContextCompat.getColor(context, R.color.orange_tel);
            case 3:
                return ContextCompat.getColor(context, R.color.pink_tel);
            case 4:
                return ContextCompat.getColor(context, R.color.purple_tel);
            case 5:
                return ContextCompat.getColor(context, R.color.tealblue_ios);
            default:
                return ContextCompat.getColor(context, R.color.dark_eq);
        }

    }
}
