package ir.mahoorsoft.app.cityneed.model.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by RCC1 on 7/7/2018.
 */

public class DBLastId extends SQLiteOpenHelper {

    private static final String databaseName = "DBLastId";
    private static final String tableName = "notify";
    private String columnName2 = "last_id";
    private String columnName1 = "sign_text";

    public DBLastId(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + tableName + "( " + columnName1 + " TEXT , " + columnName2 + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE IF EXIST" + tableName);
    }

    public void insertDBLastIdFirstData(ArrayList<String> signText) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < signText.size(); i++) {
            contentValues.put(columnName1, signText.get(i));
            contentValues.put(columnName2, "0");
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public String getLastId(String signText) {
        String data = "";
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + columnName1 + " = '" + signText + "' ", null);
        if (cursor.moveToFirst()) {
            do {

                data = cursor.getString(cursor.getColumnIndex(columnName2));

            } while (cursor.moveToNext());
        }

        return data;
    }

    public void updateLastId(String signText, String lastId) {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();

        sqlWrite.execSQL("UPDATE " + tableName + " SET " + columnName2 + " = '" + lastId + "' WHERE " + columnName1 + " = '" + signText + "'");
        sqlWrite.close();

    }
}
