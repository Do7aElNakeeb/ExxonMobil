package com.exxonmobil.mobapp.app;

/**
 * Created by Nakeeb PC on 8/2/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.exxonmobil.mobapp.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;

public class MarkerDataSource {
    SQLiteHandler dbhelper;
    SQLiteDatabase db;

    String[] cols = {SQLiteHandler.NAME, SQLiteHandler.ADDRESS, SQLiteHandler.REGION, SQLiteHandler.CITY,
            SQLiteHandler.LATITUDE, SQLiteHandler.LONGITUDE, SQLiteHandler.MOG80, SQLiteHandler.MOG92,
            SQLiteHandler.MOG95, SQLiteHandler.Diesel, SQLiteHandler.MOBILMART, SQLiteHandler.ONTHERUN,
            SQLiteHandler.THEWAYTOGO, SQLiteHandler.CARWASH, SQLiteHandler.Lubricants, SQLiteHandler.PHONE};

    public MarkerDataSource(Context c) {
        dbhelper = new SQLiteHandler(c);
    }

    public void open() throws SQLException{
        db = dbhelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    /**
     * Storing user details in database
     * */
    public void addStation(MarkerObj station) {

        ContentValues values = new ContentValues();
        values.put(SQLiteHandler.NAME, station.getName());
        values.put(SQLiteHandler.ADDRESS, station.getAddress());
        values.put(SQLiteHandler.REGION, station.getRegion());
        values.put(SQLiteHandler.CITY, station.getCity());
        values.put(SQLiteHandler.LATITUDE, station.getLatitude());
        values.put(SQLiteHandler.LONGITUDE, station.getLongitude());
        values.put(SQLiteHandler.MOG80, station.getMOG80());
        values.put(SQLiteHandler.MOG92, station.getMOG92());
        values.put(SQLiteHandler.MOG95, station.getMOG95());
        values.put(SQLiteHandler.Diesel, station.getDiesel());
        values.put(SQLiteHandler.MOBILMART, station.getMobilMart());
        values.put(SQLiteHandler.ONTHERUN, station.getOnTheRun());
        values.put(SQLiteHandler.THEWAYTOGO, station.getTheWayToGo());
        values.put(SQLiteHandler.CARWASH, station.getCarWash());
        values.put(SQLiteHandler.Lubricants, station.getLubricants());
        values.put(SQLiteHandler.PHONE, station.getPhone());

        Log.d("TAG", "New station inserted into sqlite: ");

        // Inserting Row
        db.insert(SQLiteHandler.TABLE_NAME, null, values);


        Log.d("TAG", "New station inserted into sqlite: ");
    }

    public List<MarkerObj> getStationsDetails(String args){
        List<MarkerObj> markers = new ArrayList<MarkerObj>();

        Cursor cursor = db.query(SQLiteHandler.TABLE_NAME, cols, args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MarkerObj m = cursorToMarker(cursor);
            markers.add(m);
            cursor.moveToNext();
        }
        cursor.close();

        return markers;
    }

    public void deleteMarker(MarkerObj m){
        db.delete(SQLiteHandler.TABLE_NAME, SQLiteHandler.ADDRESS + " = '" + m.getAddress() + "'", null);
    }

    public void deleteMarkers(){
        db.delete(SQLiteHandler.TABLE_NAME, null, null);
    }


    private MarkerObj cursorToMarker(Cursor cursor) {
        MarkerObj m = new MarkerObj();
        m.setName(cursor.getString(0));
        m.setAddress(cursor.getString(1));
        m.setRegion(cursor.getString(2));
        m.setCity(cursor.getString(3));
        m.setLatitude(cursor.getString(4));
        m.setLongitude(cursor.getString(5));
        m.setMOG80(cursor.getString(6));
        m.setMOG92(cursor.getString(7));
        m.setMOG95(cursor.getString(8));
        m.setDiesel(cursor.getString(9));
        m.setMobilMart(cursor.getString(10));
        m.setOnTheRun(cursor.getString(11));
        m.setTheWayToGo(cursor.getString(12));
        m.setCarWash(cursor.getString(13));
        m.setLubricants(cursor.getString(14));
        m.setPhone(cursor.getString(15));

        return m;
    }
}
