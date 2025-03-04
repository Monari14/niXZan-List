package com.example.nixzan_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nixzan_list.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CHECKED = "checked";
    private static final String COLUMN_CREATED_AT = "createdAt";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_CHECKED + " INTEGER, " +
                    COLUMN_CREATED_AT + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_ITEMS + " ADD COLUMN " + COLUMN_CREATED_AT + " TEXT;");
        }
    }

    public List<ItemModel> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ItemModel> items = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id, description, checked, createdAt FROM " + TABLE_ITEMS, null);

        if (cursor.moveToFirst()) {
            do {
                ItemModel item = new ItemModel(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_CHECKED)) == 1,
                        cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public void addItem(String description, boolean checked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_CHECKED, checked ? 1 : 0);
        values.put(COLUMN_CREATED_AT, ItemModel.getCurrentDate());

        db.insert(TABLE_ITEMS, null, values);
    }

    public void updateChecked(int id, boolean checked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CHECKED, checked ? 1 : 0);

        // Atualiza o item na tabela
        db.update(TABLE_ITEMS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
