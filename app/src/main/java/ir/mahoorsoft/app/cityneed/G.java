package ir.mahoorsoft.app.cityneed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

import ir.mahoorsoft.app.cityneed.model.api.ApiClient;
import ir.mahoorsoft.app.cityneed.view.TypefaceUtil;
import ir.mahoorsoft.app.cityneed.view.dialog.DialogGrouping;


public class G extends Application {

    public static String MID = "";
    public static String VN = BuildConfig.VERSION_NAME;
    public static String appLink = "http://www.mahoorsoft.ir/%D8%A7%D8%AE%D8%A8%D8%A7%D8%B1/ID/12/%D8%A7%D9%86%D8%AA%D8%B4%D8%A7%D8%B1-%D8%A7%D9%BE%D9%84%DB%8C%DA%A9%DB%8C%D8%B4%D9%86-%D8%AF%D9%88%D8%B1%D9%87-%DB%8C%D8%A7%D8%A8";
    public static AppCompatActivity activity;
    public static Context context;
    public static SharedPreferences preferences;
    public static String Name;
    public static int INTERVAL_CHECK_PM = 300000;
    ///////////////////////////////////
    private static final int PBK_ITERATIONS = 1000;
    private static final String PBE_ALGORITHM = "PBEwithSHA256and128BITAES-CBC-BC";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = context.getSharedPreferences(Name, MODE_PRIVATE);

        TypefaceUtil.overrideFont(context, "SERIF", "fonts/Far_Elham.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        try {
            byte[] decryptedData = func(stData("assets4.txt"), data(stData("assets1.txt")), data(stData("assets2.txt")), data(stData("assets3.txt")));
            MID = new String(decryptedData, "UTF-8");

            decryptedData = func(stData("assets24.txt"), data(stData("assets21.txt")), data(stData("assets22.txt")), data(stData("assets23.txt")));
            ApiClient.BASE_URL = ApiClient.serverAddress + new String(decryptedData, "UTF-8");
            ApiClient.BASE_URL = ApiClient.BASE_URL.replace("v1", "v2");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] func(String password, byte[] salt, byte[] iv, byte[] encryptedData) throws
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, PBK_ITERATIONS);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        Key key = secretKeyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher.doFinal(encryptedData);
    }

    private String stData(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream inputStream = this.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null)
                text.append(line);

            reader.close();

            return text.toString();

        } catch (Exception e) {
            return "";
        }
    }

    private byte[] data(String s) {
        String[] stData = (s.split("%"));
        byte[] data = new byte[stData.length];
        for (int i = 0; i < stData.length; i++)
            data[(stData.length - 1) - i] = (byte) (Integer.parseInt(stData[i]));
        return data;
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


    public static int setListItemsAnimation(View[] fadeIn, View[] fadeLeft, int position, int lastPosition) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation;
        if (fadeIn != null) {
            for (View aFadeIn : fadeIn) {
                animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
                aFadeIn.startAnimation(animation);
            }
        }
        if (position > lastPosition && fadeLeft != null) {
            for (View aFadeLeft : fadeLeft) {
                animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                aFadeLeft.startAnimation(animation);
            }
        }
        return position;
    }

}