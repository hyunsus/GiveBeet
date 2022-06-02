package kr.ac.hs.beet;

public class Beet {
    public static final String TABLE_NAME = "beet";  // TABLE_NAME 이 todo 였음!!
    public static final String BEET_ID = "beet_id";
    public static final String BEET_COUNT = "beet_count";

    //beet_db
    private static final String SQL_CREATE_BEET =
            "CREATE TABLE " + Beet.TABLE_NAME + " (" +
                    Beet.BEET_ID + " INTEGER PRIMARY KEY," +
                    Beet.BEET_COUNT+ " INTEGER)";

    private static final String SQL_DELETE_BEET =
            "DROP TABLE IF EXISTS " + Beet.TABLE_NAME;

}
