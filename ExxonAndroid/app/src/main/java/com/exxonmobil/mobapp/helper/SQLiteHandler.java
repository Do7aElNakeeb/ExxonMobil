package com.exxonmobil.mobapp.helper;

/**
 * Created by Nakeeb PC on 7/23/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "exxonmobil.db";

    // Login table name
    public static final String TABLE_NAME = "stations";

    // Login Table Columns names
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String REGION = "region";
    public static final String CITY = "city";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MOG80 = "MOG80";
    public static final String MOG92 = "MOG92";
    public static final String MOG95 = "MOG95";
    public static final String Diesel = "diesel";
    public static final String MOBILMART = "MobilMart";
    public static final String ONTHERUN = "OnTheRun";
    public static final String THEWAYTOGO = "TheWayToGo";
    public static final String CARWASH = "CarWash";
    public static final String Lubricants = "lubricants";
    public static final String PHONE = "phone";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATIONS_TABLE = "CREATE TABLE " + TABLE_NAME + "( id integer primary key autoincrement, "
                + NAME + " TEXT," + ADDRESS + " TEXT,"
                + REGION + " TEXT," + CITY + " TEXT,"
                + LATITUDE + " DOUBLE," + LONGITUDE + " DOUBLE,"
                + MOG80 + " TEXT," + MOG92 + " TEXT,"
                + MOG95 + " TEXT," + Diesel + " TEXT,"
                + MOBILMART + " TEXT," + ONTHERUN + " TEXT,"
                + THEWAYTOGO + " TEXT," + CARWASH + " TEXT,"
                + Lubricants + " TEXT," + PHONE + " TEXT" + ")";
        db.execSQL(CREATE_STATIONS_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addStation(HashMap<String, String> queryValues) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, queryValues.get(NAME));
        values.put(ADDRESS, queryValues.get(ADDRESS));
        values.put(REGION, queryValues.get(REGION));
        values.put(CITY, queryValues.get(CITY));
        values.put(LATITUDE, queryValues.get(LATITUDE));
        values.put(LONGITUDE, queryValues.get(LONGITUDE));
        values.put(MOG80, queryValues.get(MOG80));
        values.put(MOG92, queryValues.get(MOG92));
        values.put(MOG95, queryValues.get(MOG95));
        values.put(Diesel, queryValues.get(Diesel));
        values.put(MOBILMART, queryValues.get(MOBILMART));
        values.put(ONTHERUN, queryValues.get(ONTHERUN));
        values.put(THEWAYTOGO, queryValues.get(THEWAYTOGO));
        values.put(CARWASH, queryValues.get(CARWASH));
        values.put(Lubricants, queryValues.get(Lubricants));
        values.put(PHONE, queryValues.get(PHONE));

        // Inserting Row
        long id = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New station inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<HashMap<String, String>> getStationsDetails() {
        ArrayList<HashMap<String, String>> stations;
        stations = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_NAME, new String[] { NAME, ADDRESS,
                REGION, CITY, LATITUDE, LONGITUDE, MOG80, MOG92, MOG95, Diesel,
                MOBILMART, ONTHERUN, THEWAYTOGO, CARWASH, Lubricants, PHONE }, null, null, null, null, null, null);
        // Move to first row
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", cursor.getString(1));
                map.put("address", cursor.getString(2));
                map.put("region", cursor.getString(3));
                map.put("city", cursor.getString(4));
                map.put("latitude", cursor.getString(5));
                map.put("longitude", cursor.getString(6));
                map.put("MOG80", cursor.getString(7));
                map.put("MOG92", cursor.getString(8));
                map.put("MOG95", cursor.getString(9));
                map.put("diesel", cursor.getString(10));
                map.put("MobilMart", cursor.getString(11));
                map.put("OnTheRun", cursor.getString(12));
                map.put("TheWayToGo", cursor.getString(13));
                map.put("CarWash", cursor.getString(14));
                map.put("lubricants", cursor.getString(15));
                map.put("phone", cursor.getString(16));
                stations.add(map);
            }
            while (cursor.moveToNext());
        }
        db.close();
        // return user
        //Log.d(TAG, "Fetching user from Sqlite: " + stations.toString());

        return stations;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
