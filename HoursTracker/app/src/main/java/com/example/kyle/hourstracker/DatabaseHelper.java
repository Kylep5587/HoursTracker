/* ************************************
 *  DatabaseHelper.java                *
 *  Handles creation of database and   *
 *       tables                        *
 *  Created: 11/29/18                  *
 *  Last Updated: 11/28/18             *
 **************************************/

package com.example.kyle.hourstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    private Context context;
    private final int DEFAULTS_SIZE = 3;
    private static final String DATABASE_NAME = "HoursTracker.db";

    // Hours Table Data
    private static final String HOURS_TABLE_NAME = "Hours";
    private static final String hourCOL1 = "ID";
    private static final String hourCOL2 = "hoursDate";
    private static final String hourCOL3 = "startTime";
    private static final String hourCOL4 = "endTime";
    private static final String hourCOL5 = "workedHours";
    private static final String hourCOL6 = "lunch";
    private static final String hourCOL7 = "jobName";
    private final String CREATE_HOURS_TABLE = "" +
            "CREATE TABLE " + HOURS_TABLE_NAME + "(" +
            hourCOL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            hourCOL2 + " TEXT NOT NULL, " +
            hourCOL3 + " TEXT NOT NULL, " +
            hourCOL4 + " TEXT NOT NULL, " +
            hourCOL5 + " INTEGER NOT NULL, " +
            hourCOL6 + " INTEGER DEFAULT '30', " +
            hourCOL7 + " TEXT)";

    // Jobs Table Data
    private static final String JOBS_TABLE_NAME = "Jobs";
    private static final String jobCOL1 = "ID";
    private static final String jobCOL2 = "name";
    private static final String jobCOL3 = "jobActive";
    private final String CREATE_JOBS_TABLE = "" +
            "CREATE TABLE " + JOBS_TABLE_NAME + "(" +
            jobCOL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            jobCOL2 + " TEXT NOT NULL," +
            jobCOL3 + " TEXT NOT NULL)";

    // AppDefaults Table Data
    private static final String DEFAULTS_TABLE_NAME = "AppDefualts";
    private static final String defaultsCOL1 = "ID";
    private static final String defaultsCOL2 = "startTime";
    private static final String defaultsCOL3 = "endTime";
    private static final String defaultsCOL4 = "lunchDuration";
    private final String CREATE_DEFAULTS_TABLE = "" +
            "CREATE TABLE " + DEFAULTS_TABLE_NAME + "(" +
            defaultsCOL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            defaultsCOL2 + " TEXT NOT NULL," +
            defaultsCOL3 + " TEXT NOT NULL," +
            defaultsCOL4 + " TEXT NOT NULL)";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_HOURS_TABLE);
            db.execSQL(CREATE_JOBS_TABLE);
            db.execSQL(CREATE_DEFAULTS_TABLE);

        db.execSQL("INSERT INTO Jobs (name, jobActive) VALUES ('N/A', 'True')");
        db.execSQL("INSERT INTO " + DEFAULTS_TABLE_NAME + " (startTime, endTime, lunchDuration) VALUES ('8:00', '5:00', '30') ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HOURS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JOBS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DEFAULTS_TABLE_NAME);
        onCreate(db);
    }

    /*  Get list of jobs
     ******************************************/
    public List<String> getJobNames() {
        List<String> jobNames = new ArrayList<String>();

        // Get jobs
        String getJobQuery = "SELECT * FROM " + JOBS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getJobQuery, null);

        if (cursor.moveToFirst()) {
            do {
                jobNames.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return jobNames;
    }


    /*  Get total job entries
     ******************************************/
    public long totalJobs() {
        SQLiteDatabase db = this.getReadableDatabase();
        long jobCount = DatabaseUtils.queryNumEntries(db, JOBS_TABLE_NAME);
        db.close();
        return jobCount;
    }


    /*  Get total hour entries
     ******************************************/
    public long totalHours() {
        SQLiteDatabase db = this.getReadableDatabase();
        long hourCount = DatabaseUtils.queryNumEntries(db, HOURS_TABLE_NAME);
        db.close();
        return hourCount;
    }


    /*  Returns most recent hours entry data
     ******************************************/
    public String[] getHoursEntry() {
        String hoursEntry[] = new String[6];
        hoursEntry[0] = "false";
        if (totalHours() > 0) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Hours WHERE ID = (SELECT MAX(ID) FROM Hours)", null);

            if (cursor.moveToFirst()) {
                hoursEntry[0] = cursor.getString(cursor.getColumnIndex("hoursDate"));
                hoursEntry[1] = cursor.getString(cursor.getColumnIndex("startTime"));
                hoursEntry[2] = cursor.getString(cursor.getColumnIndex("endTime"));
                hoursEntry[3] = cursor.getString(cursor.getColumnIndex("workedHours"));
                hoursEntry[4] = cursor.getString(cursor.getColumnIndex("lunch"));
                hoursEntry[5] = cursor.getString(cursor.getColumnIndex("jobName"));
            }
            else {
                hoursEntry[0] = "false";
            }

            db.close();
        }
        else {
            hoursEntry[0] = "false";
        }

        return hoursEntry;
    }


    /*  Closes database connection
     ******************************************/
    private void closeDB() {
        if (db != null)
            db.close();
    }


    /* Get hour info
     ******************************************/
    public List<String> hourQuery(String queryString) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        List<String> hourInfo = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                hourInfo.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();
        db.close();

        return hourInfo;
    }


    /*  Add hour entry to database
    ******************************************/
    public boolean insertHours(String date, String start, String startAMPM, String end, String endAMPM, double lunchDuration, String lunchUnit, String job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        double hours = 0;

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        start = start + " " + startAMPM;
        end = end + " " + endAMPM;

        // Convert lunch duration to minutes
        if (lunchUnit == "Hours") {
            lunchDuration *= 60;
        }

        // Format date, start and end time
        try {
            Date startTime = timeFormat.parse(start);
            Date endTime = timeFormat.parse(end);

            long hourDifference = startTime.getTime() - endTime.getTime();
            int days = (int) (hourDifference / (1000*60*60*24));
            int calculationHours = (int) ((hourDifference - (1000*60*60*24*days)) / (1000*60*60));
            int min = (int) (hourDifference - (1000*60*60*24*days) - (1000*60*60*calculationHours)) / (1000*60);
            hours = (calculationHours < 0 ? -calculationHours: min);

            Date hoursDate = dateFormat.parse(date); // Convert from string to Date
            date = dateFormat.format(hoursDate);
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
            Log.i("Parse Error:", e.toString());
        }

        // Store Values - Column Name | Value
        contentValues.put(hourCOL2, date);
        contentValues.put(hourCOL3, start);
        contentValues.put(hourCOL4, end);
        contentValues.put(hourCOL5, hours);
        contentValues.put(hourCOL6, lunchDuration);
        contentValues.put(hourCOL7, job);

        try {
            long result = db.insert(HOURS_TABLE_NAME, null, contentValues);       // Insert values

            // Verify values were inserted
            if (result == -1) {
                db.close();
                return false;
            }
            else {
                db.close();
                return true;
            }
        }
        catch (android.database.SQLException e) {
            db.close();
            return false;
        }
    }


    /*  Get Defaults
     ******************************************/
    public String[] getAppDefaults() {
        String[] appDefaults = new String[DEFAULTS_SIZE];

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DEFAULTS_TABLE_NAME + " LIMIT 1", null);

        if (cursor.moveToFirst()) {
            appDefaults[0] = cursor.getString(cursor.getColumnIndex("startTime"));
            appDefaults[1] = cursor.getString(cursor.getColumnIndex("endTime"));
            appDefaults[2] = cursor.getString(cursor.getColumnIndex("lunchDuration"));
        }
        else {
            appDefaults[0] = "false";
        }
        return appDefaults;
    }


    /*  Set Defaults
     ******************************************/
    public boolean setDefaults(String newStartTime, String newEndTime, String newLunchDuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;

        try {
            db.execSQL("UPDATE " + DEFAULTS_TABLE_NAME + " SET startTime = '" + newStartTime + "', endTime = '" + newEndTime + "', lunchDuration = '" + newLunchDuration + "' WHERE ID = 1");
            success = true;
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL Error:", e.toString());
            success = false;
        }

        db.close();
        return success;
    }


    /*  Insert Job
     ******************************************/
    public boolean insertJob(String name, String jobActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(jobCOL2, name);
        contentValues.put(jobCOL3, jobActive);
        long result = db.insert(JOBS_TABLE_NAME, null, contentValues);

        if (result == -1) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }


    /*  Retrieve Job
     ******************************************/
    public String[] retrieveJob(String name) {
        String[] jobInfo = new String[3];

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + JOBS_TABLE_NAME + " WHERE " + jobCOL2 + " = '" + name + "' LIMIT 1", null);

        if (cursor.moveToFirst()) {
            jobInfo[0] = cursor.getString(cursor.getColumnIndex(jobCOL1)); // Job ID
            jobInfo[1] = cursor.getString(cursor.getColumnIndex(jobCOL2)); // Job name
            jobInfo[2] = cursor.getString(cursor.getColumnIndex(jobCOL3)); // Activity status
        }
        else {
            jobInfo[0] = "false";
        }
        db.close();
        return jobInfo;
    }


    /*  Update Job
     ******************************************/
    public boolean updateJob(String id, String name, String activeStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        try {
            db.execSQL("UPDATE " + JOBS_TABLE_NAME + " SET " + jobCOL2 + " = '" + name + "', " + jobCOL3 + " = '" + activeStatus + "' WHERE " + jobCOL1 + " = " + Integer.valueOf(id));
            success = true;
            db.close();
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL Error:", e.toString());
            success = false;
            db.close();
        }
        return success;
    }


    /*  Delete Hours
     ******************************************/
    public boolean deleteHours() {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        try {
            db.execSQL("DELETE FROM " + HOURS_TABLE_NAME);
            success = true;
            db.close();
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL Error:", e.toString());
            success = false;
            db.close();
        }
        return success;
    }


    /*  Delete All Jobs
     ******************************************/
    public boolean deleteJobs() {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        try {
            db.execSQL("DELETE FROM " + JOBS_TABLE_NAME);
            success = true;
            db.close();
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL Error:", e.toString());
            success = false;
            db.close();
        }
        return success;
    }


    /*  Delete Specific Job
     ******************************************/
    public boolean deleteJob(int jobID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success;
        try {
            db.execSQL("DELETE FROM " + JOBS_TABLE_NAME + " WHERE ID = " + jobID);
            success = true;
            db.close();
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL Error:", e.toString());
            success = false;
            db.close();
        }
        return success;
    }

    /*  Get Defaults
     ******************************************/
}

