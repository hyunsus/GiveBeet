package kr.ac.hs.beet;

public class ToDo {
    public static final String TABLE_NAME = "todo";
    public static final String TODO_ID = "id";
    public static final String CONTENT = "content";
    public static final String BEET_COUNT = "beet_count";
    public static final String WRITEDATE= "writeDate";

    //TODO_db
    private static final String SQL_CREATE_TODO =
            "CREATE TABLE " + ToDo.TABLE_NAME + " (" +
                    ToDo.TODO_ID + " INTEGER PRIMARY KEY," +
                    ToDo.CONTENT + " TEXT," +
                    ToDo.WRITEDATE + " TEXT)";

    private static final String SQL_DELETE_TODO =
            "DROP TABLE IF EXISTS " + ToDo.TABLE_NAME;
}
