package ir.mahoorsoft.app.cityneed.model.localDatabase;


import android.content.Context;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;
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

    public static ArrayList<StCustomCourseListHome> getCourseListSubject(Context context, String signText){
        return  (new DBCourseListHome(context)).getData(signText);
    }

    public static ArrayList<StCourse> getCourseList (Context context, String signText){
        return (new DBCourse(context)).getData(signText);
    }

    public static void saveCourseListSubject(Context context, ArrayList<StCustomCourseListHome> data, String signText){
        (new DBCourseListHome(context)).saveData(data, signText);
    }

    public static void saveCourseList(Context context, ArrayList<StCourse> data, String signText){
        (new DBCourse(context)).saveData(data, signText);
    }

    public static void removeCourseDataBAse(Context context){
        (new DBCourse(context)).removeDataBase();
        (new DBCourseListHome(context)).removeDataBase();
    }

    public static void removeCourseData(Context context, String signText){
        (new DBCourse(context)).removeData(signText);
        (new DBCourseListHome(context)).removeData(signText);
    }


}