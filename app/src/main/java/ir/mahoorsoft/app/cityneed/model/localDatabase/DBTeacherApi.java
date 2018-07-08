package ir.mahoorsoft.app.cityneed.model.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.model.struct.StNotifyData;

/**
 * Created by RCC1 on 7/7/2018.
 */

public class DBTeacherApi extends SQLiteOpenHelper {
    private Context context;
    private static final String databaseName = "DBTeacherApi";
    private static final String tableName = "notify";
    private String columnName2 = "api_code";
    private String columnName1 = "taid_date";

    public DBTeacherApi(Context context) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + tableName + "( " + columnName1 + " TEXT , " + columnName2 + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE IF EXIST" + tableName);
    }

    public void saveApiCode(ArrayList<StNotifyData> apiCode, String todayDate) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < apiCode.size(); i++) {
            contentValues.put(columnName1, todayDate);
            contentValues.put(columnName2, apiCode.get(i).apiCode);
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public void saveApiCode(String apiCode, String todayDate) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName1, todayDate);
        contentValues.put(columnName2, apiCode);
        writer.insert(tableName, null, contentValues);

        writer.close();
    }

    public ArrayList<StNotifyData> getApiCodes() {
        ArrayList<StNotifyData> data = new ArrayList<>();
        SQLiteDatabase sqlRead = this.getReadableDatabase();
        Cursor cursor = sqlRead.rawQuery(" SELECT * FROM " + tableName, null);
        if (cursor.moveToFirst()) {

            do {
                StNotifyData stNotifyData = new StNotifyData();
                stNotifyData.apiCode = cursor.getString(1);
                stNotifyData.name = cursor.getString(0);
                data.add(stNotifyData);
            } while (cursor.moveToNext());
        }

        sqlRead.close();
        return data;
    }

    public void deleteDataBase() {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        context.deleteDatabase(sqlWrite.getPath());
        sqlWrite.close();

    }
}
