package com.example.lucere;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTS = "Products";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_URL = "product_url";
    public static final String COLUMN_TYPE = "product_type";
    public static final String COLUMN_INGREDIENTS = "clean_ingreds";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_INGREDIENTS + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public List<Product> getProductsByIngredient(String ingredient) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String searchPattern = "%'" + ingredient + "'%";

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS +
                        " WHERE " + COLUMN_INGREDIENTS + " LIKE ?",
                new String[]{searchPattern});

        if (cursor.moveToFirst()) {
            do {
                products.add(new Product(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Products", null);
        if(cursor.moveToFirst()) {
            do {
                products.add(new Product(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }
}
