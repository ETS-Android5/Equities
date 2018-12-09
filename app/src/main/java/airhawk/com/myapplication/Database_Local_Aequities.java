package airhawk.com.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Dinkins on 1/12/2018.
 */

public class Database_Local_Aequities extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String TAG = "TAG";
    private static Database_Local_Aequities mInstance = null;
    private static final String DATABASE_NAME = "Database_Local_Aequities";
    private static final String TABLE_AEQUITY_INFO = "TABLE_AEQUITY_INFO";
    private static final String KEY_ID = "KEY_ID";
    public static final String KEY_AEQUITY_SYMBOL = "KEY_AEQUITY_SYMBOL";
    public static final String KEY_AEQUITY_NAME = "KEY_AEQUITY_NAME";
    public static final String KEY_AEQUITY_TYPE = "KEY_AEQUITY_TYPE";
    private Context context;

    public static Database_Local_Aequities getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Database_Local_Aequities(context);
        }
        return mInstance;
    }

    public Database_Local_Aequities(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_EQUITY_INFO  = "CREATE TABLE " + TABLE_AEQUITY_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_AEQUITY_SYMBOL + " TEXT,"
                + KEY_AEQUITY_NAME + " TEXT,"
                + KEY_AEQUITY_TYPE + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE_EQUITY_INFO );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AEQUITY_INFO);
        onCreate(db);
    }

    public long add_equity_info(String symbol,String name,String type,String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AEQUITY_SYMBOL, symbol);
        values.put(KEY_AEQUITY_NAME, name);
        values.put(KEY_AEQUITY_TYPE, type);
        long id = db.insert(TABLE_AEQUITY_INFO, null, values);
        db.close();
        return id;
    }

    public ArrayList getSymbol() {
        ArrayList<String> symbol = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    symbol.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_SYMBOL)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return symbol;
    }



    public ArrayList getstockSymbol() {
        ArrayList<String> symbol = new ArrayList<>();
        String sto ="stock";
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO + " WHERE "+KEY_AEQUITY_TYPE+" LIKE '%stock%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    symbol.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_SYMBOL)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return symbol;
    }

    public ArrayList getcryptoSymbol() {
        ArrayList<String> symbol = new ArrayList<>();
        String sto ="stock";
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO + " WHERE "+KEY_AEQUITY_TYPE+" LIKE '%Crypto%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    symbol.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_SYMBOL)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return symbol;
    }


    public ArrayList getcryptoName() {
        ArrayList<String> symbol = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO + " WHERE "+KEY_AEQUITY_TYPE+" LIKE '%Cryptocurrency%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    symbol.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_NAME)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return symbol;
    }

    public ArrayList getName() {
        ArrayList<String> name = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    name.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_NAME)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    public void deleteDuplicates(){
        String p ="";
        getWritableDatabase().execSQL("DELETE FROM "+TABLE_AEQUITY_INFO+" WHERE KEY_ID NOT IN (SELECT MIN(KEY_ID ) FROM TABLE_AEQUITY_INFO GROUP BY KEY_AEQUITY_NAME)");
    }

    public ArrayList<String> getType() {
        ArrayList<String> type = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    type.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_TYPE)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }

    public String getType(String name) {
        String type = new String();
        try {
            String query = "SELECT DISTINCT * FROM " + TABLE_AEQUITY_INFO+" WHERE KEY_AEQUITY_NAME="+"\""+name+"\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    type=cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_TYPE));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }

    public ArrayList<String> getAllAequities() {
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            String query = "SELECT KEY_AEQUITY_SYMBOL FROM " + TABLE_AEQUITY_INFO;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    tableNames.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_SYMBOL)));
                    tableNames.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_NAME)));
                    tableNames.add(cursor.getString(cursor.getColumnIndex(KEY_AEQUITY_TYPE)));
                    cursor.moveToNext();
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableNames;
    }




}
