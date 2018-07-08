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

    public static void insertDBLastIdFirstData(Context context, ArrayList<String> signText) {

        (new DBLastId(context)).insertDBLastIdFirstData(signText);
    }

    public static int getLastId(Context context, String signText) {

        return Integer.parseInt((new DBLastId(context)).getLastId(signText));
    }

    public static void updateLastId(Context context, String signText, String lastId) {

        (new DBLastId(context)).updateLastId(signText, lastId);
    }

    public static void saveApiCodes(Context context, ArrayList<StNotifyData> apiCodes, String todayDate) {

        (new DBTeacherApi(context)).saveApiCode(apiCodes, todayDate);
    }

    public static void saveApiCodes(Context context, String apiCodes, String todayDate) {

        (new DBTeacherApi(context)).saveApiCode(apiCodes, todayDate);
    }

    public static ArrayList<StNotifyData> getApiCodes(Context context) {

        return (new DBTeacherApi(context)).getApiCodes();
    }

    public static void removeApiCodes(Context context) {

        (new DBTeacherApi(context)).deleteDataBase();
    }
}