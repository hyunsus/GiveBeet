package kr.ac.hs.beet;

public class Storage {
    public static final String TABLE_NAME = "storage";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CATEGORY = "category";

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnItemName() {
        return COLUMN_ITEM_NAME;
    }

    public static String getColumnImage() {
        return COLUMN_IMAGE;
    }

    public static String getColumnCategory() { return COLUMN_CATEGORY; }
}
