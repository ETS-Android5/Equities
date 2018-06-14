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

public class Aequity_Local_DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String TAG = "TAG";
    private static Aequity_Local_DB mInstance = null;
    private static final String DATABASE_NAME = "Aequity_Local_DB";

    // Contacts table name
    private static final String TABLE_AEQUITY_INFO = "Equity_Info";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_AEQUITY_SYMBOL = "KEY_EQUITY_SYMBOL";
    private static final String KEY_AEQUITY_NAME = "KEY_EQUITY_NAME";
    private static final String KEY_AEQUITY_TYPE = "KEY_EQUITY_TYPE";


    public static Aequity_Local_DB getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new Aequity_Local_DB(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */

    public Aequity_Local_DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_EQUITY_INFO  = "CREATE TABLE " + TABLE_AEQUITY_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_AEQUITY_SYMBOL + " TEXT,"
                + KEY_AEQUITY_NAME + " TEXT,"
                + KEY_AEQUITY_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_EQUITY_INFO );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AEQUITY_INFO);

        // Create tables again
        onCreate(db);
    }

    public void add_equity_info(String symbol,String name,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AEQUITY_SYMBOL, symbol);
        values.put(KEY_AEQUITY_NAME, name);
        values.put(KEY_AEQUITY_TYPE, type);
        // Inserting Row
        db.insert(TABLE_AEQUITY_INFO, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<String> get_all_equities() {
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_AEQUITY_INFO, null );
        res.moveToFirst();
        if (res != null)
        {
            while(res.isAfterLast() == false){
                arrayList.add(res.getString(res.getColumnIndex(KEY_AEQUITY_SYMBOL)));
                res.moveToNext();
            }}
        res.close();
        return arrayList;

    }
    public ArrayList<String> getAllFriends(int id) {
        ArrayList<String> friendsNames = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try {
            Cursor res =  sqLiteDatabase.rawQuery( "select * from "+TABLE_AEQUITY_INFO, null );
            while (res.moveToNext()) {
                friendsNames.add(res.getString(res.getColumnIndex(KEY_AEQUITY_SYMBOL)));
            }
        }catch(Exception ex){
            Log.e(TAG,"Erro in geting friends "+ex.toString());
        }
        return friendsNames;
    }


    //Get name based  on equity_symbol
    public String get_equity_name(String equsymbol) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_AEQUITY_INFO + " WHERE " + KEY_AEQUITY_SYMBOL + " =  \"" + equsymbol + "\"";


        Cursor cursor = db.rawQuery(query, null);



        String varaible1="";
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            varaible1 = cursor.getString(cursor.getColumnIndex("KEY_EQUITY_NAME"));

            cursor.close();
            //Log.i(JSONParser.TAG, "I love " + varaible1);

        } else {
            varaible1 = null;
        }
        db.close();
        return varaible1;
    }





    public App_Variables getEquity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_AEQUITY_INFO, new String[] { KEY_ID,
                        KEY_AEQUITY_SYMBOL, KEY_AEQUITY_NAME,KEY_AEQUITY_TYPE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        App_Variables getEquity = new App_Variables(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        return getEquity;
    }

    public List<String> getAllEquities() {
        List<String> tableNames = new ArrayList<>();
        try {
            String query = "SELECT  * FROM " + TABLE_AEQUITY_INFO;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableNames;
    }




}
