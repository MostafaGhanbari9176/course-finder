package ir.mahoorsoft.app.cityneed.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M-gh on 13-Mar-18.
 */

public class RandomColor {

    public static int randomColor(Context context) {
        int color = (new Random()).nextInt(8);
        switch (color) {
            case 0:
                return ContextCompat.getColor(context, R.color.blue_ios);
            case 1:
                return ContextCompat.getColor(context, R.color.green_ios);
            case 2:
                return ContextCompat.getColor(context, R.color.orange_ios);
            case 3:
                return ContextCompat.getColor(context, R.color.pink_ios);
            case 4:
                return ContextCompat.getColor(context, R.color.purple_ios);
            case 5:
                return ContextCompat.getColor(context, R.color.tealblue_ios);
            case 6:
                return ContextCompat.getColor(context, R.color.red_ios);
            case 7:
                return ContextCompat.getColor(context, R.color.yellow_ios);
            default:
                return ContextCompat.getColor(context, R.color.dark_eq);
        }

    }
}
