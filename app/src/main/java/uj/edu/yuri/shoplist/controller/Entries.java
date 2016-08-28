package uj.edu.yuri.shoplist.controller;

import android.provider.BaseColumns;

import lombok.Getter;

/**
 * Created by Yuri on 16.08.2016.
 */

public class Entries {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE = " INTEGER";

    public Entries() {
    }

    @Getter
    private static final String SQL_CREATE_ENTRIES_LISTITEM =
            "CREATE TABLE " + ListItem.TABLE_NAME + " (" +
                    ListItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ListItem.COLUMN_TITLE + TEXT_TYPE + " NOT NULL " + COMMA_SEP +
                    ListItem.COLUMN_DONE + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    ListItem.COLUMN_LIST_ID + INTEGER_TYPE + " NOT NULL " +
                    " )";

    @Getter
    private static final String SQL_CREATE_ENTRIES_LIST =
            "CREATE TABLE " + List.TABLE_NAME + " (" +
                    List._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    List.COLUMN_TITLE + TEXT_TYPE + " NOT NULL " + COMMA_SEP +
                    List.COLUMN_ARCHIVED + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    List.COLUMN_TIMESTAMP + INTEGER_TYPE + " NOT NULL " +
                    " )";

    public static abstract class ListItem implements BaseColumns {
        public static final String TABLE_NAME = "ITEM";
        public static final String COLUMN_TITLE = "BODY";
        public static final String COLUMN_DONE = "DONE";
        public static final String COLUMN_LIST_ID = "LIST_ID";
    }

    public static abstract class List implements BaseColumns {
        public static final String TABLE_NAME = "LIST";
        public static final String COLUMN_TITLE = "TITLE";
        public static final String COLUMN_ARCHIVED = "ARCHIVED";
        public static final String COLUMN_TIMESTAMP = "TIMESTAMP";

    }


    public static final String[] selectAllListsItems = new String[]{ListItem._ID,
            ListItem.COLUMN_TITLE,
            ListItem.COLUMN_DONE,
            ListItem.COLUMN_LIST_ID};

    public static final String[] selectAllList = new String[]{List._ID,
            List.COLUMN_TITLE,
            List.COLUMN_ARCHIVED,
            List.COLUMN_TIMESTAMP};
}
