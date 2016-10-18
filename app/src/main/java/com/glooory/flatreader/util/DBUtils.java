package com.glooory.flatreader.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.glooory.flatreader.constants.Constants;

/**
 * Created by Glooory on 2016/10/18 0018 14:03.
 */

public class DBUtils {
    public static final String CREATE_TABLE_IF_NOT_EXISTS = "create table if not exists %s " +
            "(id integer primary key autoincrement, key text unique, is_read integer)";
    public static final int READ = 1;

    private static DBUtils sDBUtils;
    private SQLiteDatabase mDatabase;

    private DBUtils(Context context){
        mDatabase = new ReadDBHelper(context, Constants.DB_IS_READ_NAME + ".db").getWritableDatabase();
    }

    public static synchronized DBUtils getDB(Context context) {
        if (sDBUtils == null) {
            sDBUtils = new DBUtils(context);
        }
        return sDBUtils;
    }

    public void insertHasRead(String table, String key, int value) {
        Cursor cursor = mDatabase.query(table, null, null, null, null, null, "id asc");
        if (cursor.getCount() > 200 && cursor.moveToNext()) {
            mDatabase.delete(table, "id = ?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))});
        }
        cursor.close();
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", key);
        contentValues.put("is_read", value);
        mDatabase.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public boolean isRead(String table, String key, int value) {
        boolean isRead = false;
        Cursor cursor = mDatabase.query(table, null, "key = ?", new String[]{key}, null, null, null);
        if (cursor.moveToNext() && cursor.getInt(cursor.getColumnIndex("is_read")) == value) {
            isRead = true;
        }
        cursor.close();
        return isRead;
    }

    public class ReadDBHelper extends SQLiteOpenHelper {

        public ReadDBHelper(Context context, String name) {
            super(context, name, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(String.format(CREATE_TABLE_IF_NOT_EXISTS, Constants.RIBAO));
            db.execSQL(String.format(CREATE_TABLE_IF_NOT_EXISTS, Constants.GANK));
            db.execSQL(String.format(CREATE_TABLE_IF_NOT_EXISTS, Constants.ITHOME));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
