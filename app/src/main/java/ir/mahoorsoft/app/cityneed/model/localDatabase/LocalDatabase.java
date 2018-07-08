package ir.mahoorsoft.app.cityneed.model.localDatabase;


import android.content.Context;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;

/**
 * Created by RCC1 on 4/28/2018.
 */

public class LocalDatabase {

    public static void addSmsText(Context context, ArrayList<String> smsText) {
        DBSmsText dbSmsText = new DBSmsText(context);
        dbSmsText.saveSmsText(smsText);
    }

    public static void addSmsText(Context context, String smsText) {
        DBSmsText dbSmsText = new DBSmsText(context);
        dbSmsText.saveSmsText(smsText);
    }

    public static ArrayList<String> getSmsText(Context context) {
        DBSmsText dbSmsText = new DBSmsText(context);
        return dbSmsText.getSmsText();
    }

    public static void removeSmsText(Context context, String smsText) {
        DBSmsText dbSmsText = new DBSmsText(context);
        dbSmsText.removeSmsText(smsText);
    }
}