package ir.mahoorsoft.app.cityneed.model.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StCourse;
import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;

/**
 * Created by RCC1 on 8/21/2018.
 */

public class DBCourse extends SQLiteOpenHelper {

    private static final String databaseName = "DBCourse";
    private static final String tableName = "course";
    private String column1 = "courseListId";
    private String column2 = "courseId";
    private String column3 = "courseName";
    private String column4 = "teachId";
    private String column5 = "teachName";
    private String column6 = "startDate";
    private String column7 = "empty";
    private Context context;


    public DBCourse(Context context) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " +
                tableName
                + " (" + column1 + " TEXT , " + column2 + " INTEGER , " + column3 + " TEXT , " + column4 + " TEXT , " + column5 + " TEXT , " + column6 + " TEXT , " + column7 + " INTEGER )");
        db.execSQL("CREATE UNIQUE INDEX pk_index ON " + tableName + "(" + column1 + "," + column2 + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP  TABLE IF EXISTS " + tableName);
    }

    public void saveData(ArrayList<StCourse> data, String signText) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (StCourse d : data ) {
            contentValues.put(column1, signText + d.courseListId);
            contentValues.put(column2, d.id);
            contentValues.put(column3, d.CourseName);
            contentValues.put(column4, d.MasterName);
            contentValues.put(column5, d.idTeacher);
            contentValues.put(column6, d.startDate);
            contentValues.put(column7, d.empty);
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public ArrayList<StCourse> getData(String signText) {
        ArrayList<StCourse> dataList = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column1 + " LIKE '%" + signText + "%' ", null);
        if (cursor.moveToFirst()) {
            do {
                StCourse data = new StCourse();
                data.courseListId = cursor.getString(cursor.getColumnIndex(column1));
                data.id = cursor.getInt(cursor.getColumnIndex(column2));
                data.CourseName = cursor.getString(cursor.getColumnIndex(column3));
                data.MasterName = cursor.getString(cursor.getColumnIndex(column4));
                data.idTeacher = cursor.getString(cursor.getColumnIndex(column5));
                data.startDate = cursor.getString(cursor.getColumnIndex(column6));
                data.empty = cursor.getInt(cursor.getColumnIndex(column7));
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public void removeDataBase(){
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        context.deleteDatabase(sqlWrite.getPath());
        sqlWrite.close();
    }

    public void removeData(String signText) {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        sqlWrite.execSQL(" DELETE FROM " + tableName + " WHERE " + column1 + " LIKE '%" + signText + "%' ");
        sqlWrite.close();
    }
}
