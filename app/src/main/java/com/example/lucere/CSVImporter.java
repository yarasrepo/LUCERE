package com.example.lucere;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVImporter {

    public static void importCSV(Context context, int resourceId, DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_NAME, columns[0].trim());
                values.put(DatabaseHelper.COLUMN_URL, columns[1].trim());
                values.put(DatabaseHelper.COLUMN_TYPE, columns[2].trim());
                values.put(DatabaseHelper.COLUMN_INGREDIENTS, columns[3].trim());

                db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
            }

            db.close();
            Log.d("CSVImporter", "Data imported successfully!");

        } catch (Exception e) {
            Log.e("CSVImporter", "Error reading CSV file", e);
        }
    }
}
