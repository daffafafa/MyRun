package com.daffa.myrun.provider;

import com.daffa.myrun.MyRunDBHandler;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

public class MyRunContentProvider extends ContentProvider{

    private MyRunDBHandler MyRunDB;

    private static final String AUTHORITY = "com.daffa.myrun.provider.MyRunContentProvider";

    public static final Uri RUN_URI = Uri.parse("content://"+AUTHORITY+"/MyRuns");


    public static final String TABLE = "MyRuns";

    public static final int RUNS = 1;

    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, TABLE, RUNS);
    }

    public MyRunContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        // throw new UnsupportedOperationException("Not yet implemented");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = MyRunDB.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case RUNS:
                rowsDeleted = sqlDB.delete(MyRunDBHandler.TABLE_RUN,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " +
                        uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        // throw new UnsupportedOperationException("Not yet implemented");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = MyRunDB.getWritableDatabase();
        long id = 0;

        switch (uriType) {
            case RUNS:
                id = sqlDB.insert(MyRunDBHandler.TABLE_RUN,
                        null, values);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        switch (uriType) {
            case RUNS:
                return Uri.parse(TABLE + "/" + id);


            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        MyRunDB = new MyRunDBHandler(getContext(), null, null, 1);
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        // throw new UnsupportedOperationException("Not yet implemented");

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case RUNS:
                queryBuilder.setTables(MyRunDBHandler.TABLE_RUN);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(MyRunDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        // throw new UnsupportedOperationException("Not yet implemented");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = MyRunDB.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case RUNS:
                rowsUpdated =
                        sqlDB.update(MyRunDBHandler.TABLE_RUN,
                                values,
                                selection,
                                selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }

        getContext().getContentResolver().notifyChange(uri,
                null);

        return rowsUpdated;
    }
}
