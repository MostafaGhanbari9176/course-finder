package ir.mahoorsoft.app.cityneed.model.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StCustomCourseListHome;

/**
 * Created by M-gh on 20-Aug-18.
 */

public class DBCourseListHome extends SQLiteOpenHelper {

    private static final String databaseName = "DBCourseListHome";
    private static final String tableName = "home_list";
    private String column1 = "courseListId";
    private String column2 = "groupSubject";
    private String column3 = "empty";
    private Context context;


    public DBCourseListHome(Context context) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " +
                tableName
                + " (" + column1 + " TEXT PRIMARY KEY, " + column2 + " TEXT, " + column3 + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE IF EXIST" + tableName);
    }

    public void saveData(ArrayList<StCustomCourseListHome> data, String signText) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (StCustomCourseListHome d : data ) {
            contentValues.put(column1, signText + data.indexOf(d));
            contentValues.put(column2, d.groupSubject);
            contentValues.put(column3, d.empty);
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public ArrayList<StCustomCourseListHome> getData(String signText) {
        ArrayList<StCustomCourseListHome> dataList = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column1 + " LIKE '%" + signText + "%' ", null);
        if (cursor.moveToFirst()) {
            do {
                StCustomCourseListHome data = new StCustomCourseListHome();
                data.courseListId = cursor.getString(cursor.getColumnIndex(column1));
                data.groupSubject = cursor.getString(cursor.getColumnIndex(column2));
                data.empty = cursor.getInt(cursor.getColumnIndex(column3));
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
