package com.example.nixzan_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nixzan-list.db";
    private static final int DATABASE_VERSION = 2;

    // Campos comuns para todas as tabelas
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CHECKED = "checked";
    private static final String COLUMN_CREATED_AT = "createdAt";

    // Construtor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Verifica se a tabela 'items' existe antes de tentar adicionar a coluna
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='items';", null);
        if (cursor != null && cursor.moveToFirst()) {
            // A tabela existe, então adiciona a coluna
            if (oldVersion < 2) {
                db.execSQL("ALTER TABLE items ADD COLUMN " + COLUMN_CREATED_AT + " TEXT;");
            }
        }
        cursor.close();
    }


    public void createTable(SQLiteDatabase db, String tableName) {
        // Adicionando aspas ao redor do nome da tabela, caso tenha espaços ou caracteres especiais
        String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS `" + tableName + "` (" + // Usando aspas backtick
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_CHECKED + " INTEGER, " +
                        COLUMN_CREATED_AT + " TEXT" +
                        ");";
        db.execSQL(CREATE_TABLE);  // Cria a tabela
    }


    public List<ItemModel> getItemsFromTable(String tableName) {
        List<ItemModel> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para pegar todos os itens da tabela específica
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                boolean checked = cursor.getInt(cursor.getColumnIndex("checked")) > 0;
                String createdAt = cursor.getString(cursor.getColumnIndex("createdAt"));

                // Cria o ItemModel e adiciona à lista
                ItemModel item = new ItemModel(id, description, checked, createdAt);
                itemList.add(item);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return itemList;
    }


    public void addItemToTable(String tableName, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(tableName, null, values);
        db.close();
    }


    // Atualizar o estado de um item na tabela
    public void updateChecked(String tableName, int id, boolean checked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CHECKED, checked ? 1 : 0);

        db.update(tableName, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Verifica se a tabela existe antes de tentar excluir
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Deletar um item da tabela
    public void deleteItem(String tableName, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Obter todos os itens de uma tabela
    public List<String> getAllItemsFromTable(String tableName) {
        List<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DESCRIPTION + " FROM " + tableName, null);
        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                items.add(description);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }


    // Obter todos os nomes das tabelas do banco de dados
    public List<String> getAllTableNames() {
        List<String> tableNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
        if (cursor.moveToFirst()) {
            do {
                String tableName = cursor.getString(0);
                if (!tableName.equals("android_metadata") && !tableName.equals("sqlite_sequence")) {
                    tableNames.add(tableName);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tableNames;
    }
}
