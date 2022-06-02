package kr.ac.hs.beet;

public class Customer {
    public static final String TABLE_NAME = "customer";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HEAD = "head";
    public static final String COLUMN_BODY = "body";

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnHead() {
        return COLUMN_HEAD;
    }

    public static String getColumnBody() {
        return COLUMN_BODY;
    }

}
