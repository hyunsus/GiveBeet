package kr.ac.hs.beet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "GiveBeet.db";

    private static final String SQL_CREATE_STORAGE =
            "CREATE TABLE " + Storage.TABLE_NAME + " (" +
                    Storage.COLUMN_ITEM_NAME + " TEXT PRIMARY KEY," +
                    Storage.COLUMN_IMAGE + " TEXT, " +
                    Storage.COLUMN_CATEGORY + " TEXT) ";

    private static final String SQL_DELETE_STORAGE =
            "DROP TABLE IF EXISTS " + Storage.TABLE_NAME;


    private static final String SQL_CREATE_CUSTOMER =
            "CREATE TABLE " + Customer.TABLE_NAME + " (" +
                    Customer.COLUMN_ID + " TEXT PRIMARY KEY," +
                    Customer.COLUMN_HEAD + " TEXT, " +
                    Customer.COLUMN_BODY + " TEXT)";

    private static final String SQL_DELETE_CUSTOMER =
            "DROP TABLE IF EXISTS " + Customer.TABLE_NAME;

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STORAGE);
        db.execSQL(SQL_CREATE_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STORAGE);
        db.execSQL(SQL_CREATE_CUSTOMER);
        onCreate(db);
    }

}
