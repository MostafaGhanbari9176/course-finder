package ir.mahoorsoft.app.cityneed.model.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by M-gh on 20-Aug-18.
 */

public class DBCourseListHome extends SQLiteOpenHelper {

    private static final String databaseName = "DBCourseListHome";
    private static final String tableName = "home_list";
    private String groupSubject = "message";
    private String groupSubjectColumn = "message";
    private String groupSubjectColumn = "message";

    public DBCourseListHome(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + tableName + "( " + columnName + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE IF EXIST" + tableName);
    }

    public void saveSmsText(String smsText) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, smsText);
        writer.insert(tableName, null, contentValues);
        writer.close();
    }

    public void saveSmsText(ArrayList<String> smsTexts) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0 ; i<smsTexts.size(); i++) {
            contentValues.put(columnName, smsTexts.get(i));
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public void removeSmsText(String smsText) {
        SQLiteDatabase writer = this.getWritableDatabase();
        writer.execSQL(" DELETE FROM " + tableName + " WHERE " + columnName + " = '" + smsText + "' ");
        writer.close();
    }

    public ArrayList<String> getSmsText() {
        ArrayList<String> datas = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName, null);
        if (cursor.moveToFirst()) {
            do {

                String data = cursor.getString(cursor.getColumnIndex(columnName));
                datas.add(data);

            } while (cursor.moveToNext());
        }

        return datas;
    }
}
