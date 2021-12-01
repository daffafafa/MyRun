package com.daffa.myrun;

import com.daffa.myrun.provider.MyRunContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.ContentResolver;
import android.net.Uri;

import java.sql.Date;

//handles creation of db and communication with content provider
public class MyRunDBHandler extends SQLiteOpenHelper{
    private ContentResolver myCR;


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyRun.db";
    public static final String TABLE_RUN = "MyRuns";
    public static final String _ID = "_id";
    public static final String NAME = "runName";
    public static final String TOTAL_TIME  = "totalTime";

    public static final String MAX_SPEED = "maxSpeed";
    public static final String TOTAL_DISTANCE = "totalDistance";
    public static final String RATING = "runRating";

    public static final String COMMENTS = "comments";
    public static final String DATE = "date";


    public MyRunDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MYRUN_TABLE = "CREATE TABLE " +
                TABLE_RUN + "("
                + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                NAME + " VARCHAR(128)," + TOTAL_TIME
                + " INTEGER," + MAX_SPEED + " REAL," + TOTAL_DISTANCE + " REAL," + RATING
                + " VARCHAR(128)," + COMMENTS + " VARCHAR(128)," + DATE + " TEXT" + ")" ;



        db.execSQL(CREATE_MYRUN_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUN);
        onCreate(db);
    }

    //for adding entries
    public int addRun(RunRecord run) {
        ContentValues values = new ContentValues();
        values.put(NAME, run.getRunName());
        values.put(TOTAL_TIME, run.getTotalTime());
        values.put(MAX_SPEED, run.getMaxSpeed());
        values.put(TOTAL_DISTANCE, run.getTotalDistance());
        values.put(RATING, run.getRunRating());
        values.put(COMMENTS, run.getComments());
        values.put(DATE, run.getRunDate());
        Uri run_id_uri = myCR.insert(MyRunContentProvider.RUN_URI, values);

        int run_id = Integer.parseInt(run_id_uri.getLastPathSegment());
        return run_id;
    }

    //returns entire table
    public Cursor readAllData(){
        Cursor allData = myCR.query(MyRunContentProvider.RUN_URI, null, null, null, null);

        return allData;
    }

    //return table according to month selected
    public Cursor readMonthData(String selection){
        Cursor MonthData = myCR.query(MyRunContentProvider.RUN_URI, null, selection, null, null);

        return MonthData;
    }

    //returns specific entry based on ID
    public Cursor queryID(String selection){
        Cursor MonthData = myCR.query(MyRunContentProvider.RUN_URI, null, selection, null, null);

        return MonthData;
    }

}
