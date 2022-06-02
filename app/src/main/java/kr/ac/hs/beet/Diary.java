package kr.ac.hs.beet;


public class Diary {
    public static final String TABLE_NAME = "diary";
    public static final String DIARY_ID = "diary_id";
    public static final String SENTENCE = "sentence";
    public static final String DATE= "date";
    public static final String IMAGE = "image";

    private static final String SQL_CREATE_DIARY =
            "CREATE TABLE " + Diary.TABLE_NAME + " (" +
                    Diary.DIARY_ID + " INTEGER PRIMARY KEY," +
                    Diary.SENTENCE + " TEXT," +
                    Diary.DATE + " TEXT," +
                    Diary.IMAGE + " TEXT)";

    private static final String SQL_DELETE_DIARY =
            "DROP TABLE IF EXISTS " + Diary.TABLE_NAME;

}
