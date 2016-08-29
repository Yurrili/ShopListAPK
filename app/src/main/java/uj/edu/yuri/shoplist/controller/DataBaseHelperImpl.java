package uj.edu.yuri.shoplist.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import uj.edu.yuri.shoplist.model.Item;
import uj.edu.yuri.shoplist.model.ShoppingList;


/**
 * Created by Yuri on 16.08.2016.
 */
public class DataBaseHelperImpl extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingListsAPK.db";

    /**
     * @param context
     */
    public DataBaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Entries.getSQL_CREATE_ENTRIES_LISTITEM());
        Log.d("DB", "created table List item");
        sqLiteDatabase.execSQL(Entries.getSQL_CREATE_ENTRIES_LIST());
        Log.d("DB", "created table List");

    }

    /**
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("DB", "UPGRADE");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Entries.ListItem.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Entries.List.TABLE_NAME);
    }

    /**
     * @param  title
     */
    public ShoppingList insertList(String title) {
        Log.d("DB", "insert Shopping List");
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Timestamp tmp = new Timestamp(Calendar.getInstance().getTime().getTime());
        values.put(Entries.List.COLUMN_TITLE, title);
        values.put(Entries.List.COLUMN_TIMESTAMP, tmp.toString());
        values.put(Entries.List.COLUMN_ARCHIVED, 0);
        long id = dba.insert(Entries.List.TABLE_NAME, null, values);

        dba.close();
        return new ShoppingList(id, title, tmp);
    }

    public Item insertItem(String title, long id_parent) {
        Log.d("DB", "insert item");
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values = new ContentValues();
        values.put(Entries.ListItem.COLUMN_TITLE, title);
        values.put(Entries.ListItem.COLUMN_LIST_ID, id_parent);
        values.put(Entries.ListItem.COLUMN_DONE, 0);
        long id = dba.insert(Entries.ListItem.TABLE_NAME, null, values);

        dba.close();

        return new Item(id,title,false);

    }

    public void checkItem(Item item){
        Log.d("DB", "update List" + item.getId());
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Entries.ListItem.COLUMN_DONE, item.isDone());
        dba.update(Entries.ListItem.TABLE_NAME, values, Entries.ListItem._ID + "='" + item.getId() + "'", null);
        dba.close();
    }

    /**
     * @param list object ready to remove, removing every connected item
     */
    public void removeList(ShoppingList list) {
        Log.d("DB", "remove List" + list.getId());
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.ListItem.TABLE_NAME, Entries.ListItem.COLUMN_LIST_ID + "='" + list.getId() + "'", null);
        dba.delete(Entries.List.TABLE_NAME, Entries.List._ID + "='" + list.getId() + "'", null);
        dba.close();
    }

    /**
     * @param item ready to remove
     */
    public void removeItem(Item item) {
        Log.d("DB", "remove List item" + item.getId());
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.ListItem.TABLE_NAME, Entries.ListItem._ID + "='" + item.getId() + "'", null);
        dba.close();
    }

    /**
     * @param list to updated (only header)
     */
    public void updateList(ShoppingList list) {
        Log.d("DB", "update List" + list.getId());
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Entries.List.COLUMN_TITLE, list.getTitle());
        dba.update(Entries.List.TABLE_NAME, values, Entries.List._ID + "='" + list.getId() + "'", null);
        dba.close();
    }

    public void archiveList(ShoppingList list) {
        Log.d("DB", "update List" + list.getId());
        SQLiteDatabase dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Entries.List.COLUMN_ARCHIVED, 1);
        dba.update(Entries.List.TABLE_NAME, values, Entries.List._ID + "='" + list.getId() + "'", null);
        dba.close();
    }

    public void unarchiveList(ShoppingList list) {
        Log.d("DB", "update List" + list.getId());
        SQLiteDatabase dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Entries.List.COLUMN_ARCHIVED, 0);
        dba.update(Entries.List.TABLE_NAME, values, Entries.List._ID + "='" + list.getId() + "'", null);
        dba.close();
    }

    /**
     * @param item ready to update
     */
    public void updateListItemsBody(Item item) {
        Log.d("DB", "update ShoppingList" + item.getId());
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Entries.ListItem.COLUMN_TITLE, item.getBody());
        dba.update(Entries.ListItem.TABLE_NAME, values, Entries.ListItem._ID + "='" + item.getId() + "'", null);
        dba.close();
    }



    /**
     * @param list header of the list
     * @return object ShoppingList filled with items from db
     */
    public ShoppingList getListElements(ShoppingList list) {
        Log.d("DB", "get List" + list.getId());
        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor c = dba.query(Entries.ListItem.TABLE_NAME,
                Entries.selectAllListsItems,
                Entries.ListItem.COLUMN_LIST_ID + "='" + list.getId() + "'", null,
                null, null, null);

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add( new Item(c.getLong(0), c.getString(1), (c.getInt(2) == 1)));
            }
            c.close();
        }

        dba.close();
        return list;
    }


    public ShoppingList getList(long id){

        Log.d("DB", "get Lists");
        SQLiteDatabase dba = this.getReadableDatabase();
        ShoppingList temp = new ShoppingList("");
        Cursor c = dba.query(Entries.List.TABLE_NAME,
                Entries.selectAllList,
                Entries.List.COLUMN_ARCHIVED + "=? AND " + Entries.List._ID + "=?", new String[]{String.valueOf(0), String.valueOf(id)}, null, null, Entries.List.COLUMN_TIMESTAMP + " DESC");

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                temp = new ShoppingList(c.getInt(0), c.getString(1), new Timestamp(c.getLong(3)));
            }
            c.close();
        }

        dba.close();
        return temp;
    }

    /**
     * @return list of shopping lists
     */
    public ArrayList<ShoppingList> getLists() {
        ArrayList<ShoppingList> list = new ArrayList<>();
        Log.d("DB", "get Lists");
        SQLiteDatabase dba = this.getReadableDatabase();
        ShoppingList temp;
        Cursor c = dba.query(Entries.List.TABLE_NAME,
                Entries.selectAllList,
                Entries.List.COLUMN_ARCHIVED + "=?", new String[]{String.valueOf(0)}, null, null, Entries.List.COLUMN_TIMESTAMP + " DESC");

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                temp = new ShoppingList(c.getInt(0), c.getString(1), new Timestamp(c.getLong(3)));
                temp.setQuantity((int) DatabaseUtils.queryNumEntries(dba, Entries.ListItem.TABLE_NAME,
                        Entries.ListItem.COLUMN_LIST_ID + "=?", new String[]{String.valueOf(c.getInt(0))}));
                list.add(temp);
            }
            c.close();
        }

        dba.close();
        Log.d("DB", "Lists:" + list.size());
        return list;
    }


}
