package kr.ac.hs.beet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Pocketpet.db";

    //Todo_db
    private static final String SQL_CREATE_TODO =
            "CREATE TABLE " + ToDo.TABLE_NAME + " (" +
                    ToDo.TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ToDo.CONTENT + " TEXT NOT NULL," +
                    ToDo.BEET_COUNT + " INTEGER," +
                    ToDo.WRITEDATE + " TEXT NOT NULL)";

    private static final String SQL_DELETE_TODO =
            "DROP TABLE IF EXISTS " + ToDo.TABLE_NAME;

    //DIARY_db
    private static final String SQL_CREATE_DIARY =
            "CREATE TABLE " + Diary.TABLE_NAME + " (" +
                    Diary.DIARY_ID + " INTEGER PRIMARY KEY," +
                    Diary.SENTENCE + " TEXT," +
                    Diary.DATE + " TEXT," +
                    Diary.IMAGE + " TEXT)";

    private static final String SQL_DELETE_DIARY =
            "DROP TABLE IF EXISTS " + Diary.TABLE_NAME;


    //beet_db
    private static final String SQL_CREATE_BEET =
            "CREATE TABLE " + Beet.TABLE_NAME + " (" +
                    Beet.BEET_ID + " INTEGER PRIMARY KEY," +
                    Beet.BEET_COUNT+ " INTEGER)";

    private static final String SQL_DELETE_BEET =
            "DROP TABLE IF EXISTS " + Beet.TABLE_NAME;


    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TODO);
        sqLiteDatabase.execSQL(SQL_CREATE_DIARY);
        sqLiteDatabase.execSQL(SQL_CREATE_BEET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TODO);
        sqLiteDatabase.execSQL(SQL_DELETE_DIARY);
        sqLiteDatabase.execSQL(SQL_DELETE_BEET);
        onCreate(sqLiteDatabase);
    }
    //TodoSELECT 문 (할 일 목록을 조회)
    public ArrayList<TodoItem> getTodoList(String date) {
        ArrayList<TodoItem> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ToDo.TABLE_NAME + " WHERE " + ToDo.WRITEDATE + " == '" + date + "' ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){ //조회한 데이터가 있을 때 수행
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                TodoItem todoItem = new TodoItem();
                todoItem.setId(id);
                todoItem.setContent(content);
                todoItem.setWriteDate(writeDate);
                todoItems.add(todoItem);
            }
        }
        cursor.close();
        return todoItems;
    }

    //TodoINSERT 문 (할 일 목록을 db에 넣기)
    public void InsertTodo(String _content,  String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " +ToDo.TABLE_NAME+ "(content, writeDate) VALUES('" + _content + "','" + _writeDate + "');");
    }

    //TodoUPDATE 문 (할 일 목록을 수정)
    public void UpdateTodo(String _content, String _writeDate, int todoid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " +ToDo.TABLE_NAME+ " SET content= '" + _content + "', writeDate= '" + _writeDate + "' WHERE id ='" + todoid + "'");
    }

    //TodoDELETE 문 (할 일 목록을 제거)
    public void DeleteTodo(int todoid){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +ToDo.TABLE_NAME+ " WHERE id = " + todoid + "");
    }


}
